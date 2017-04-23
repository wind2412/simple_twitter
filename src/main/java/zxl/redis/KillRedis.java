package zxl.redis;

import java.io.IOException;

public class KillRedis {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		if(Redis.os.startsWith("Mac")){
			System.out.println(Redis.call_shell("pkill -15 redis-sentinel", false));	//kill所有redis-sentinel				
			System.out.println(Redis.call_shell("pkill -15 redis-server", false));	//kill所有redis-server				
		} else {
			System.out.println(Redis.call_shell("tskill redis-sentinel", false));
			System.out.println(Redis.call_shell("tskill redis-server", false));
		}
		
	}

}
