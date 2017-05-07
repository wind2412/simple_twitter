package zxl.bean;

import com.alibaba.fastjson.JSONObject;

public class User {
	
	//突然发现password是用不到的......因为直接存放在数据库中，直接和数据库交流就可以，这里的User只是临时用于显示[左方之地]效果，因此好像并不需要。
	
	private String name;
	
	private long UID;
	private long time;		//user的加入社区时间
	
	private int age;
	private String main_page;		//main_page的图片
	private String portrait_path;	//头像(相对路径)
	private String introducton;		//自我介绍
	private String position;		//地点
	private String website;			//个人主页

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUID() {
		return UID;
	}

	public void setUID(long UID) {
		this.UID = UID;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public String getMain_page() {
		return main_page;
	}

	public void setMain_page(String main_page) {
		this.main_page = main_page;
	}

	public String getPortrait_path() {
		return portrait_path;
	}

	public void setPortrait_path(String portrait_path) {
		this.portrait_path = portrait_path;
	}
	
	public String getIntroducton() {
		return introducton;
	}

	public void setIntroducton(String introducton) {
		this.introducton = introducton;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String toJSON(){
		return JSONObject.toJSONString(this);
	}
	
	
}
