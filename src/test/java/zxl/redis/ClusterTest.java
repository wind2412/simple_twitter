package zxl.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class ClusterTest {
	
	private JedisCluster jc;
	
	public ClusterTest() {
		//连接数据库服务器
		connect();
		//先要清空数据库。然后调用此方法初始化数据.
		initialize_db();
	
	}
	
	private void connect() {

		Set<HostAndPort> hosts = new HashSet<HostAndPort>();
		hosts.add(new HostAndPort("localhost", 6379));
		hosts.add(new HostAndPort("localhost", 6380));
		hosts.add(new HostAndPort("localhost", 6381));
		hosts.add(new HostAndPort("localhost", 6382));
		hosts.add(new HostAndPort("localhost", 6383));
		hosts.add(new HostAndPort("localhost", 6384));
		hosts.add(new HostAndPort("localhost", 6385));
		hosts.add(new HostAndPort("localhost", 6386));			
		jc = new JedisCluster(hosts);
	
	}
	
	private void initialize_db() {
		
	}


	@Test
	public void getUser(){
		assert jc.hget("user:1", "name") == "sb";
		assert jc.hget("user:1", "pass") == "123";
		assert jc.hget("user:1", "age") == "20";
	}
	
}
