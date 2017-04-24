package zxl.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zxl.bean.Article;
import zxl.bean.Comment;
import zxl.bean.User;

public class Cluster {

	public static String ip = "127.0.0.1";
	private JedisCluster jc;
	
	public Cluster() {
		
		Set<HostAndPort> hosts = new HashSet<HostAndPort>();
		hosts.add(new HostAndPort(ip, 6379));
		hosts.add(new HostAndPort(ip, 6380));
		hosts.add(new HostAndPort(ip, 6381));
		hosts.add(new HostAndPort(ip, 6382));
		hosts.add(new HostAndPort(ip, 6383));
		hosts.add(new HostAndPort(ip, 6384));
		hosts.add(new HostAndPort(ip, 6385));
		hosts.add(new HostAndPort(ip, 6386));			
		jc = new JedisCluster(hosts, new JedisPoolConfig());
		
		get_all_keys();
		
	}
	
	//得到所有的key表的列表。
	public void get_all_keys(){
		System.out.println("*************** keys ***************");
		Map<String, JedisPool> nodes = jc.getClusterNodes();
		for(String s : nodes.keySet()){
			JedisPool jp = nodes.get(s);
			Jedis conn = jp.getResource();
			System.out.println(s + " " + conn.keys("*"));
		}
		System.out.println("************************************");
	}
	
	//慎用！清空全部数据库
	public void flush_all(){
		System.out.println("************ after flush ***********");
		Map<String, JedisPool> nodes = jc.getClusterNodes();
		for(String s : nodes.keySet()){
			JedisPool jp = nodes.get(s);
			Jedis conn = jp.getResource();
			try{
				conn.flushAll();				
			}catch (Exception e){}
			System.out.println(s + " " + conn.keys("*"));
		}
		System.out.println("************************************");
	}
	
	//如果成功，返回1  否则返回0
	public int add_a_user(User user){	///传入user的除了UID之外的各种属性
//		jc		//没有事务？？？ 事务无法操纵多个主键。因此只能使用lua脚本。
		//添加信息到用户名密码数据库中 + 判断是否重复注册。表名：pass		这个判断必须放在所有数据库操作的第一位。因为它必须最先判断。
		long ret = jc.hsetnx("pass", user.getName(), user.getPass());
		if(ret == 0){
			//临退出之前，需要在这个空的user中写入他本身的UID才好，方便外界的user可以重复使用。
			long user_uid = Long.parseLong(jc.hget("getuser", user.getName()));
			user.setUID(user_uid);
			user.setTime(Long.parseLong(jc.hget("user:"+user_uid, "time")));
			return 0;			//此用户名已经存在，已经有此用户了。操作终止。
		}
		//写入
		long UID = jc.incr("UID");
		user.setUID(UID);
		user.setTime(System.currentTimeMillis()/1000);
		//添加到name-UID反查库中。表名：getuser	<hash>
		jc.hset("getuser", user.getName(), String.valueOf(user.getUID()));
		//设置user:[UID]，user信息表		//UID不用设置。表的名字即是UID。
		String keyname = "user:"+user.getUID();
		jc.hset(keyname, "name", user.getName());
		jc.hset(keyname, "pass", user.getPass());
		jc.hset(keyname, "age", String.valueOf(user.getAge()));
		jc.hset(keyname, "time", String.valueOf(user.getTime()));
		return 1;
	}
	
	public void add_an_article(Article article) throws IOException{
		long AID = jc.incr("AID");
		article.setAID(AID);
		String keyname = "article:"+article.getAID();	//hash
		article.setTime(System.currentTimeMillis()/1000);
		//设置文章article的article:[AID]表。
		jc.hset(keyname, "content", article.getContent());
		jc.hset(keyname, "UID",  String.valueOf(article.getUID()));
		if(article.getTrans_AID() != 0)	{				//如果是正在转发别人的文章
			jc.hset(keyname, "trans_AID",  String.valueOf(article.getTrans_AID()));
			//设置get_transed:[AID]表。		=>	被转发文章的所有转发列表
			jc.zadd("get_transed:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));		//被转发的文章被转发次数+1，加到被转发文章的get_transed的zset中。
		}
		jc.hset(keyname, "time", String.valueOf(article.getTime()));
		//添加到user的all_articles:[UID]表。	=>	user写的文章。
		jc.zadd("all_articles:"+article.getUID(), article.getTime(), String.valueOf(article.getAID()));
	}

	public void remove_an_article(long AID){
		String keyname = "article:"+AID;				//hash
		//删除此文章的磁盘路径
		//从该用户的all_articles:[UID]表中移除此文章的AID。但是我们因为要由此文章的article:[AID]表索引到UID，因此这一步必须放在前面。
		long UID = Long.parseLong(jc.hget(keyname, "UID"));	//得到文章作者UID
		jc.zrem("all_articles:"+UID, String.valueOf(AID));	//移除此作者名下的这篇文章
		//删除此文章的article:[AID]表......全删除好了。查文章索引不到为nil的时候，直接弹出“因法律法规原因并未显示”好了（笑
		jc.del(keyname);
		//删除此文章下所有评论：get_commented:[AID]
		jc.del("get_commented:"+AID);
		//删除此文章下所有赞：get_voted:[AID]		//注意：此文章下所有转发是删不了的。
		jc.del("get_voted:"+AID);
		//删除此文章下所有图片，但是图片的路径还是别删了（大雾 :pictures:[AID]
		jc.del("pictures:"+AID);
	}
	
	public void add_a_comment(Comment comment) throws IOException{
		long CID = jc.incr("CID");
		comment.setCID(CID);
		String keyname = "comment:"+comment.getCID();	//hash
		comment.setTime(System.currentTimeMillis()/1000);
		//设置comment:[CID]表。		=>		此评论信息
		jc.hset(keyname, "content", String.valueOf(comment.getContent()));
		jc.hset(keyname, "UID", String.valueOf(comment.getUID()));
		jc.hset(keyname, "AID", String.valueOf(comment.getAID()));
		jc.hset(keyname, "time", String.valueOf(comment.getTime()));
		//设置get_commented:[AID]表。=>		被评论的文章的所有评论列表。	
		jc.zadd("get_commented:"+comment.getAID(), comment.getTime(), String.valueOf(comment.getCID()));		//被评论的文章被评论次数+1，加到被评论文章的get_commented列表中。
	}	
	
	public Set<String> get_user_vote_others(long UID){
		return jc.zrevrangeByScore("voted:"+UID, "+inf", "-inf");
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {

		Cluster c = new Cluster();
//		User zxl = new User("zhengxiaolin", "123", 20);
//		User jxc = new User("jiangxicong", "123", 20);
//		c.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
//		c.add_a_user(jxc);
//		c.add_an_article(new Article("today I bought a very good thing!", zxl.getUID(), 0));		//0参数表示并非转发
//		c.remove_an_article(1);
//		c.flush_all();
		c.get_all_keys();
	}

}
