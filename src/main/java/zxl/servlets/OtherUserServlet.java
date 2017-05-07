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
@WebServlet("/OtherUserServlet")
public class OtherUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OtherUserServlet() {
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
		long UID = Long.parseLong(String.valueOf(request.getSession().getAttribute("LogInUID")));
		System.out.println(UID);
		String username = String.valueOf(request.getSession().getAttribute("LogInusername"));		//这次要从session中取出来了。
		System.out.println(username);
		long other_UID = Long.parseLong(request.getParameter("usr"));	//假设点击别人的头像，会跳转为：/OtherUserServlet?usr=xxxx .		//那个usr的头像域中要保存他的UID和name.
		System.out.println("other_UID: " + other_UID);
		
		//保存在request中就好，不用session。
		//全局
		request.setAttribute("other_UID", other_UID);		
		
		//前方之风
		request.setAttribute("other_articles", Cluster.get_user_articles_num(other_UID));						//对方推文数量
		request.setAttribute("other_focus", Cluster.get_focus_num(other_UID));									//对方正在关注数量
		request.setAttribute("other_fans", Cluster.get_fans_num(other_UID));									//对方关注者数量
		request.setAttribute("other_main_page", Cluster.getJC().hget("user:"+other_UID, "main_page"));			//对方主页图片		(不一定用)
		request.setAttribute("did_I_focused_him", Cluster.focus_or_not(UID, other_UID));						//关注按钮的显示。true/false
		
		//左方之地
		request.setAttribute("other_name", username);
		System.out.println(JSONObject.toJSONString(Cluster.get_a_user(other_UID)));
		request.setAttribute("other_usr_info", JSONObject.toJSONString(Cluster.get_a_user(other_UID)));
//		request.setAttribute("other, o);
		
		
		request.getRequestDispatcher("/twitter.jsp").forward(request, response);
//		Cookie cookie = new Cookie(username, password);
//		cookie.setMaxAge(1000);
//		response.addCookie(cookie);
//		response.sendRedirect("chat.jsp");
//		request.getRequestDispatcher("frame_HTML(2)/index.html").forward(request, response);	//forward不好弄。因为会造成跳转后的页面由于相对路径的原因变得css检索不到。
	}

}
