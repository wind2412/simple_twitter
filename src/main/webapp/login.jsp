<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+"twitter_proj/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<title>LogIn</title>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta name="description" content="" />
<meta name="copyright" content="" />
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />                  
<link rel="stylesheet" type="text/css" href="style.css" media="all" />                          
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/kickstart.js"></script>                                  
<script type="text/javascript">
	
	var username = '<%= request.getAttribute("username")%>';
	var msg = "<%= request.getAttribute("msg") %>";		//虽然这里的注释诡异地变成了蓝色。。但是它是对的。。可以消除内部英文一撇被解释成为单引号的问题。。6666！！
														//太有意思了这里！！！！！卧槽！！！我在msg中设定的是:Sorry...There's not any service here...结果到这里原先写的是
														//msg='....',结果把一撇的分隔符变成单引号了！！结果js硬是显示不出来。。。	
	function checkUsernameLength(){
		//alert("fhhf hf");
		var usr = document.getElementById("usr");
		var psw = document.getElementById("psw");
		if(usr.value.trim().length == 0 || psw.value.trim().length == 0){
			alert("不可以输入空用户名或密码！");
			return false;	//千万要return！！！否则的话，表单即使校验失败也会消失！因为还是会提交！所以只有返回false并在onclick中修改成return才可！！
			//document.writeln("<strong style='color:red;font-size:15'>"+"不可以不输入用户名！"+"</strong>");
		} 
		return true;	
	}

	</script>
	
</head><body>

<!-- Menu Horizontal -->

<div class="grid">
	
<!-- ===================================== END HEADER ===================================== -->
<form action = "/twitter_proj/LogInServlet" method = "post">
<div class="col_12">
	<div align="center">
	<h1 style="font-family:YaHeiConsola" >登录页面</h1>	
	</div>
	<br><br><br>
	<div align="center">
    <script type="text/javascript">	//这一段还没添加上去。
    	if(msg != "null"){
    		document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>" + msg + "</p>");
    	}
		else if(username == "null" || username.length == 0) {
			document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>请您登录！</p>");
		}
		else{
			var output = "welcome，"+username+"！";
			document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>"+output+"</p>");
		}
    </script>
    </div>
	
	<div class="col_3" style="height:50px">
    </div><br><br><br>
	<ul class="icons">
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="username" id="usr" placeholder="Username" style="width:400px;height=100px"></li><br>
    <div class="col_3" style="height:20px">
    </div><br><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="password" name="password" id="psw" placeholder="Password" style="width:400px;height=100px"></li>
	<div class="col_3" style="height:40px">
    </div><br><br><br>
    <li align="center"><button type="submit" style="height:50px;width:100px;" onclick="return checkUsernameLength();">登录</button></li>
    </ul>
	
	
	
	
	<hr />
	
	
</div>

</div><!-- END GRID -->
</form>
<!-- ===================================== START FOOTER ===================================== -->
<div class="clear"></div>
<div id="footer">
&copy; Copyright 2011–2012 All Rights Reserved. This website was built with <a href="http://www.99lime.com">HTML KickStart</a>
</div>

</body></html>