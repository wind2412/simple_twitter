package zxl.spring0;


import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class App {

//	@Resource
//	User user = new User();

	@Test
	public void testIOC1(){
		//对象交给Spring的IOC容器
		Resource resource = new ClassPathResource("zxl/spring0/applicationContext.xml");	//maven项目的默认路径就是main/java了.....直接写包中的相对路径就好...
		//创建IOC容器(Bean工厂)  IOC容器 = 工厂类 + applicationContext.xml.
		BeanFactory factory = new XmlBeanFactory(resource);
		//直接从容器中获取
		User user = (User)factory.getBean("user");	//小写的......因为配置的<bean id="user"....
		User user1 = (User)factory.getBean("user");	
		
		System.out.println(user + "  " + user1);
	}
	
	@Test
	public void testIOC2(){
		//得到IOC容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("zxl/spring0/applicationContext.xml");
		//直接从容器中获取
		User user = (User)ac.getBean("user");
		User user1 = (User)ac.getBean("user");
		
		System.out.println(user + " " + user1);
	}
	
}
