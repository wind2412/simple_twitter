package zxl.servlets;



import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String new_username = request.getParameter("username");									//新的username
		System.out.println(new_username);														//上传的图片最后再说
		String introduction = request.getParameter("introduction");								//introduction
		System.out.println(introduction);
		String nationality = request.getParameter("nationality");								//nationality
		System.out.println(nationality);
		String website = request.getParameter("website");										//website
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

			response.setHeader("Refresh", "1;URL=/twitter_proj/twitter_focus.jsp?usr="+new_username+"&timestamp="+new Date().getTime());
		}
		else {
			User old_user = new User(old_username);
			old_user.setUID(UID);								//别忘了set_UID
			old_user.setIntroduction(introduction);
			old_user.setWebsite(website);
			old_user.setPosition(nationality);
			Cluster.upgrade_user_settings(old_user);
			response.setHeader("Refresh", "1;URL=/twitter_proj/twitter_focus.jsp?usr="+old_username+"&timestamp="+new Date().getTime());
		}
	}

}
