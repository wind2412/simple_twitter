package zxl.servlets;



import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import zxl.bean.User;
import zxl.redis.Cluster;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String old_username = (String)request.getSession().getAttribute("LogInusername");		//旧的username
		System.out.println(old_username);
		long UID = Long.parseLong(String.valueOf(request.getSession().getAttribute("LogInUID")));		//UID
		System.out.println(UID);
		
		//获取request.getParameter()：注意，因为Http请求传输时将url以ISO-8859-1编码，服务器收到字节流后默认会以ISO-8859-1编码来解码成字符流（造成中文乱码）
		//详见http://blog.csdn.net/wzygis/article/details/50964864
		
		String new_username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");  		//新的username
		System.out.println(new_username);														//上传的图片最后再说
		String introduction = new String(request.getParameter("introduction").getBytes("iso-8859-1"), "utf-8");		//introduction
		System.out.println(introduction);
		String nationality = new String(request.getParameter("nationality").getBytes("iso-8859-1"), "utf-8");		//nationality
		System.out.println(nationality);
		String website = new String(request.getParameter("website").getBytes("iso-8859-1"), "utf-8");				//website
		System.out.println(website);
		
		if(!new_username.equals("")){				//如果用户没有填写这一项的话，那就不改名了。
			request.getSession().setAttribute("LogInusername", new_username);	//改成新的username

			Cluster.change_user_name(UID, old_username, new_username); 		//改名 并且修改数据库所有相关值
			User new_user = new User(new_username);
			new_user.setUID(UID);								//别忘了set_UID
			new_user.setIntroduction(introduction);
			new_user.setWebsite(website);
			new_user.setPosition(nationality);
			Cluster.upgrade_user_settings(new_user);	 		//更新信息

//			System.out.println("iso-8859-1 encoding :" + new String(new_username.getBytes("utf-8"), "iso-8859-1"));
//			System.out.println("utf-8 encoding: "+new_username);
			
			//【还是这URLEncoder好使啊......】
			response.setHeader("Refresh", "1;URL=/twitter_proj/twitter_focus.jsp?usr="+URLEncoder.encode(new_username)+"&timestamp="+new Date().getTime());
		}
		else {
			User old_user = new User(old_username);
			old_user.setUID(UID);								//别忘了set_UID
			old_user.setIntroduction(introduction);
			old_user.setWebsite(website);
			old_user.setPosition(nationality);
			Cluster.upgrade_user_settings(old_user);
			response.setHeader("Refresh", "1;URL=/twitter_proj/twitter_focus.jsp?usr="+URLEncoder.encode(old_username)+"&timestamp="+new Date().getTime());
		}
	}

}
