package zxl.servlets;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import zxl.redis.Cluster;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
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
//		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		System.out.println(username);
		String password = request.getParameter("password");
		System.out.println(password);
		long UID;
		if((UID = Cluster.is_user_in_DB(username)) == 0){
			System.out.println("haha");
			request.setAttribute("msg", "Your account is invalid!! Please reinput!!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
			request.getSession().setAttribute("LogInUID", UID);
			request.getSession().setAttribute("LogInusername", username);
			//以下统统放到DWR中去了！！
//			request.setAttribute("articles", Cluster.get_user_articles_num(UID));		//推文数量	
//			request.setAttribute("focus", Cluster.get_focus_num(UID));					//正在关注数量
//			request.setAttribute("fans", Cluster.get_fans_num(UID));					//关注者数量
//			request.setAttribute("main_page", Cluster.jc.hget("user:"+UID, "main_page"));			//主页图片
//			request.setAttribute("haha", Cluster.get_all_fans(5));		//设置Set<Long>进去Attribute中	//连json都不用打！！太棒了！在js端直接会变成数组object对象！
																		//所以到时候直接var set = <%= request.getAttribute("haha") %>;
																		//alert(set.length); alert(set[0]);  就好了！！
			request.getRequestDispatcher("/twitter_user.jsp").forward(request, response);
		}
//		Cookie cookie = new Cookie(username, password);
//		cookie.setMaxAge(1000);
//		response.addCookie(cookie);
//		response.sendRedirect("chat.jsp");
//		request.getRequestDispatcher("frame_HTML(2)/index.html").forward(request, response);	//forward不好弄。因为会造成跳转后的页面由于相对路径的原因变得css检索不到。
	}

}
