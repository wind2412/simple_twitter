<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+"twitter_proj/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
<title>edit my profile</title>
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

<!-- DWR script -->
	<script type='text/javascript' src='/twitter_proj/dwr/engine.js'></script>
	<script type='text/javascript' src='/twitter_proj/dwr/util.js'></script>
  	<script type='text/javascript' src='/twitter_proj/dwr/interface/Cluster.js'></script>
	

<script type="text/javascript">
	var LogInUID = <%= request.getSession().getAttribute("LogInUID")%>;	
	var LogInusername = '<%= request.getSession().getAttribute("LogInusername")%>';
	function check(){
		if(LogInUID == null || LogInusername == "null")		return false;
		else return true;
	}
</script>
	
</head><body>

<!-- Menu Horizontal -->

<div class="grid">
	
<!-- ===================================== END HEADER ===================================== -->
<form action = "/twitter_proj/EditProfileServlet" method = "get">
<div class="col_12">
	<div align="center">
	<h1 style="font-family:YaHeiConsola" >编辑个人资料</h1>	
	</div>
	<br>
	<div align="center">
    <script type="text/javascript">	//这一段还没添加上去。
		if(check() == false) {
			document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>请您登录！</p>");
		} else {
			var output = "welcome，"+LogInusername+"！Please edit your profile.";
			document.writeln("<p style='font-size: 20px;color:red;font-family:monaco'>"+output+"</p>");
		}
    </script>
    </div>
    	
    <br>
	<ul class="icons">	
	<li align="center"><i class="fa fa-li fa-check"></i> <div><img id="portrait" src="" style="width: 200px; height: 200px; border: 3px #fff solid;
	border-radius: 20px;"></div> </li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="file" name="upload_image" id="up_img" style="width:400px;height=100px"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="username" id="usr" placeholder="新的昵称" style="width:400px;height=100px"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="introduction" id="intro" placeholder="个人简介" style="width:400px;height=100px"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="nationality" id="nation" placeholder="国家" style="width:400px;height=100px"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="website" id="website" placeholder="个人网址" style="width:400px;height=100px"></li><br><br>
    <li align="center"><button type="submit" style="height:50px;width:150px;" onclick="return check();">更改个人资料</button></li>
    </ul>
	
	
<script type="text/javascript">
	
	if(LogInUID != null)
		Cluster.get_user_portrait(LogInUID, function(src){
			src==null? src="portraits/anonymous.jpg" :{}; 
			document.getElementById("portrait").src = src;
		});
	
</script>
	
</div>

</div><!-- END GRID -->
</form>
<!-- ===================================== START FOOTER ===================================== -->
<div class="clear"></div>
<div id="footer">
&copy; Copyright 2011–2012 All Rights Reserved. This website was built with <a href="http://www.99lime.com">HTML KickStart</a>
</div>

</body></html>