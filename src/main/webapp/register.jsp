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
<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />                  <!-- KICKSTART -->
<link rel="stylesheet" type="text/css" href="style.css" media="all" />                          <!-- CUSTOM STYLES -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/kickstart.js"></script>                                  <!-- KICKSTART -->
<script type="text/javascript">
	var msg = '<%= request.getAttribute("msg") %>';	

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
<form action = "/test_client/RegisterServlet" method = "post">
<div class="col_12">
	<div align="center">
	<h1 style="font-family:YaHeiConsola" >注册页面</h1>	
	</div>
	<br>
	
	<div align="center">
    <script type="text/javascript">	//这一段还没添加上去。
    	if(msg != "null"){
    		document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>" + msg + "</p>");
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
    <li align="center"><button type="submit" style="height:50px;width:100px;" onclick="return checkUsernameLength();">注册</button></li>
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