package zxl.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import zxl.bean.Article;
import zxl.bean.Comment;
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
		user.setTime(System.currentTimeMillis()/1000);
		jc.hset("user:"+user.getUID(), "name", user.getName());
		jc.hset("user:"+user.getUID(), "pass", user.getPass());
		jc.hset("user:"+user.getUID(), "age", String.valueOf(user.getAge()));
		jc.hset("user:"+user.getUID(), "time", String.valueOf(user.getTime()));
	}
	
	public void add_an_article(Article article) throws IOException{
		long AID = jc.incr("AID");
		article.setPath("articles/article_" + AID);
		article.setTime(System.currentTimeMillis()/1000);
		article.add_article_to_disk();		//持久化文章内容到硬盘上			//图片功能再议
		jc.hset("article:"+article.getAID(), "path", article.getPath());
		jc.hset("article:"+article.getAID(), "UID",  String.valueOf(article.getUID()));
		if(article.getTrans_AID() != 0)		jc.hset("article:"+article.getAID(), "trans_AID",  String.valueOf(article.getTrans_AID()));
		jc.hset("article:"+article.getAID(), "time", String.valueOf(article.getTime()));
	}
	
	public void add_a_comment(Comment comment) throws IOException{
		long CID = jc.incr("CID");
		comment.setPath("comments/comment_" + CID);
		comment.setTime(System.currentTimeMillis()/1000);
		comment.add_comment_to_disk();		//持久化文章内容到硬盘上			//图片功能再议
		jc.hset("comment:"+comment.getCID(), "path", comment.getPath());
		jc.hset("comment:"+comment.getCID(), "UID", String.valueOf(comment.getUID()));
		jc.hset("comment:"+comment.getCID(), "AID", String.valueOf(comment.getAID()));
		jc.hset("comment:"+comment.getCID(), "time", String.valueOf(comment.getTime()));
	}	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		Cluster c = new Cluster();
//		c.get_all_keys();
	}

}
