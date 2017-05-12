package zxl.servlets;



import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

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
//		String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");		//文本框全是中文，不用再转码了。需要转码的只有在js中直接指定url的。。会变成iso-8859-1
		String username = request.getParameter("username");		//文本框輸入的東西竟然直接是中文....不用再转码了。。。。
		System.out.println(username);
		String password = request.getParameter("password");
		System.out.println(password);
		long UID;
		if((UID = Cluster.is_user_in_DB(username)) == 0 && Cluster.get_user_password(username).equals(password)){
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
			
			//URLEncoder好像就是把一个本来是UTF-8编码的东西，在每个字节前加上%号，充当URL的query的网络编码格式啊。。实际上还是utf-8.但是又不同于utf-8了。
			//因为我设置twitter_focus.jsp接收西文的iso-8859-1网络编码query，因此变成8859-1把。
			//而且query的字符编码也不是纯的iso-8859-1。。。其实是要在每个字节前也要加上%的。。。。(笑)
			request.getRequestDispatcher("/twitter_focus.jsp?usr="+/*URLEncoder.encode(username)*/URLEncoder.encode(new String(username.getBytes(), "iso-8859-1"))+"&timestamp="+new Date().getTime()).forward(request, response);	//如果这里不加上query，那么twitter_user.jsp会由于没有query，会再跳一次页面。
																										//这样的话，所有的ajax会请求两次。如果碰到不加query后边调用由于某个值是null就会出错的情况，
																										//服务器会报异常。server error。解决方法就是这里加上username，然后jsp把错误情况过滤掉。
				//注意：加上query时间戳为了防止ajax缓存
		}
	}

}
