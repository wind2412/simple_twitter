package zxl.bean;

public class User {
	
	private String name;
	private String pass;
	private long UID;
	private int age;
	private long time;		//user的加入社区时间
	
	private String main_page;		//个人主页
	private String portrait_path;	//头像(相对路径)

	public User(String name, String pass, int age, String main_page, String portrait_path) {
		super();
		this.name = name;
		this.pass = pass;
		this.age = age;
		this.main_page = main_page;
		this.portrait_path = portrait_path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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
	
	
	
	
}
