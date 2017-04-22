package zxl.spring0;

public class UserFactory {

	public static User static_create() {
		return new User("static method", 1);
	}
	
	public User normal_create() {
		return new User("member method", 0);
	}
	
}
