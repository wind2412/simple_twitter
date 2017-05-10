package zxl.redis;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.JedisCluster;
import zxl.bean.Article;
import zxl.bean.TimeLineNode;
import zxl.bean.User;

public class ClusterTest {
	
	private static JedisCluster jc = Cluster.jc;
	
	static {
		//先要更换成为测试数据库。
//		try {
//			KillRedis.main(null);		//杀死目前的数据库进程。
//			Thread.sleep(100);			//需要等待。否则还没杀完，就又开启了。			//这里的等待时间竟然只需要100ms！！
//			RedisTest.main(null); 		//开启测试数据库服务器
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}		
//		//连接数据库服务器
//		try {
//			Thread.sleep(2000);		//需要等待。否则服务还没开启，客户端就去连接了，肯定错误。		//卧槽！！原来的CLUSTER DOWN居然问题在这里！！ 这里即使等待1s也不行！！开不开！！必须要2s！！！
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		connect();
		try {
			initialize_db();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	//图片功能未测试
	private static void connect() {

		//调用Cluster的任一方法时候，Cluster类会初始化，由于static块在最前边，所以会自动运行，即客户端会自动连接。
		Cluster.flush_all(); 		//清空测试数据库
		User zxl = new User("zhengxiaolin");
		User jxc = new User("jiangxicong");
		User ltg = new User("litiange");
		User zfy = new User("zhangfangyuan");
		User wy = new User("wangyue");
		User user1 = new User("user1");		//加上10个随机用户  测试推荐好友算法
		User user2 = new User("user2");
		User user3 = new User("user3");
		User user4 = new User("user4");
		User user5 = new User("user5");
		User user6 = new User("user6");
		User user7 = new User("user7");
		User user8 = new User("user8");
		User user9 = new User("user9");
		User user10 = new User("user10");
		zxl.setUID(Cluster.add_a_user(zxl.getName(), "123"));	//函数内部会自动赋给zxl一个UID
		Cluster.add_a_user(zxl.getName(), "123");				//***重复添加测试***
		{
			zxl.setIntroduction("I am a good person. I am a good person. I am a good person.");
			zxl.setPosition("China");
			zxl.setWebsite("wind2412.github.io");
			zxl.setPortrait_path("portraits/head_"+zxl.getUID()+".jpg");
			Cluster.upgrade_user_settings(zxl);
		}
		jxc.setUID(Cluster.add_a_user(jxc.getName(), "123"));
		{
			jxc.setIntroduction("蛤蛤.");
			jxc.setPosition("欧摩西裸衣市");
			jxc.setWebsite("youngerJiang.twitter.com");
			jxc.setPortrait_path("portraits/head_"+jxc.getUID()+".jpg");			
			Cluster.upgrade_user_settings(jxc);
		}
		ltg.setUID(Cluster.add_a_user(ltg.getName(), "123"));
		zfy.setUID(Cluster.add_a_user(zfy.getName(), "123"));			//这些方法全都要改！！因为UID并没有设置！所以new Article(zxl.getUID()的时候getUID就是0了！！！
		wy.setUID(Cluster.add_a_user(wy.getName(), "123"));
		user1.setUID(Cluster.add_a_user(user1.getName(), "123"));
		user2.setUID(Cluster.add_a_user(user2.getName(), "123"));
		user3.setUID(Cluster.add_a_user(user3.getName(), "123"));
		user4.setUID(Cluster.add_a_user(user4.getName(), "123"));
		user5.setUID(Cluster.add_a_user(user5.getName(), "123"));
		user6.setUID(Cluster.add_a_user(user6.getName(), "123"));
		user7.setUID(Cluster.add_a_user(user7.getName(), "123"));
		user8.setUID(Cluster.add_a_user(user8.getName(), "123"));
		user9.setUID(Cluster.add_a_user(user9.getName(), "123"));
		user10.setUID(Cluster.add_a_user(user10.getName(), "123"));
		Cluster.add_an_article(new Article("null --By zhengxiaolin.", zxl.getUID(), 0, 0, false, null));		//0参数表示并非转发
		Cluster.add_an_article(new Article("the second blood! --By zhengxiaolin.", zxl.getUID(), 0, 0, false, null));		//0参数表示并非转发
		Cluster.add_an_article(new Article("omoshiroi --By jiangxicong.", jxc.getUID(), 0, 0, false, null));
		Cluster.add_an_article(new Article("mouth can't hold ouch!! --By litiange.", ltg.getUID(), 0, 0, false, null));
		Cluster.add_an_article(new Article("if U can take me with the trip~ --By zhangfangyuan.", zfy.getUID(), 0, 0, false, null));
		Cluster.add_an_article(new Article("T4ks --By wangyue.", wy.getUID(), 0, 0, false, null));
		Cluster.get_all_keys();
		get_all_scores();
	}
	
	/**
	 * 在此test中，得到用户和文章的所有分数
	 * 假定至少有一个用户。否则会崩溃。
	 */
	private static void get_all_scores(){
		System.out.println("**************user scores*************");
		Set<String> user_names = jc.hkeys("pass");
		for(String name : user_names){
			System.out.println(name + ": " + Cluster.get_a_user_score(Cluster.get_user_UID(name)));			
		}
		System.out.println("************article scores************");
		List<String> AIDs = jc.sort("score");
		for(String aid : AIDs){
			System.out.println("article: "+aid+", UID: " + Cluster.get_userID_of_an_article(Long.parseLong(aid)) + ", score: " + Cluster.get_an_article_score(Long.parseLong(aid)));
		}
		System.out.println("**************************************");
	}
	
	
	private static void initialize_db() throws InterruptedException {
		
		//互相关注一波
		Cluster.focus_a_user(2, 1);
		Cluster.focus_a_user(1, 2);
		Cluster.focus_a_user(3, 4);
		Cluster.focus_a_user(4, 3);
		Cluster.focus_a_user(1, 5);
		Cluster.focus_a_user(1, 4);
		Cluster.focus_a_user(1, 3);
		Cluster.focus_a_user(6, 5);
		Cluster.focus_a_user(5, 6);
		Cluster.focus_a_user(3, 7);
		Cluster.focus_a_user(4, 7);
		Cluster.focus_a_user(2, 8);
		
		Cluster.get_all_keys();
		
		//推送可能认识的人
		System.out.println("***" + Cluster.get_probably_acquaintance(1));
		System.out.println("***" + Cluster.get_probably_acquaintance(2));
		Cluster.focus_cancelled_oh_no(1, 2);		//取消了一波关注
		
		get_all_scores();
		
		//test
		assert Cluster.focus_or_not(1, 2) == false;
		assert Cluster.focus_or_not(2, 1) == true;
		assert Cluster.focus_or_not(3, 4) == true;
		assert Cluster.focus_or_not(4, 3) == true;
		assert Cluster.focus_or_not(1, 5) == true;
		assert Cluster.focus_or_not(5, 1) == false;
		
		//开始点赞
//		Cluster.vote_an_article(1, 1); 		//zhengxiaolin 给自己的 1 文章点赞
//		
//		get_all_scores();
//		
//		//test
//		assert Cluster.judge_voted(1, 1) == true;
//		assert Cluster.judge_voted(2, 1) == false;
		
		int sleepsec = 1;		//更改这个就可以修改时间间隔。一般如果想要时间间隔，因为内部时间time是除以1000的，因此设置为1000就好。
		
		//开始评论
		Cluster.add_an_article(new Article("comment myself article~~ --By zhengxiaolin", 1, 1, 1, false, null));	//7号文章回复1号
		get_all_scores();
		Thread.sleep(sleepsec);		//间隔一秒钟，模拟时间的不同。否则zrevrange里边的时间都是一样。。只能按照字典排序了。。。
		
		Cluster.add_an_article(new Article("comment your comment. --By jiangxicong", 2, 1, 7, false, null));		//8号文章回复7号
		get_all_scores();
		Thread.sleep(sleepsec);
		
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 8, false, null));		//9号文章回复8号
		get_all_scores();
		Thread.sleep(sleepsec);
		
//		Cluster.remove_an_article(9);																				//移除9号
//		get_all_scores();
		
