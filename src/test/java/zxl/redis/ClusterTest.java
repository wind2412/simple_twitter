package zxl.redis;

import java.io.IOException;

import org.junit.Test;

import redis.clients.jedis.JedisCluster;

public class ClusterTest {
	
	private static JedisCluster jc;
	
	static {
		//先要更换成为测试数据库。
		try {
			KillRedis.main(null);		//杀死目前的数据库进程。
			Thread.sleep(100);			//需要等待。否则还没杀完，就又开启了。
			RedisTest.main(null); 		//开启测试数据库服务器
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		//连接数据库服务器
		try {
			Thread.sleep(100);		//需要等待。否则服务还没开启，客户端就去连接了，肯定错误。
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connect();
		//然后调用此方法初始化数据.
		initialize_db();
		
	}
	
	//图片功能未测试
	private static void connect() {

		//调用Cluster的任一方法时候，Cluster类会初始化，由于static块在最前边，所以会自动运行，即客户端会自动连接。
		jc = Cluster.getJC();
		jc.set("haha", "11");
		System.out.println(jc.get("haha"));
//		Cluster.flush_all(); 		//清空测试数据库
//		User zxl = new User("zhengxiaolin", "123", 20);
//		User jxc = new User("jiangxicong", "123", 20);
//		User ltg = new User("litiange", "123", 19);
//		User zfy = new User("zhangfangyuan", "123", 19);
//		User wy = new User("wangyue", "123", 20);
//		Cluster.add_a_user(zxl);	//函数内部会自动赋给zxl一个UID
//		Cluster.add_a_user(zxl);				//***重复添加测试***
//		Cluster.add_a_user(jxc);
//		Cluster.add_a_user(ltg);
//		Cluster.add_a_user(zfy);
//		Cluster.add_a_user(wy);
//		Cluster.add_an_article(new Article("null --By zhengxiaolin.", zxl.getUID(), 0, 0, null));		//0参数表示并非转发
//		Cluster.add_an_article(new Article("the second blood! --By zhengxiaolin.", zxl.getUID(), 0, 0, null));		//0参数表示并非转发
//		Cluster.add_an_article(new Article("omoshiroi --By jiangxicong.", jxc.getUID(), 0, 0, null));
//		Cluster.add_an_article(new Article("mouth can't hold ouch!! --By litiange.", ltg.getUID(), 0, 0, null));
//		Cluster.add_an_article(new Article("if U can take me with the trip~ --By zhangfangyuan.", zfy.getUID(), 0, 0, null));
//		Cluster.add_an_article(new Article("T4ks --By wangyue.", wy.getUID(), 0, 0, null));
		
	}
	
	
	
	private static void initialize_db() {
		
	}


	@Test
	public void testBasic(){
		assert jc.hget("user:1", "name") == "zhengxiaolin";
		assert jc.hget("user:1", "pass") == "123";
		assert jc.hget("user:1", "age") == "20";
	} 
	
}
