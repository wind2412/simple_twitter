package zxl.redis;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.JedisCluster;
import zxl.bean.Article;
import zxl.bean.User;

public class ClusterTest {
	
	private static JedisCluster jc;
	
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
		initialize_db();
		
	}
	
	//图片功能未测试
	private static void connect() {

		//调用Cluster的任一方法时候，Cluster类会初始化，由于static块在最前边，所以会自动运行，即客户端会自动连接。
		jc = Cluster.getJC();
		Cluster.flush_all(); 		//清空测试数据库
		User zxl = new User("zhengxiaolin", "123", 20);
		User jxc = new User("jiangxicong", "123", 20);
		User ltg = new User("litiange", "123", 19);
		User zfy = new User("zhangfangyuan", "123", 19);
		User wy = new User("wangyue", "123", 20);
		User user1 = new User("user1", "123", 11);		//加上10个随机用户  测试推荐好友算法
		User user2 = new User("user2", "123", 12);
		User user3 = new User("user3", "123", 13);
		User user4 = new User("user4", "123", 14);
		User user5 = new User("user5", "123", 15);
		User user6 = new User("user6", "123", 16);
		User user7 = new User("user7", "123", 17);
		User user8 = new User("user8", "123", 18);
		User user9 = new User("user9", "123", 19);
		User user10 = new User("user10", "123", 20);
		Cluster.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
		Cluster.add_a_user(zxl);				//***重复添加测试***
		Cluster.add_a_user(jxc);
		Cluster.add_a_user(ltg);
		Cluster.add_a_user(zfy);
		Cluster.add_a_user(wy);
		Cluster.add_a_user(user1);
		Cluster.add_a_user(user2);
		Cluster.add_a_user(user3);
		Cluster.add_a_user(user4);
		Cluster.add_a_user(user5);
		Cluster.add_a_user(user6);
		Cluster.add_a_user(user7);
		Cluster.add_a_user(user8);
		Cluster.add_a_user(user9);
		Cluster.add_a_user(user10);
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
	
	
	private static void initialize_db() {
		
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
		
		System.out.println("***" + Cluster.get_probably_acquaintance(1));
		
		Cluster.focus_cancelled_oh_no(1, 2);
		
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
		
		//开始评论
		Cluster.add_an_article(new Article("comment myself article~~ --By zhengxiaolin", 1, 1, 1, false, null));	//7号文章回复1号
		get_all_scores();
		
		Cluster.add_an_article(new Article("comment your comment. --By jiangxicong", 2, 1, 7, false, null));		//8号文章回复7号
		get_all_scores();
		
		Cluster.add_an_article(new Article("comment your comment! --By zhengxiaolin", 1, 1, 8, false, null));		//9号文章回复8号
		get_all_scores();
		
		Cluster.remove_an_article(9);																				//移除9号
		get_all_scores();
		
		//开始转发
		Cluster.add_an_article(new Article("trans myself article~~ --By zhengxiaolin", 1, 2, 1, false, null));		//10号文章转发1号
		get_all_scores();
		
		Cluster.add_an_article(new Article("trans jxc's article. --By litinage", 3, 2, 8, false, null));			//11号文章转发8号
		get_all_scores();

		Cluster.remove_an_article(11); 																		//移除11号文章
		get_all_scores();

		Cluster.get_all_keys();
		
		List<List<Article>> all_comments = Cluster.get_article_comments_context(1, 0);
		for(List<Article> l : all_comments){
			for(Article a : l){
				System.out.println("AID:" + a.getAID() + " ... " + "trans_AID:" + a.getTrans_AID());
			}
		}
		
	}


	@Test
	public void testBasic(){
		assert jc.hget("user:1", "name").equals("zhengxiaolin");
		assert jc.hget("user:1", "pass").equals("123");
		assert jc.hget("user:1", "age").equals("20");
	} 
	
}
