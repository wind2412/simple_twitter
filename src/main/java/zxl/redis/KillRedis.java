package zxl.redis;

import java.io.IOException;

public class KillRedis {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println(Redis.call_shell("pkill -15 redis-server", false));	//kill所有redis-server	
		
	}

}
