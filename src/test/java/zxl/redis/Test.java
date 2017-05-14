package zxl.redis;

import java.util.ArrayList;
import java.util.List;

import zxl.bean.User;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		
		System.out.println(new User("haha"){
			{
				this.setPosition("China");
			}
		}.toJSON());
		
		System.out.println(JSONObject.toJSONString(new ArrayList<List<String>>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				this.add(new ArrayList<String>(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
						this.add("haha");
						this.add("hehe");
					}
				});
				this.add(new ArrayList<String>(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					{
						this.add("heihei");
					}
				});
			}
		}));
		
		
		

	}

}