		//开始转发
		Cluster.add_an_article(new Article("trans myself article~~ --By zhengxiaolin", 1, 2, 1, false, null));		//10号文章转发1号
		get_all_scores();
		Thread.sleep(sleepsec);
		
		Cluster.add_an_article(new Article("trans jxc's article. --By litinage", 3, 2, 8, false, null));			//11号文章转发8号
		get_all_scores();
		Thread.sleep(sleepsec);

		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 7, false, null));		//12号文章回复7号
		Thread.sleep(sleepsec);
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 12, false, null));		//13号文章回复12号
		Thread.sleep(sleepsec);

		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 7, false, null));		//14号文章回复7号
		Thread.sleep(sleepsec);
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 14, false, null));		//15号文章回复14号
		Thread.sleep(sleepsec);
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 14, false, null));		//16号文章回复14号
		Thread.sleep(sleepsec);
		
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 7, false, null));		//17号文章回复7号
		Thread.sleep(sleepsec);

		
		Cluster.remove_an_article(11); 																		//移除11号文章
		get_all_scores();

		Cluster.get_all_keys();
		
		//得到所有评论
		List<List<Article>> all_comments = Cluster.get_article_comments_context(7, 0);
		for(List<Article> l : all_comments){
			for(Article a : l){
				System.out.println("AID:" + a.getAID() + " ... " + "trans_AID:" + a.getTrans_AID());
			}
			System.out.println();
		}
		
		//得到uid的时间线
		List<TimeLineNode> all_timeline = Cluster.get_timeline_chains(2, 0);
		for(TimeLineNode tln : all_timeline){
			if(tln.getType() == 0)  System.out.println("your focus " + tln.getUID() + " posted: " + tln.getTarget_AID());		//少！！修复bug。
			else if(tln.getType() == 1)  System.out.println("your focus " + tln.getUID() + " replied: " + tln.getTarget_AID());
			else if(tln.getType() == 2)  System.out.println("your focus " + tln.getUID() + " transed: " + tln.getTarget_AID());
			else System.out.println("hehe");
		}
		
		//我关注的人也关注了他  这些人都有谁
		List<Long> who_I_focus_also_focus_him = Cluster.who_I_focus_also_focus_him(1, 7);
		System.out.println(who_I_focus_also_focus_him);
		
	}


	@Test
	public void testBasic(){
		assert jc.hget("user:1", "name").equals("zhengxiaolin");
		assert jc.hget("user:1", "pass").equals("123");
		assert jc.hget("user:1", "age").equals("20");
	} 
	
}
