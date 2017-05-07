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
		if(Cluster.add_a_user(username, password) == 0){
			request.setAttribute("msg", "Your account is invalid!! Please reinput!!");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);
			request.getRequestDispatcher("/twitter.jsp").forward(request, response);
		}
//		Cookie cookie = new Cookie(username, password);
//		cookie.setMaxAge(1000);
//		response.addCookie(cookie);
//		response.sendRedirect("chat.jsp");
//		request.getRequestDispatcher("frame_HTML(2)/index.html").forward(request, response);	//forward不好弄。因为会造成跳转后的页面由于相对路径的原因变得css检索不到。
	}

}
