package zxl.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;
import zxl.bean.Article;
import zxl.bean.TimeLineNode;
import zxl.bean.User;

/**
 * 	Jedis不支持集群的事务。
 *  等逻辑写完之后，我会使用lua重写全部。						=> 	 	大坑
 *  time最好不要在这里写。应该放到前端，用户点击的瞬间才好啊。		=>		坑
 */
public class Cluster {
	
	public static String ip = "127.0.0.1";
	public static final int VOTE_SCORE = 432;
	public static final int REPLY_SCORE = 648;
	public static final int TRANS_SCORE = 1080;
	public static final int FOCUS_SCORE = 2160;
	public static final int ARTICLES_PER_PAGE = 20;
	public static final int COMMENT_PER_PAGE = 20;
	
	public static JedisCluster jc;
	
	static {		//构造函数。变成了静态的。
		
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
	public static void get_all_keys(){
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
	public static void flush_all(){
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
	
	//如果成功，返回UID  否则返回0
	public static long add_a_user(String username, String pass){	///传入user的除了UID之外的各种属性
//		jc		//没有事务？？？ 事务无法操纵多个主键。因此只能使用lua脚本。
		//添加信息到用户名密码数据库中 + 判断是否重复注册。表名：pass		这个判断必须放在所有数据库操作的第一位。因为它必须最先判断。
		long ret = jc.hsetnx("pass", username, pass);
		if(ret == 0){
//			long user_uid = Long.parseLong(jc.hget("getuser", username));
			return 0;			//此用户名已经存在，已经有此用户了。操作终止。
		}
		//写入
		long UID = jc.incr("UID");
//		user.setUID(UID);
//		user.setTime(System.currentTimeMillis()/1000);
		//添加到name-UID反查库中。表名：getuser	<hash>
		jc.hset("getuser", username, String.valueOf(UID));
		//设置user:[UID]，user信息表		//UID不用设置。表的名字即是UID。
		String keyname = "user:"+UID;
		jc.hset(keyname, "name", username);
		jc.hset(keyname, "pass", pass);
		jc.hset(keyname, "time", String.valueOf(System.currentTimeMillis()/1000));
		//把UID添加到UIDs，用作在“推荐认识的人”那里的uid查询方便......
		jc.sadd("UIDs", String.valueOf(UID));
		return UID;
	}
	
	/**
	 * 更新User的信息。
	 * @param user
	 */
	public static void upgrade_user_settings(User user){
		String keyname = "user:"+user.getUID();
		if(user.getAge() != 0) 				jc.hset(keyname, "age", String.valueOf(user.getAge()));
		if(user.getMain_page() != null)		jc.hset(keyname, "main_page", user.getMain_page());
		if(user.getPortrait_path() != null)	jc.hset(keyname, "portrait_path", user.getPortrait_path());
		if(user.getIntroducton() != null)	jc.hset(keyname, "introduction", user.getIntroducton());
		if(user.getWebsite() != null)		jc.hset(keyname, "website", user.getWebsite());
		if(user.getPosition() != null)		jc.hset(keyname, "position", user.getPosition());
	}
	
	public static void add_an_article(Article article){		//注意，没有加上图片功能。
		long AID = jc.incr("AID");
		article.setAID(AID);
		String keyname = "article:"+article.getAID();
		article.setTime(System.currentTimeMillis()/1000);
		//设置文章article的article:[AID]表。
		jc.hset(keyname, "content", article.getContent());
		jc.hset(keyname, "UID",  String.valueOf(article.getUID()));
		jc.hset(keyname, "type", String.valueOf(article.getType()));
		jc.hset(keyname, "time", String.valueOf(article.getTime()));
		jc.hset(keyname, "isPrivate", String.valueOf(article.isPrivate()));
		//设置文章article的pictures:[AID]表的pics路径。
		if(article.getPics() != null)
		for(int i = 0; i < article.getPics().size(); i ++){
			jc.lpush("pictures:"+AID, article.getPics().get(i));
		}
		//添加到user的all_articles:[UID]表。	=>	user写的文章。
		jc.zadd("all_articles:"+article.getUID(), article.getTime(), String.valueOf(article.getAID()));
		//添加此文章AID到score表。填入内容为Unix时间。
		jc.zadd("score", article.getTime(), String.valueOf(article.getAID()));
		//对于[回复和/转发]进行额外的工作		=>	get_commented/get_transed:trans_AID
		if(article.getType() == 1)	{			//如果是正在[回复]别人的[文章/回复/转发]
			//设置此article:AID表的trans_AID属性。
			jc.hset(keyname, "trans_AID", String.valueOf(article.getTrans_AID()));
			//设置get_commented:[AID]表。
			jc.zadd("get_commented:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));		//被回复的文章被回复次数+1，加到被回复文章的get_commented的zset中。
			//给被回复的文章加分		//改？？？？？应该改成给所有链上的文章加分。。。
			add_score_to_article_chains_and_user(AID, REPLY_SCORE);
			//加到score:reply:[trans_AID]表中。
			jc.zadd("score:reply:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));
			//加到commented:[UID]表中
			jc.zadd("commented{"+article.getUID()+"}", article.getTime(), String.valueOf(article.getAID()));
		} else if(article.getType() == 2) {		//如果是正在[转发]别人的[文章/回复/转发]
			//设置此article:AID表的trans_AID属性。
			jc.hset(keyname, "trans_AID", String.valueOf(article.getTrans_AID()));
			//设置get_transed:[AID]表。		=>	被转发文章的所有转发列表
			jc.zadd("get_transed:"+article.getTrans_AID(), article.getTime(), String.valueOf(article.getAID()));		//被转发的文章被转发次数+1，加到被转发文章的get_transed的zset中。
			//给被转发的文章加分		//改？？？？？应该改成给所有链上的文章加分。。。
			add_score_to_article_chains_and_user(AID, TRANS_SCORE);
			//加到transed:[UID]表中
			jc.zadd("transed{"+article.getUID()+"}", article.getTime(), String.valueOf(article.getAID()));
		} else{		//type == 0
			//设置此article:AID表的trans_AID属性。
			jc.hset(keyname, "trans_AID", String.valueOf(0));		//也要设置。如果仅仅因为没有trans_AID就不设置，那么到get_an_article方法里，Long.parseLong应该会崩溃吧。		
		}
		//添加到所有fans的timeline中。
		long TID = jc.incr("TID");		//把时间线ID=>TID+1.
		jc.hset(keyname, "TID", String.valueOf(TID)); 		//设置为文章属性。
		article.setTID(TID); 			//设置到文章之中。
		///这里千万注意！！如果是type==0，那就是某个UID发了一篇文章，无可挑剔。但是如果是type==2，3的话，那么这篇文章在timeline表中还是“此UID发的文章”。所以，
		///如果要产生“XX分钟前XXX转发了XXX的一篇文章”的话，因为这里tln记录的是转发后的哪篇新文章AID，因此还需要通过此AID找到trans_AID来继续跳查一步才行！！
		TimeLineNode tln = new TimeLineNode(TID, article.getUID(), article.getType(), article.getAID(), article.getTime());	
		add_action_message(tln);
		add_action_to_all_fans_timeline(tln);
		//添加到timeline总表中，于是这个总表可以当成流API来用了。
		jc.zadd("timeline", tln.getTime(), String.valueOf(tln.getTID()));	
		//如果不是私有的，就添加TID信息到文章中
		if(article.isPrivate() == false)	jc.hset("article:"+article.getAID(), "TID", String.valueOf(tln.getTID()));
	}
	
	private static TimeLineNode get_a_timeLineNode(long TID){
		String keyname = "action:"+TID;
		return new TimeLineNode(
				TID,
				Long.parseLong(jc.hget(keyname, "UID")),
				Long.parseLong(jc.hget(keyname, "type")),
				Long.parseLong(jc.hget(keyname, "target_AID")),
				Long.parseLong(jc.hget(keyname, "time"))
				);
	}
	
	/**
	 * 得到用户的时间线。在用户的主页显示。也即是，显示所有他的关注者的动态。
	 * @param UID
	 * @param page
	 * @return
	 */
	public static List<TimeLineNode> get_timeline_chains(long UID, int page){
		if((page-1)*ARTICLES_PER_PAGE > jc.zcard("timeline:"+UID))	return null;		//说明已经到了所有页的末尾，后面已经没有了
		Set<String> TIDs = jc.zrevrange("timeline:"+UID, (page-1)*ARTICLES_PER_PAGE, page*ARTICLES_PER_PAGE-1);
		List<TimeLineNode> action_list = new LinkedList<>();
		for(String tid : TIDs){
			action_list.add(get_a_timeLineNode(Long.parseLong(tid)));
		}
		return action_list;		
	}
	
	/**
	 * 得到不包括此AID文章的之前所有文章链。举例：此AID时由转发AID(3)文章得来。AID(3)由评论AID(5)文章得来。于是得到表：[3,5].
	 * 此函数仅仅用于给文章加分。如果是要显示所有回复什么的，请使用get_article_comments_context().
	 * @param AID
	 * @return
	 */
	private static List<Long> get_article_chains_all_before(long AID){
		List<Long> list = new LinkedList<Long>();
		long type = Long.parseLong(jc.hget("article:"+AID, "type"));
		while(type == 1 || type == 2){		//是评论和转发才继续做。
			long trans_AID = Long.parseLong(jc.hget("article:"+AID, "trans_AID"));
			//如果原来被评论的文章已经被删除了的话	=> 到达源头
			if(jc.zrank("score", String.valueOf(AID)) == null){
				break;
			}
			else list.add(0, trans_AID);
			type = Long.parseLong(jc.hget("article:"+trans_AID, "type"));
			AID = trans_AID;
		}
		return list;
	}

	/**
	 * 调用get_article_chains方法进行对chain上的每个article都进行加分。
	 * @param AID
	 * @param add_score
	 */
	private static void add_score_to_article_chains_and_user(long AID, int add_score){
		List<Long> art_list = get_article_chains_all_before(AID);
//		art_list.add(AID);		//把这篇文章也加入到加分表中。
		Set<Long> user_set = new HashSet<Long>();		//使用HashSet是为了防止同一个用户多次在链上评论，然后被加了多次分。
		for(long aid : art_list){
			//搜索文章的作者，然后加到无重复集合中。届时会给user加分。
			user_set.add(Long.parseLong(jc.hget("article:"+aid, "UID")));
			//先给score总表加分
			jc.zincrby("score", add_score, String.valueOf(aid));
			long type = Long.parseLong(jc.hget("article:"+aid, "type"));
			if(type == 1){
				//如果这个aid代表的是回复，也需要在回复表计分当中同步。
				jc.zincrby("score:reply:"+aid, add_score, String.valueOf(aid));
			}
		}
		//给链上的user加分。
		System.out.println(art_list);
		System.out.println(user_set);
		for(long uid : user_set){
			jc.zincrby("score:user", add_score, String.valueOf(uid));
		}
	}
	
	/**
	 * 移除某一文章
	 * @param AID
	 */
	public static void remove_an_article(long AID){
		String keyname = "article:"+AID;				//hash
		//从该用户的all_articles:[UID]表中移除此文章的AID。但是我们因为要由此文章的article:[AID]表索引到UID，因此这一步必须放在前面。
		long UID = Long.parseLong(jc.hget(keyname, "UID"));	//得到文章作者UID
		long TID = Long.parseLong(jc.hget(keyname, "TID"));	//得到文章的TID，即时间点
		jc.zrem("all_articles:"+UID, String.valueOf(AID));	//移除此作者名下的这篇文章
		//如果这篇文章是回复/转发自别人的话，需要从get_commented/get_transed:[trans_AID]表当中删除这篇被删除的转发！
		long type = Long.parseLong(jc.hget("article:"+AID, "type"));
		if(type == 1){
			//删除表项：get_commented:[AID]表中的trans_AID字段
			jc.zrem("get_commented:"+Long.parseLong(jc.hget("article:"+AID, "trans_AID")), String.valueOf(AID));
			//给被回复的文章链整体减分	//算了。已经回复过就是回复过，即使删了分数也还有吧。
//			add_score_to_article_chains(AID, -REPLY_SCORE);
			//删除表项：commented:[UID]
			jc.zrem("commented{"+UID+"}", String.valueOf(AID));
		}else if(type == 2){
			//删除表项：get_transed:[AID]表中的trans_AID字段
			jc.zrem("get_transed:"+Long.parseLong(jc.hget("article:"+AID, "trans_AID")), String.valueOf(AID));
			//给被转发的文章链整体减分
//			add_score_to_article_chains(AID, -REPLY_SCORE);			
			//删除表项：transed:[UID]
			jc.zrem("transed{"+UID+"}", String.valueOf(AID));
		}
		//删除此文章的article:[AID]表......全删除好了。查文章索引不到为nil的时候，直接弹出“因法律法规原因并未显示”好了（笑
		jc.del(keyname);
		//删除此文章下所有赞：get_voted:[AID]		//注意：此文章下所有回复和转发已经变成推文，是删不了的。
		jc.del("get_voted:"+AID);
				//是谁赞的文章的voted:[AID]表就不删除了。如果发现“xxx最近赞了xxx”时候文章已经被del，那就直接显示已经被删除好了。但是“最近赞了”还是要留下。
		//删除此文章下所有图片，但是图片的路径还是别删了（大雾 :pictures:[AID]
		jc.del("pictures:"+AID);
		//删除score表中的此AID
		jc.zrem("score", String.valueOf(AID));
		//从fans的时间线中删除所有关于这篇文章的信息(如果是公开的文章)		但是总时间线并没有删除。
		if(Boolean.parseBoolean(jc.hget("article:"+AID, "isPrivate")) == false)	rem_action_to_all_fans_timeline(UID, TID);
	}
	
	/**
	 * 某篇文章是不是转发/回复的？
	 * @param AID
	 * @return
	 */
	public static boolean is_transed_article(long AID){
		return jc.hget("article:"+AID, "trans_AID") == null ? false : true;
	}
	
	
	/**
	 * 需要加入文章score机制的vote系统	=> 表名：score{`AID`, `score`}
	 * 设定：一篇文章的score是：Unix_time + voted*432 + reply*648 + trans*1080
	 * @param UID => 谁赞的
	 * @param AID => 赞了啥
	 * 		PS::调用此方法之前需要先使用judge_voted函数检测是否投过票了？
	 */
	public static void vote_an_article(long UID, long AID){
		//先检查文章到底被没被删除！也就是查AID到底有没有！但是不必管UID到底有没有。因为操作者一定有个UID。也就是UID一定是存在的。
		if(jc.zrank("score", String.valueOf(AID)) == null)		return;		//看似不相干，但是因为score表当中存了所有文章的集合，因此可以拿来使用！！
		long cur_time = System.currentTimeMillis()/1000;
		//让这篇文章链的分数上升432
		add_score_to_article_chains_and_user(AID, VOTE_SCORE);
		//加文章到此人赞的列表中
		jc.zadd("voted{"+UID+"}", cur_time, String.valueOf(AID));
		//加此人到此文章被赞的列表中
		jc.zadd("get_voted:"+AID, cur_time, String.valueOf(UID));
	}
	
	/**
	 * 取消点赞一篇文章
	 * @param UID
	 * @param AID
	 * 		PS::调用此方法之前需要先使用judge_voted函数检测是否投过票了？
	 */
	public static void vote_cancelled_oh_no(long UID, long AID){
		//先检查文章到底被没被删除！也就是查AID到底有没有！但是不必管UID到底有没有。因为操作者一定有个UID。也就是UID一定是存在的。
		if(jc.zrank("score", String.valueOf(AID)) == null)		return;		//看似不相干，但是因为score表当中存了所有文章的集合，因此可以拿来使用！！
		//让这篇文章的分数下降432
		add_score_to_article_chains_and_user(AID, -VOTE_SCORE);
		//删除文章从此人赞的列表
		jc.zrem("voted{"+UID+"}", String.valueOf(AID));
		//删除此人从此文章被赞的列表
		jc.zrem("get_voted:"+AID, String.valueOf(UID));		
	}
	
	/**
	 * 判断一篇文章是否已经被投票？仅仅从投票一方voted:[UID]进行判断，可以不判断get_voted:[AID].
	 * @param UID
	 * @param AID
	 */
	public static boolean judge_voted(long UID, long AID){
		return jc.zrank("voted{"+UID+"}", String.valueOf(AID)) == null ? false : true;
	}
	
	/**
	 * 得到此用户最后赞过哪篇文章  适用于：(xxx在hh:mm时赞过yyy)的twitter		//发现好像没有xxx在最后评论过yyy的文章啊......
	 * @param UID
	 * @return
	 */
	public static Set<String> get_user_vote_others(long UID){
		return jc.zrevrangeByScore("voted{"+UID+"}", "+inf", "-inf");
	}
	
	/**
	 * 得到此用户所有推文
	 * @param UID
	 * @return
	 */
	public static Set<String> get_user_articles(long UID){
		return jc.zrevrangeByScore("all_articles:"+UID, "+inf", "-inf");
	}
	
	/**
	 * 得到一篇AID对应的UID
	 * @param AID
	 * @return
	 */
	public static long get_userID_of_an_article(long AID){
		return Long.parseLong(jc.hget("article:"+AID, "UID"));
	}
	
	/**
	 * 得到此用户所有推文的总数
	 * @param UID
	 * @return
	 */
	public static long get_user_articles_num(long UID){
		//返回zset.length()
		return jc.zcard("all_articles:"+UID);
	}
	
	/**
	 * 关注某人o(* ////▽//// *)o
	 * 前端调用之前，需要调用focus_or_not()方法检测srcUID和targetUID是不是已经关注了。必须调用！
	 * @param srcUID
	 * @param targetUID
	 */
	public static void focus_a_user(long srcUID, long targetUID){
		long time = System.currentTimeMillis()/1000;
		//在自己关注中加入对方
		jc.zadd("focus{"+srcUID+"}", time, String.valueOf(targetUID));
		//在对方粉丝中加入自己
		jc.zadd("fans{"+targetUID+"}", time, String.valueOf(srcUID));
		//给对方加分
		jc.zincrby("score:user", FOCUS_SCORE, String.valueOf(targetUID));
	}
	
	/**
	 * 前端调用之前，需要调用focus_or_not()方法检测srcUID和targetUID是不是已经关注了。必须调用！
	 * @param srcUID
	 * @param targetUID
	 */
	//取消关注
	public static void focus_cancelled_oh_no(long srcUID, long targetUID){
		//从自己的关注中移除对方
		jc.zrem("focus{"+srcUID+"}", String.valueOf(targetUID));
		//从对方的粉丝中移除自己
		jc.zrem("fans{"+targetUID+"}", String.valueOf(srcUID));
		//给对方减分
		jc.zincrby("score:user", -FOCUS_SCORE, String.valueOf(targetUID));
	}
	
	/**
	 * 判断$(1)是否关注了$(2)		=>		$(1)指第一个参数
	 * @param srcUID
	 * @param targetUID
	 * @return
	 */
	public static boolean focus_or_not(long srcUID, long targetUID){
		return jc.zrank("focus{"+srcUID+"}", String.valueOf(targetUID)) == null ? false : true;
	}
	
	/**
	 * 得到某个用户的所有关注列表	=>		按照时间排序
	 * @param UID
	 * @return
	 */
	public static Set<String> get_all_focus(long UID){
		return jc.zrevrangeByScore("focus{"+UID+"}", "+inf", "-inf");
	}
	
	/**
	 * 得到用户关注的数目
	 * @param UID
	 * @return
	 */
	public static long get_focus_num(long UID){
		return jc.zcard("focus{"+UID+"}");
	}
	
	/**
	 * 得到用户关注的数目
	 * @param UID
	 * @return
	 */
	public static long get_fans_num(long UID){
		return jc.zcard("fans{"+UID+"}");
	}
	
	
	/**
	 * 得到某个用户的所有粉丝列表	=>		按照时间排序
	 * @param UID
	 * @return
	 */
	public static Set<String> get_all_fans(long UID){
		return jc.zrevrangeByScore("fans{"+UID+"}", "+inf", "-inf");
	}
	
	
	/**
	 * 把一个时间线事件添加到某一用户的所有fans上	=>   加到timeline:[UID]表中
	 * @param tln
	 */
	private static void add_action_to_all_fans_timeline(TimeLineNode tln){
		Set<String> fans_set = get_all_fans(tln.getUID());
		for(String fans : fans_set){
			long fan = Long.parseLong(fans);
			jc.zadd("timeline:"+fan, tln.getTime(), String.valueOf(tln.getTID()));		//添加TID到timeline:[UID]表中
		}
	}
	
	private static void add_action_message(TimeLineNode tln){
		String keyname = "action:"+tln.getTID();
		jc.hset(keyname, "time", String.valueOf(tln.getTime()));
		jc.hset(keyname, "TID", String.valueOf(tln.getTID()));
		jc.hset(keyname, "UID", String.valueOf(tln.getUID()));
		jc.hset(keyname, "type", String.valueOf(tln.getType()));
		jc.hset(keyname, "target_AID", String.valueOf(tln.getTarget_AID()));
	}
	
	/**
	 * 移除某一用户的fans的所有时间轴上的某一事件
	 * @param UID
	 * @param TID
	 */
	private static void rem_action_to_all_fans_timeline(long UID, long TID){
		Set<String> fans_set = get_all_fans(UID);
		for(String fans : fans_set){
			long fan = Long.parseLong(fans);
			jc.zrem("timeline:"+fan, String.valueOf(TID));		//移除TID从timeline:[UID]表中
		}		
	}
	
	/**
	 * 得到一篇文章的评分
	 * @param AID
	 * @return
	 */
	public static Long get_an_article_score(long AID){
		Double score = jc.zscore("score", String.valueOf(AID));
		if(score == null)	return null;
		else return (long)((double)score);		//我们的score全是整数。
	}

	/**
	 * 得到一个用户的个人分数
	 * @param UID
	 * @return
	 */
	public static Long get_a_user_score(long UID){
		Double score = jc.zscore("score:user", String.valueOf(UID));
		if(score == null)	return null;
		else return (long)((double)score);		//我们的score全是整数。
	}
	
	/**
	 * 分页得到某个user的文章。一页20篇文章。
	 * @param UID
	 * @param page
	 * @return
	 */
	public static List<Article> get_articles(long UID, int page){
		if((page-1)*ARTICLES_PER_PAGE > jc.zcard("all_articles:"+UID))	return null;		//说明已经到了所有页的末尾，后面已经没有了
		Set<String> AIDs = jc.zrevrange("all_articles:"+UID, (page-1)*ARTICLES_PER_PAGE, page*ARTICLES_PER_PAGE-1);
		List<Article> art_list = new LinkedList<Article>();
		for(String aid : AIDs){
			art_list.add(get_an_article(Long.parseLong(aid)));
		}
		return art_list;
	}
	
	/**
	 * 分页得到某个文章评论。一页10个评论。且注意twitter只能得到左子树评论。
	 * 推特的推文是，打开的此AID节点下的所有评论(支脉)都会显示出来。但是此AID节点下的子节点的评论(支脉)会只显示最早评论的那一条(即左子树).
	 * @param AID
	 * @param page	=>	用户先得到第一页。需要往下滚动的时候才开始加载第二页。这时要把page加上1之后调用此函数。注意，网页中，需要使用一个js变量page保存用户打开了几页.
	 * @return 返回值是一个List<List<Long>>类型。list.0是根源->此AID(包括)。list.1以后，全是评论的支脉。也就是每个child_aid的支脉了。
	 */
	public static List<List<Article>> get_article_comments_context(long AID, int page){
		List<List<Article>> art_list = new LinkedList<List<Article>>();
		if(page == 0){		//如果是第一次请求page，即page==0，那么就把根源->此AID全发过去。如果page>0，那么这一段必然已经加载。那就不必再发这个了。
			//得到所有上游
			List<Long> upon = get_comments_up(AID);
			//添加自己的AID	//遍历时候找到此AID，就最大显示。因为这是用户所点击的AID。因此主要显示。
			upon.add(AID);
			//创建上游总表
			List<Article> art_upon = new LinkedList<Article>();
			//必须用Long来承接。因为第一个根源可能是null。已经被作者删除了。
			for(Long aid : upon){
				if(aid == null)	art_upon.add(null);
				else art_upon.add(get_an_article(aid));
			}
			//添加上游总表
			art_list.add(art_upon);
		}
		//得到一页的用户回复	=>	先得到10个“此节点的支脉(叶节点)”:child_AID，然后对所有支脉:child_AID求左子树。
		Set<String> child_AIDs = jc.zrevrange("get_commented:"+AID, (page-1)*COMMENT_PER_PAGE, page*COMMENT_PER_PAGE-1);
		for(String child_aid : child_AIDs){
			//得到左支脉下游
			List<Long> child_AID_down = get_comments_down(Long.parseLong(child_aid));
			//最前边加上此child_aid
			child_AID_down.add(0, Long.parseLong(child_aid));
			//创建下游总表
			List<Article> art_down = new LinkedList<Article>();
			//可以使用long来承接。	这样可以看出和上边的区别所在。
			for(long aid : child_AID_down){
				art_down.add(get_an_article(aid));
			}
			//添加上游总表
			art_list.add(art_down);
		}
		return art_list;
	}
	
	/**
	 * 得到此AID上游回溯的所有列表。即，得到此AID的上游评论链条。通过此AID，可以追溯到根源。
	 * @param AID
	 * @return
	 */
	private static List<Long> get_comments_up(long AID){
		List<Long> list = new LinkedList<Long>();
		long type = Long.parseLong(jc.hget("article:"+AID, "type"));
		while(true){		
			if(type == 1 || type == 2){	//是评论才继续做。		//是转发的话，就只做一次，添加完然后退出。
				//得到上一篇文章的AID
				long trans_AID = Long.parseLong(jc.hget("article:"+AID, "trans_AID"));
				//如果原来被评论的文章已经被删除了的话	=> 到达源头
				if(jc.zrank("score", String.valueOf(AID)) == null){
					//头插入
					list.add(0, null);		//添加一个占位符表示原文章被删除！！
					break;
				} else {
					list.add(0, trans_AID);
					if(type == 2)	break;
				}
				type = Long.parseLong(jc.hget("article:"+trans_AID, "type"));				
			}else{		//type是0，返回空列表。因为上游是空的。
				break;
			}
		}
		return list;
	}

	/**
	 * 注意get_comments_up和get_comments_down之间应该是由两层没有做。一层是传入参数的AID，第二层是AID的所有支脉。
	 * 也就是说，此函数的传参是第二层的某一个child_AID.
	 * 
	 * 得到此AID下游回溯的左子树表。即，得到此AID的下游评论链条。通过此AID，可以追踪到最后。
	 * @param child_AID
	 * @return
	 */
	private static List<Long> get_comments_down(Long child_AID){
		List<Long> list = new LinkedList<Long>();
//		long type = Long.parseLong(jc.hget("article:"+AID, "type"));
		while(jc.exists("get_commented:"+child_AID)){	//下边有评论才继续做。
			//得到最早评论的AID，即左子树上的那个。
			child_AID = Long.parseLong(jc.zrange("get_commented:"+child_AID, 0, 0).iterator().next());
			if(child_AID != null)
				list.add(child_AID);
		}
		return list;
	}
	
	/**
	 * 得到一篇推文的信息
	 * @param AID
	 * @return
	 */
	public static Article get_an_article(long AID){
		String keyname = "article:"+AID;
		Article art =  new Article(
				jc.hget(keyname, "content"),
				Long.parseLong(jc.hget(keyname, "UID")), 
				Long.parseLong(jc.hget(keyname, "type")), 
				Long.parseLong(jc.hget(keyname, "trans_AID")), 
				Boolean.parseBoolean(jc.hget(keyname, "isPrivate")),
				jc.lrange("pictures:"+AID, 0, -1));
		art.setTime(Long.parseLong(jc.hget(keyname, "time")));
		art.setAID(AID);
		art.setTID(Long.parseLong(jc.hget(keyname, "TID")));
		return art;
	}
	
	/**
	 * 得到一个用户的信息
	 * @param UID
	 * @return
	 */
	public static User get_a_user_by_UID(long UID){		//由于使用了DWR，在js中不能分辨重载的方法。因此把重载的方法更改了名称并且取消掉了。
		String keyname = "user:"+UID;
		User user = new User(jc.hget(keyname, "name")); 
		user.setUID(UID);
		user.setTime(Long.parseLong(jc.hget(keyname, "time")));
		String age = jc.hget(keyname, "age");	user.setAge(age==null ? 0 : Integer.parseInt(age));
		user.setMain_page(jc.hget(keyname, "main_page"));
		user.setPortrait_path(jc.hget(keyname, "portrait_path"));
		user.setPosition(jc.hget(keyname, "position"));
		user.setIntroducton(jc.hget(keyname, "introduction"));
		user.setWebsite(jc.hget(keyname, "website"));
		return user;
	}
	
	/**
	 * 得到一个用户的信息
	 * @param name
	 * @return
	 */
	public static User get_a_user_by_name(String name){
		String UID = jc.hget("getuser", name);
		if(UID == null)	return null;		//查无此人
		return get_a_user_by_UID(Long.parseLong(UID));
	}
	
	/**
	 * 查询一个用户(登录时校验)是否在数据库中。
	 * 如果在，返回UID，否则返回0.
	 * @param name
	 * @return
	 */
	public static long is_user_in_DB(String name){
		String UID = jc.hget("getuser", name);
		return UID == null ? 0 : Long.parseLong(UID);
	}
	
	/**
	 * 通过UID得到用户名
	 * @param UID
	 * @return
	 */
	public static String get_user_name(long UID){
		return jc.hget("user:"+UID, "name");
	}
	
	/**
	 * 通过用户名得到UID
	 * @param name
	 * @return
	 */
	public static long get_user_UID(String name){
		return Long.parseLong(jc.hget("getuser", name));
	}
	
	/**
	 * Set<String> => Set<Long>的转换
	 * @param s
	 * @return
	 */
	public static Set<Long> change_set_type(Set<String> set){
		Set<Long> result = new HashSet<Long>();
		for(String s : set){
			result.add(Long.parseLong(s));
		}
		return result;
	}
	
	/**
	 * 推送：可能认识的人  =>	twitter·类三元闭包算法, 使用二度好友当做推荐对象.  =>   额外加入随机化。
	 * http://www.aboutyun.com/thread-17333-1-1.html
	 * https://www.quora.com/How-does-Twitters-follow-suggestion-algorithm-work
	 * https://zhuanlan.zhihu.com/p/20533434
	 * 
	 * ****以下：不一定要找出所有人。按照时间顺序找最新的30人就可以。毕竟用户的关注是按时间增长的。这样可以减少服务器分析压力。****
	 * 算法思想：找出UID所focus的所有人和votes, comment, trans。二度查找这些人的focus，votes，comment以及trans。对二度的这些zunionstore，然后设置expire过期时间。
	 * 之后score会增加。取最多的几个。如果不是UID和一度查找里边的UID，就显示出来。<注意这步操作放在前端执行>当然，为什么用并集呢？因为速度快，而且redis的并集有sum操作。这样的话，相当于实际上是交集了。因为交集的元素
	 * score会变得超高，酸爽！于是乎，这样还可以避免讨论二度的UID里边两两交集，复杂度超高->平方级别，而且有空集的现象还要讨论简直头疼......
	 * 
	 * 注意返回值是Set<String>.因为可以直接从缓存中取。所以String并没有转为Long。就这样吧。要不反而还要浪费时间复杂度。
	 * 
	 * @param UID
	 */
	public static Set<Long> get_probably_acquaintance(long UID){
		//查看是否有缓存～有的话直接取出来～～
		//注意second的意思是二度查找
		if(jc.exists("acquaintance_second{"+UID+"}"))	return change_set_type(jc.zrevrange("acquaintance:"+UID, 0, 20));		//返回20个。虽然可能只用得到5个。但是我们不能忽略重复的可能性。
		
		//时间倒序查找。每个取20个。		这里的first表示“一度查找”。
		//[在集群中不是同一个slot不能操作！所以必须把[]改成{}来标记slot！{}中的内容只要一样，就存在同一个slot中！！]
		//详见：https://my.oschina.net/u/2242064/blog/646771
		jc.zunionstore("acquaintance_first{"+UID+"}", "focus{"+UID+"}", "voted{"+UID+"}", "commented{"+UID+"}", "trans{"+UID+"}");	//一步操作进行。就是量太大了。其实可以把数量变小。找最新的几人。只不过麻烦，需要额外开辟多个set的副本。
		jc.expire("acquaintance_first{"+UID+"}", 24*60*60);		//保存一天。
		
		Set<Long> first = change_set_type(jc.zrange("acquaintance_first{"+UID+"}", 0, -1));
		//http://www.oschina.net/question/947956_2192487?p=1
		String[] argu = new String[first.size()];	//作为变长参数的参数传递。=> 如何把数组中的东西当参数传递给变长参数？=>直接传递数组就好。java会懂你的。～～
		int j = 0;
		for(long uid : first){
			//对于每个一度朋友，都缓存一次这个朋友的一度查找。然后我们会对所有一度朋友的一度查找进行union，形成我的二度查找。即寻找朋友的朋友最牛B的几个人。
			String uid_string = "acquaintance_first{"+uid+"}";
			if(!jc.exists(uid_string)){
				jc.zunionstore(uid_string, "focus{"+uid+"}", "voted{"+uid+"}", "commented{"+uid+"}", "trans{"+uid+"}");	//一步操作进行。就是量太大了。其实可以把数量变小。找最新的几人。只不过麻烦，需要额外开辟多个set的副本。
				jc.expire(uid_string, 24*60*60);		//保存一天。				
			}
			argu[j++] = uid_string; 
		}
		
		
		String[] argu2 = new String[first.size()];		//存新的转存的字符串～
		for(int i = 0; i < argu.length; i ++){
			//疯狂地从redis中取出
			Set<Tuple> set = jc.zrangeWithScores(argu[i], 0, -1);
			//提取uid
			String uid = argu[i].substring(argu[i].indexOf('{')+1, argu[i].indexOf('}'));
			//换个名字：
			argu2[i] = "acquaintance_first:"+uid+"{"+UID+"}";
			//然后换个名字存进和UID相同的槽中去
			Map<String, Double> map = new HashMap<String, Double>();
			for(Tuple t : set){
				map.put(t.getElement(), t.getScore());
			}
			jc.zadd(argu2[i], map);
		}
		
		jc.zunionstore("acquaintance_second{"+UID+"}", argu2);		//数组传递变长参数太棒了！！		//用不了。因为每个用户在不同的槽中，{uid}不同。所以最后一步无法这样。必须手动。、
		
		//得到二度查找，不够就扩充。
		Set<Long> ac = change_set_type(jc.zrevrange("acquaintance_second{"+UID+"}", 0, 20));
		long user_num = Long.parseLong(jc.get("UID"));		//得到用户总数
		while(ac.size() < 8){		//设定如果数目太少，那就随机推荐人吧。填充到10个就好了。毕竟全是陌生人。
			//防止随机生成的列表出现focus里边的人+自己的UID，即防止出现已经关注过的人+自己.
			long random_UID = (long)(Math.random() * (user_num-1)) + 1;		//+1防止出现0这样的UID
			//无此人 防止日后可能加入删除用户功能
			if(jc.sismember("UIDs", String.valueOf(random_UID)) == null)	continue;
			//是个新人，不是UID自身及其关注对象。
			if(random_UID != UID && jc.zrank("focus{"+UID+"}", String.valueOf(random_UID)) == null){
				ac.add(random_UID);
			}
		}
		
		//移除自己
		ac.remove(UID);
		//移除自己的focus
		ac.removeAll(change_set_type(jc.zrange("focus{"+UID+"}", 0, -1)));
		
		return ac;
		
	}
	
	/**
	 * 我关注的人也关注了他～ 
	 * @param UID => 我的UID
	 * @return
	 */
	public static List<Long> who_I_focus_also_focus_him(long UID, long dest_UID){
		List<Long> list = new ArrayList<>();
		Set<Long> my_focus = change_set_type(get_all_focus(UID));
		for(long focus : my_focus){
			if(jc.zscore("focus{"+focus+"}", String.valueOf(dest_UID)) != null){
				list.add(focus);
			}
		}
		return list;
	}
	
	public static void main(String[] args) {

//		User zxl = new User("zhengxiaolin", "123", 20);
//		User jxc = new User("jiangxicong", "123", 20);
//		Cluster.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
//		Cluster.add_a_user(jxc);
//		Cluster.add_an_article(new Article("today I bought a very good thing!", jxc.getUID(), 0, 0));		//0参数表示并非转发
//		Cluster.remove_an_article(1);
//		System.out.println(Cluster.get_an_article_score(3));
//		Cluster.vote_an_article(1, 3);
//		Cluster.remove_an_article(4);
//		Cluster.add_a_comment(new Comment("nice to see U!", 1, 3, 0));	//调用的时候，如果不是转别人的评论，就不需要填写。但是AID那个参数是必填的，因为全归属于那个文章。
//		Cluster.add_a_comment(new Comment("comment myself comment!", 1, 3, 1));	//调用的时候，如果不是转别人的评论，就不需要填写。但是AID那个参数是必填的，因为全归属于那个文章。
//		Cluster.remove_a_comment(10);
//		System.out.println(Cluster.get_an_article_score(3));
//		Cluster.vote_cancelled_oh_no(1, 3);
//		Cluster.focus_a_user(2, 1);
//		Cluster.focus_cancelled_oh_no(2, 1);
//		Cluster.flush_all();
		Cluster.get_all_keys();
		
	}

}
