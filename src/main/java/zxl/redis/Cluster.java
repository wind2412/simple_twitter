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
import zxl.bean.Binutils;
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
	
	public void add_a_user(User user){	///传入user的除了UID之外的各种属性
//		jc		//没有事务？？？ 事务无法操纵多个主键。因此只能使用lua脚本。
		long UID = jc.incr("UID");
		user.setUID(UID);
		user.setTime(System.currentTimeMillis()/1000);
		//设置user:[UID]，user信息表
		jc.hset("user:"+user.getUID(), "name", user.getName());
		jc.hset("user:"+user.getUID(), "pass", user.getPass());
		jc.hset("user:"+user.getUID(), "age", String.valueOf(user.getAge()));
		jc.hset("user:"+user.getUID(), "time", String.valueOf(user.getTime()));
	}
	
	public void add_an_article(Article article) throws IOException{
		long AID = jc.incr("AID");
		String keyname = "article:"+article.getAID();	//hash
		article.setPath("articles/article_" + AID);
		article.setTime(System.currentTimeMillis()/1000);
		article.add_article_to_disk();					//持久化文章内容到硬盘上			//图片功能再议
		//设置文章article的article:[AID]表。
		jc.hset(keyname, "path", article.getPath());
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
		String path = jc.hget(keyname, "path");
		Binutils.remove_things_from_disk(path);
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
		String keyname = "comment:"+comment.getCID();	//hash
		comment.setPath("comments/comment_" + CID);
		comment.setTime(System.currentTimeMillis()/1000);
		comment.add_comment_to_disk();					//持久化文章内容到硬盘上			//图片功能再议
		//设置comment:[CID]表。		=>		此评论信息
		jc.hset(keyname, "path", comment.getPath());
		jc.hset(keyname, "UID", String.valueOf(comment.getUID()));
		jc.hset(keyname, "AID", String.valueOf(comment.getAID()));
		jc.hset(keyname, "time", String.valueOf(comment.getTime()));
		//设置get_commented:[AID]表。=>		被评论的文章的所有评论列表。	
		jc.zadd("get_commented:"+comment.getAID(), comment.getTime(), String.valueOf(comment.getCID()));		//被评论的文章被评论次数+1，加到被评论文章的get_commented列表中。
	}	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		Cluster c = new Cluster();
//		c.get_all_keys();
	}

}
