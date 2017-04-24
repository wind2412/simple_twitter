package zxl.bean;

public class User {
	
	private String name;
	private String pass;
	private long UID;
	private int age;
	private long time;		//user的加入社区时间

	public User(String name, String pass, int age) {
		super();
		this.name = name;
		this.pass = pass;
		this.age = age;
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
	
	
	
	
}
