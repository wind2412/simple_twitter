package zxl.redis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class Cluster {

	private JedisCluster jc;
	
	public Cluster() {
		
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
	
	public void add_a_user(){
		long UID = jc.incr("UID");
		
	}
	
	public static void main(String[] args) {

		Cluster c = new Cluster();
		
	}

}
