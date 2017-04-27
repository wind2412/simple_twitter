package zxl.redis;

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
		Cluster.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
		Cluster.add_a_user(zxl);				//***重复添加测试***
		Cluster.add_a_user(jxc);
		Cluster.add_a_user(ltg);
		Cluster.add_a_user(zfy);
		Cluster.add_a_user(wy);
		Cluster.add_an_article(new Article("null --By zhengxiaolin.", zxl.getUID(), 0, 0, null));		//0参数表示并非转发
		Cluster.add_an_article(new Article("the second blood! --By zhengxiaolin.", zxl.getUID(), 0, 0, null));		//0参数表示并非转发
		Cluster.add_an_article(new Article("omoshiroi --By jiangxicong.", jxc.getUID(), 0, 0, null));
		Cluster.add_an_article(new Article("mouth can't hold ouch!! --By litiange.", ltg.getUID(), 0, 0, null));
		Cluster.add_an_article(new Article("if U can take me with the trip~ --By zhangfangyuan.", zfy.getUID(), 0, 0, null));
		Cluster.add_an_article(new Article("T4ks --By wangyue.", wy.getUID(), 0, 0, null));
		Cluster.get_all_keys();
		get_all_scores();
	}
	
	/**
	 * 在此test中，得到用户和文章的所有分数
	 */
	private static void get_all_scores(){
		System.out.println("**************user scores*************");
		System.out.println("zhengxiaolin: " + Cluster.get_a_user_score(1));
		System.out.println("jiangxicong: " + Cluster.get_a_user_score(2));
		System.out.println("litiange: " + Cluster.get_a_user_score(3));
		System.out.println("zhangfangyuan: " + Cluster.get_a_user_score(4));
		System.out.println("wangyue: " + Cluster.get_a_user_score(5));
		System.out.println("************article scores************");
		System.out.println("article: 1, UID: " + Cluster.get_userID_of_an_article(1) + ", score: " + Cluster.get_an_article_score(1));
		System.out.println("article: 2, UID: " + Cluster.get_userID_of_an_article(2) + ", score: " + Cluster.get_an_article_score(2));
		System.out.println("article: 3, UID: " + Cluster.get_userID_of_an_article(3) + ", score: " + Cluster.get_an_article_score(3));
		System.out.println("article: 4, UID: " + Cluster.get_userID_of_an_article(4) + ", score: " + Cluster.get_an_article_score(4));
		System.out.println("article: 5, UID: " + Cluster.get_userID_of_an_article(5) + ", score: " + Cluster.get_an_article_score(5));
		System.out.println("article: 6, UID: " + Cluster.get_userID_of_an_article(6) + ", score: " + Cluster.get_an_article_score(6));
		System.out.println("**************************************");
	}
	
	
	private static void initialize_db() {
		
		//互相关注一波
		Cluster.focus_a_user(2, 1);
		Cluster.focus_a_user(1, 2);
		Cluster.focus_a_user(3, 4);
		Cluster.focus_a_user(4, 3);
		Cluster.focus_a_user(1, 5);
		
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
		Cluster.vote_an_article(1, 1); 		//zhengxiaolin 给自己的 1 文章点赞
		
		get_all_scores();
		
		//test
		assert Cluster.judge_voted(1, 1) == true;
		assert Cluster.judge_voted(2, 1) == false;
	}


	@Test
	public void testBasic(){
		assert jc.hget("user:1", "name").equals("zhengxiaolin");
		assert jc.hget("user:1", "pass").equals("123");
		assert jc.hget("user:1", "age").equals("20");
	} 
	
}
