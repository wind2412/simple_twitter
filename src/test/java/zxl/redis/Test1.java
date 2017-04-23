package zxl.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class Test1 {
	
	private JedisCluster jc;
	
	public Test1() {
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
	
	@Test
	public void getUser(){
		assert jc.hget("user:1", "name") == "sb";
		assert jc.hget("user:1", "pass") == "123";
		assert jc.hget("user:1", "age") == "20";
	}
	
}
