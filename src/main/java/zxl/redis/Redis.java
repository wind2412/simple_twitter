package zxl.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Redis {
	
	public static String os = System.getProperty("os.name");

	public static String call_shell(String command, boolean bg) throws IOException, InterruptedException{
//		ProcessBuilder pb = new ProcessBuilder(command);
//		if(dir != null)	pb.directory(new File(dir));
//		Process proc = pb.start();
		Process proc = Runtime.getRuntime().exec(command);
		if(!bg){
			if(proc.waitFor() != 0)	System.out.println("wrong in shell.");
			String s = null;
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while((s = br.readLine()) != null){
				sb.append(s).append("\r\n");
			}
			br.close();
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public static void redis_cluster() throws IOException, InterruptedException{
		if(Redis.os.startsWith("Mac")){
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6382/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6379/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6380/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6381/redis.conf",  true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6383/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6384/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6385_add/redis.conf", true));
			System.out.println(call_shell("/usr/local/bin/redis-server cluster_test/6386_add_slave/redis.conf", true));			
		} else {
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6382\\redis2.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6379\\redis2.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6380\\redis2.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6381\\redis2.conf",  true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6383\\redis2.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6384\\redis2.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6385_add\\redis.conf", true));
			System.out.println(call_shell("\"C:\\Program Files\\Redis\\redis-server.exe\" cluster_test\\6386_add_slave\\redis.conf", true));						
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Redis.redis_cluster();	//open redis cluster~
//		System.out.println(System.getProperty("os.name"));
//		System.out.println(call_shell("ls -l cluster_test", false));
//		System.out.println(call_shell("./cluster_test/redis-trib.rb create --replicas 1 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384", false));
		
		
	}

}
