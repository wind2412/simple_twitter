<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+"twitter_proj/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
	<!-- META -->
	<title>twitter</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="" />
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
	<link rel="stylesheet" type="text/css" href="style.css" media="all" /> 
	
	<!-- Javascript -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/kickstart.js"></script>
	
	<!-- DWR script -->
	<script type='text/javascript' src='/twitter_proj/dwr/engine.js'></script>
	<script type='text/javascript' src='/twitter_proj/dwr/util.js'></script>
  	<script type='text/javascript' src='/twitter_proj/dwr/interface/Cluster.js'></script>
	
</head>
<body>

<!--
	<div class="col_12" style="margin-top:100px;">
		<h1 class="center">
		<p><i class="fa fa-fire"></i></p>
		This example is blank</h1>
		<h4 style="color:#999;margin-bottom:40px;" class="center">Add some HTML KickStart Elements to see the magic happen</h4>
	</div>
-->

<div style="z-index: 1001;">
	<div class="navbar" style="border-bottom-color: black; border-bottom-width:thin">	<!-- 去掉了border-style:solid, 否则会感觉header不在垂直的正中央.-->
	
			<ul style="float: left">
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li style=""><a href=""><img src="icons/1.png" height="20px" width="20px"><span>主页</span></a></li>
			<li><a href=""><img src="icons/2.png" height="20px" width="20px"><span>通知</span></a></li>
			<li><a href=""><img src="icons/3.png" height="20px" width="20px"><span>私信</span></a></li>
			</ul>
			
			<ul style="float: left">
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li><a  style="text-align: center"><img src="icons/5.png" style="width: 40px; height: 40px; margin-top: -10px"></a></li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li><input type="text" placeholder="搜索 Twitter" style="font-size: 13px; margin-top: -20px"></li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
			<li><a ><img src="" style="width: 30px; height: 30px; margin-top: -25px" id="portrait"><font id="loginusername" style="color: black;"></font></a></li>
			</ul>

			<a id="logo"><button class="medium blue">发推</button></a>
	</div>
</div>



<div id="bg" style="background-color: #2aa3ef; height: 325px; width: 100%; ">		<!--蓝色状态栏-->
	
</div>

<div>
	<nav class="navbar1">
		
		<div class="head" style="z-index: 0"><a id="other_head"><img id="bighead" src="" style="width: 200px; height: 200px; visibility: hidden;"></a></div>
		<a id="logo0"><button class="medium blue">编辑个人资料</button></a>
		
		<ul>
		<li><a href=""><div>推文</div><div id="articles"></div></a></li>
		<li><a href=""><div>正在关注</div><div id="focus"></div></a></li>
		<li><a href=""><div>关注者</div><div id="fans"></div></a></li>
		</ul>
		
	</nav>
	
</div>
<script type="text/javascript">
	//得到session中登录用户的UID
	var LogInUID = <%= request.getSession().getAttribute("LogInUID")%>;		//可能为null
	if(LogInUID == null)	LogInUID = 0;
	//得到session中登录用户的name
	var LogInusername = '<%= request.getSession().getAttribute("LogInusername")%>';		//注意......这里会真的显示Tom....不是字符串“Tom”，而是就是Tom......
	document.getElementById("loginusername").innerHTML = LogInusername;
	//得到query的username
	var other_name = '<%= request.getParameter("usr") %>';		//得到请求末尾的query.		但是要注意，可能是null。
	var other_UID; 
	//看query是否合法才能向下进行。因此这里必须同步方式。需要关闭ajax异步。
	dwr.engine.setAsync(false);
	//1.如果query不空，那就检测是否合法，合法就继续走，不合法直接跳页404  2.query空，看是否已经登录，如果登录过(LogInusername不空)则query设为LogInusername 3.否则“请您登录推特主页吧”
	if(other_name != "null")	{		//注意这里即便是null，也变成了"null"字符串了...
		Cluster.is_user_in_DB(other_name, function(other_uid){if(other_uid == 0){  window.location.href = "/twitter_proj/doubi.html";} else {other_UID = other_uid;}})
	}else if(LogInusername != "null")	{
		window.location.href = "/twitter_proj/twitter_user.jsp?usr="+LogInusername;
	}else{
		window.location.href = "/twitter_proj/login.jsp";
	}
	//此时已经获取到最重要的other_UID，可以开启异步了。
	dwr.engine.setAsync(true);
	//鼠标放到头像上，显示名字
	document.getElementById("other_head").title = other_name;
	//得到登录的用户LogInusername头像		//必须在得到UID的同时才能执行。因为是异步，不知道什么时候才能读取到啊。
//alert(LogInUID + "..." + other_UID);		//test
	Cluster.get_user_portrait(LogInUID, function(src){src==null? src="portraits/anonymous.jpg" :{}; document.getElementById("portrait").src = src;});
	//得到大头像
	if(LogInUID != other_UID){//卧槽？？？js里边这src==null?...就少些个等号=，src竟然就变成object了？？？？？
		Cluster.get_user_portrait(other_UID,function(src){src==null?src="portraits/anonymous.jpg":{}; document.getElementById("bighead").src = src;});
		document.getElementById("bighead").style.visibility = "visible";
	}else{	
		document.getElementById("bighead").src = document.getElementById("portrait").src;		
		document.getElementById("bighead").style.visibility = "visible";		
	}
	//得到[左方之地]=>other_UID所有信息
	//var other_usr_info;
	//Cluster.get_a_user_by_UID(other_UID, function(user_obj){alert(user_obj);});
		
	//赋予随机背景颜色
	var main_page = function(){
		return "#" + Math.floor(Math.random() * 16777215).toString(16);		//随机颜色生成
	}
	document.getElementById("bg").style.backgroundColor = main_page();
	
	
	
	
	
</script>

<script type="text/javascript">
	//DWR的js方法直接调用java方法！
	//Cluster.get_user_articles_num(1, function(data){alert(data);});
	
	//得到此用户所有推文，正在关注，以及关注者信息。
	Cluster.get_user_articles_num(LogInUID, function(data){document.getElementById("articles").innerHTML = data;});
	Cluster.get_focus_num(LogInUID, function(data){document.getElementById("focus").innerHTML = data;});
	Cluster.get_fans_num(LogInUID, function(data){document.getElementById("fans").innerHTML = data;});
	
</script>



<hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr>


</body>
</html>
