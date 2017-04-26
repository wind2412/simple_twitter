package zxl.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zxl.bean.Article;
import zxl.bean.User;

/**
 * 	Jedis不支持集群的事务。
 *  等逻辑写完之后，我会使用lua重写全部。						=> 	 	大坑
 *  再加上spring。										=>		大坑	
 *  time最好不要在这里写。应该放到前端，用户点击的瞬间才好啊。		=>		坑
 */
public class Cluster {
	
	public static String ip = "127.0.0.1";
	public static final int VOTE_SCORE = 432;
	public static final int REPLY_SCORE = 648;
	public static final int TRANS_SCORE = 1080;
	public static final int ARTICLES_PER_PAGE = 25;
	
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
			System.out.println(user.getUID());
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
	
	public void add_an_article(Article article) throws IOException{		//注意，没有加上图片功能。
		long AID = jc.incr("AID");
		article.setAID(AID);
		String keyname = "article:"+article.getAID();
		article.setTime(System.currentTimeMillis()/1000);
		//设置文章article的article:[AID]表。
		jc.hset(keyname, "content", article.getContent());
		jc.hset(keyname, "UID",  String.valueOf(article.getUID()));
		jc.hset(keyname, "type", String.valueOf(article.getType()));
		jc.hset(keyname, "time", String.valueOf(article.getTime()));
		//设置文章article的pictures:[AID]表的pics路径。
		if(article.getPics() != null)
		for(int i = 0; i < article.getPics().size(); i ++){
			jc.lpush("pictures:"+AID, article.getPics().get(i));
		}
		//添加到user的all_articles:[UID]表。	=>	user写的文章。
		jc.zadd("all_articles:"+article.getUID(), article.getTime(), String.valueOf(article.getAID()));
		//添加此文章AID到score:AID表。填入内容为Unix时间。
		jc.zadd("score:AID", article.getTime(), String.valueOf(article.getAID()));
		//对于[回复和/转发]进行额外的工作
		if(article.getType() == 1)	{			//如果是正在[回复]别人的[文章/回复/转发]
			//设置此article:AID表的trans_AID属性。
			jc.hset(keyname, "trans_AID", String.valueOf(article.getTrans_AID()));
			//设置get_commented:[AID]表。
			jc.zadd("get_commented:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));		//被回复的文章被回复次数+1，加到被回复文章的get_commented的zset中。
			//给被回复的文章加分		//改？？？？？应该改成给所有链上的文章加分。。。
			add_score_to_article_chains(AID, REPLY_SCORE);
		} else if(article.getType() == 2) {		//如果是正在[转发]别人的[文章/回复/转发]
			//设置此article:AID表的trans_AID属性。
			jc.hset(keyname, "trans_AID", String.valueOf(article.getTrans_AID()));
			//设置get_transed:[AID]表。		=>	被转发文章的所有转发列表
			jc.zadd("get_transed:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));		//被转发的文章被转发次数+1，加到被转发文章的get_transed的zset中。
			//给被转发的文章加分		//改？？？？？应该改成给所有链上的文章加分。。。
			add_score_to_article_chains(AID, TRANS_SCORE);
		}
	}
	
	/**
	 * 得到不包括此AID文章的之前所有文章链。举例：此AID时由转发AID(3)文章得来。AID(3)由评论AID(5)文章得来。于是得到表：[3,5].
	 * @param AID
	 * @return
	 */
	public List<Long> get_article_chains(long AID){
		List<Long> list = new LinkedList<Long>();
		long type = Long.parseLong(jc.hget("article:"+AID, "type"));
		while(type == 1 || type == 2){		//是评论和转发才继续做。
			long trans_AID = Long.parseLong(jc.hget("article:"+AID, "trans_AID"));
			list.add(trans_AID);
			type = Long.parseLong(jc.hget("article:"+trans_AID, "type"));
		}
		return list;
	}

	/**
	 * 调用get_article_chains方法进行对chain上的每个article都进行加分。
	 * @param AID
	 */
	public void add_score_to_article_chains(long AID, int add_score){
		List<Long> s = this.get_article_chains(AID);
		for(long aid : s){
			//先给score总表加分
			jc.zincrby("score", add_score, String.valueOf(aid));
			long type = Long.parseLong(jc.hget("article:"+aid, "type"));
			if(type == 1){
				//如果这个aid代表的是回复，也需要在回复表计分当中同步。
				jc.zincrby("score:reply:"+aid, add_score, String.valueOf(aid));
			}else if(type == 2){
				//如果这个aid代表的是转发，也需要在转发表计分当中同步。
				jc.zincrby("score:trans:"+aid, add_score, String.valueOf(aid));					
			}
		}
	}
	
	public void remove_an_article(long AID){
		String keyname = "article:"+AID;				//hash
		//从该用户的all_articles:[UID]表中移除此文章的AID。但是我们因为要由此文章的article:[AID]表索引到UID，因此这一步必须放在前面。
		long UID = Long.parseLong(jc.hget(keyname, "UID"));	//得到文章作者UID
		jc.zrem("all_articles:"+UID, String.valueOf(AID));	//移除此作者名下的这篇文章
		//如果这篇文章是转发自别人的话，需要从get_transed:[trans_AID]表当中删除这篇被删除的转发！
		String trans_AID = jc.hget("article:"+AID, "trans_AID");
		if(trans_AID != null){
			//删除表项：get_transed:[AID]表中的trans_AID字段
			jc.zrem("get_transed:"+trans_AID, String.valueOf(AID));
			//给被转发的文章减分
			jc.zincrby("score:AID", -TRANS_SCORE, String.valueOf(trans_AID));			
		}
		//删除此文章的article:[AID]表......全删除好了。查文章索引不到为nil的时候，直接弹出“因法律法规原因并未显示”好了（笑
		jc.del(keyname);
		//删除此文章下所有评论：get_commented:[AID]
		jc.del("get_commented:"+AID);
		//删除此文章下所有赞：get_voted:AID:[AID]		//注意：此文章下所有转发是删不了的。
		jc.del("get_voted:AID:"+AID);
				//是谁赞的文章的voted:[AID]表就不删除了。如果发现“xxx最近赞了xxx”时候文章已经被del，那就直接显示已经被删除好了。但是“最近赞了”还是要留下。
		//删除此文章下所有图片，但是图片的路径还是别删了（大雾 :pictures:AID:[AID]
		jc.del("pictures:AID:"+AID);
		//删除score:AID表中的此AID
		jc.zrem("score:AID", String.valueOf(AID));
	}
	
	/**
	 * 某篇文章是不是转发/回复的？
	 * @param AID
	 * @return
	 */
	public boolean is_transed_article(long AID){
		return jc.hget("article:"+AID, "trans_AID") == null ? false : true;
	}
	
	
	/**
	 * 需要加入文章score:AID机制的vote系统	=> 表名：score:AID{`AID`, `score`}
	 * 设定：一篇文章的score是：Unix_time + voted*432 + reply*648 + trans*1080
	 * @param UID => 谁赞的
	 * @param AID => 赞了啥
	 * 		PS::调用此方法之前需要先使用judge_voted函数检测是否投过票了？
	 */
	public void vote_an_article(long UID, long AID){
		//先检查文章到底被没被删除！也就是查AID到底有没有！但是不必管UID到底有没有。因为操作者一定有个UID。也就是UID一定是存在的。
		if(jc.zrank("score:AID", String.valueOf(AID)) == null)		return;		//看似不相干，但是因为score:AID表当中存了所有文章的集合，因此可以拿来使用！！
		//检查一个用户到底有没有那个文章，=> 不能给自己投票！！
		if(jc.zrank("all_articles:"+UID, String.valueOf(AID)) != null) 		return;
		long cur_time = System.currentTimeMillis()/1000;
		//让这篇文章的分数上升432
		jc.zincrby("score:AID", VOTE_SCORE, String.valueOf(AID));
		//加文章到此人赞的列表中
		jc.zadd("voted:"+UID, cur_time, String.valueOf(AID));
		//加此人到此文章被赞的列表中
		jc.zadd("get_voted:AID:"+AID, cur_time, String.valueOf(UID));
	}
	
	/**
	 * 取消点赞一篇文章
	 * @param UID
	 * @param AID
	 * 		PS::调用此方法之前需要先使用judge_voted函数检测是否投过票了？
	 */
	public void vote_cancelled_oh_no(long UID, long AID){
		//先检查文章到底被没被删除！也就是查AID到底有没有！但是不必管UID到底有没有。因为操作者一定有个UID。也就是UID一定是存在的。
		if(jc.zrank("score:AID", String.valueOf(AID)) == null)		return;		//看似不相干，但是因为score:AID表当中存了所有文章的集合，因此可以拿来使用！！
		//检查一个用户到底有没有那个文章，=> 不能给自己反投票！！
		if(jc.zrank("all_articles:"+UID, String.valueOf(AID)) != null) 		return;
		//让这篇文章的分数下降432
		jc.zincrby("score:AID", -VOTE_SCORE, String.valueOf(AID));
		//删除文章从此人赞的列表
		jc.zrem("voted:"+UID, String.valueOf(AID));
		//删除此人从此文章被赞的列表
		jc.zrem("get_voted:AID:"+AID, String.valueOf(UID));		
	}
	
	/**
	 * 判断一篇文章是否已经被投票？仅仅从投票一方voted:[UID]进行判断，可以不判断get_voted:AID:[AID].
	 * @param UID
	 * @param AID
	 */
	public boolean judge_voted(long UID, long AID){
		return jc.zrank("voted:"+UID, String.valueOf(AID)) == null ? true : false;
	}
	
	//得到此用户最后赞过哪篇文章  适用于：(xxx在hh:mm时赞过yyy)的twitter		//发现好像没有xxx在最后评论过yyy的文章啊......
	public Set<String> get_user_vote_others(long UID){
		return jc.zrevrangeByScore("voted:"+UID, "+inf", "-inf");
	}
	
	//得到此用户所有推文
	public Set<String> get_user_articles(long UID){
		return jc.zrevrangeByScore("all_articles:"+UID, "+inf", "-inf");
	}
	
	//得到此用户所有推文的总数
	public long get_user_articles_num(long UID){
		//返回zset.length()
		return jc.zcard("all_articles:"+UID);
	}
	
	//关注某人o(*////▽////*)o
	/**
	 * 前端调用之前，需要调用focus_or_not()方法检测srcUID和targetUID是不是已经关注了。必须调用！
	 * @param srcUID
	 * @param targetUID
	 */
	public void focus_a_user(long srcUID, long targetUID){
		long time = System.currentTimeMillis()/1000;
		//在自己关注中加入对方
		jc.zadd("focus:"+srcUID, time, String.valueOf(targetUID));
		//在对方粉丝中加入自己
		jc.zadd("fans:"+targetUID, time, String.valueOf(srcUID));
	}
	
	/**
	 * 前端调用之前，需要调用focus_or_not()方法检测srcUID和targetUID是不是已经关注了。必须调用！
	 * @param srcUID
	 * @param targetUID
	 */
	//取消关注
	public void focus_cancelled_oh_no(long srcUID, long targetUID){
		//从自己的关注中移除对方
		jc.zrem("focus:"+srcUID, String.valueOf(targetUID));
		//从对方的粉丝中移除自己
		jc.zrem("fans:"+targetUID, String.valueOf(srcUID));
	}
	
	/**
	 * 判断$(1)是否关注了$(2)		=>		$(1)指第一个参数
	 * @param srcUID
	 * @param targetUID
	 * @return
	 */
	public boolean focus_or_not(long srcUID, long targetUID){
		return jc.zrank("focus:"+srcUID, String.valueOf(targetUID)) == null ? false : true;
	}
	
	/**
	 * 得到某个用户的所有关注列表	=>		按照时间排序
	 * @param UID
	 * @return
	 */
	public Set<String> get_all_focus(long UID){
		return jc.zrevrangeByScore("focus:"+UID, "+inf", "-inf");
	}
	
	/**
	 * 得到某个用户的所有粉丝列表	=>		按照时间排序
	 * @param UID
	 * @return
	 */
	public Set<String> get_all_fans(long UID){
		return jc.zrevrangeByScore("fans:"+UID, "+inf", "-inf");
	}
	
	/**
	 * 得到一篇文章的评分
	 * @param AID
	 * @return
	 */
	public Long get_an_article_score(long AID){
		Double score = jc.zscore("score:AID", String.valueOf(AID));
		if(score == null)	return null;
		else return (long)((double)score);		//我们的score全是整数。
	}
	
	/**
	 * 分页得到某个文章评论。一页25个评论。
	 * @param AID
	 * @param page
	 * @return
	 */
	public Set<String> get_article_comments_msg(long AID, int page){
		//得到一页的用户回复
		Set<String> CIDs = jc.zrevrange("get_commented:"+AID, (page-1)*ARTICLES_PER_PAGE, page*ARTICLES_PER_PAGE-1);
		Set<String> comments = new LinkedHashSet<String>();
		for(String CID : CIDs){
			comments.add(this.get_nested_reply(Long.parseLong(CID)));
		}
		return comments;
	}
	
	
	
	
	public static void main(String[] args) throws IOException {

		Cluster c = new Cluster();
//		User zxl = new User("zhengxiaolin", "123", 20);
//		User jxc = new User("jiangxicong", "123", 20);
//		c.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
//		c.add_a_user(jxc);
//		c.add_an_article(new Article("today I bought a very good thing!", jxc.getUID(), 0, 0));		//0参数表示并非转发
//		c.remove_an_article(1);
//		c.flush_all();
		System.out.println(c.get_an_article_score(3));
//		c.vote_an_article(1, 3);
		c.remove_an_article(4);
//		c.add_a_comment(new Comment("nice to see U!", 1, 3, 0));	//调用的时候，如果不是转别人的评论，就不需要填写。但是AID那个参数是必填的，因为全归属于那个文章。
//		c.add_a_comment(new Comment("comment myself comment!", 1, 3, 1));	//调用的时候，如果不是转别人的评论，就不需要填写。但是AID那个参数是必填的，因为全归属于那个文章。
//		c.remove_a_comment(10);
		System.out.println(c.get_an_article_score(3));
//		c.vote_cancelled_oh_no(1, 3);
//		c.focus_a_user(2, 1);
//		c.focus_cancelled_oh_no(2, 1);
		c.get_all_keys();
		
	}

}
