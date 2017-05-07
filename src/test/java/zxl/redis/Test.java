package zxl.redis;

import zxl.bean.User;

public class Test {

	public static void main(String[] args) {
		
		System.out.println(new User("haha"){
			{
				this.setPosition("China");
			}
		}.toJSON());

	}

}
