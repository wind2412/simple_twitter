package zxl.servlets;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zxl.redis.Cluster;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String username = request.getParameter("username");
		System.out.println(username);
		String password = request.getParameter("password");
		System.out.println(password);
		long UID;
		if((UID = Cluster.add_a_user(username, password)) != 0){	//数据库没有那个用户名 就可以建立。	
			request.getSession().setAttribute("LogInUID", UID);		//因为届时会登录一个账号，查看另一个人。因此只用一个uid是不行的。
			request.getSession().setAttribute("LogInusername", username);
			request.setAttribute("articles", 0);		//推文数量
			request.setAttribute("focus", 0);			//正在关注数量
			request.setAttribute("fans", 0);			//关注者数量
			request.getRequestDispatcher("/twitter.jsp").forward(request, response);
		}else{			
			request.setAttribute("msg", "Your account has been registered! Please reinput or log in!!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);		//详见：http://ask.csdn.net/questions/182199 很漂亮！！
		}
//		Cookie cookie = new Cookie(username, password);
//		cookie.setMaxAge(1000);
//		response.addCookie(cookie);
//		response.sendRedirect("chat.html");
//		request.getRequestDispatcher("frame_HTML(2)/index.html").forward(request, response);	//forward不好弄。因为会造成跳转后的页面由于相对路径的原因变得css检索不到。
	}

}
