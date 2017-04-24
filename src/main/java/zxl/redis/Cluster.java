package zxl.redis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
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
		long UID = jc.incr("UID");
		user.setUID(UID);
		jc.hset("user:"+user.getUID(), "name", user.getName());
		jc.hset("user:"+user.getUID(), "pass", user.getPass());
		jc.hset("user:"+user.getUID(), "age", String.valueOf(user.getAge()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		Cluster c = new Cluster();
//		c.add_a_user(new User("sb", "123", 20));
//		c.get_all_keys();
	}

}
