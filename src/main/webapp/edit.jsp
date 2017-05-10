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
	var img_suffix_legal = true;
	var LogInUID = <%= request.getSession().getAttribute("LogInUID")%>;	
	var LogInusername = '<%= request.getSession().getAttribute("LogInusername")%>';
	function check(){
		if(LogInUID == null || LogInusername == "null" || img_suffix_legal == false)		return false;
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
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="file" onchange="show_pic(this)" accept="image/*" name="upload_image" id="up_img"  style="width:400px;height=100px" title="头像"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="file" onchange="add_main_page(this)" accept="image/*" name="bg_img_up" id="bg_img"  style="width:400px;height=100px" title="主页图片"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="username" id="usr" placeholder="新的昵称" style="width:400px;"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="introduction" id="intro" placeholder="个人简介" style="width:400px;"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="nationality" id="nation" placeholder="国家" style="width:400px;"></li><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="website" id="website" placeholder="个人网址" style="width:400px;"></li><br><br>
	<li align="center"><i class="fa fa-li fa-check"></i> <input type="text" name="img" id="img_to_string" placeholder="个人网址" style="display: none;height: 0px"></li>
    <li align="center"><button type="submit" style="height:50px;width:150px;" onclick="return check();">更改个人资料</button></li>
    </ul>
	
	
<script type="text/javascript">
	
	//ajax异步 得到用户的真·头像  如果没有 显示anonymous。
	if(LogInUID != null)
		Cluster.get_user_portrait(LogInUID, function(src){
			src==null? src="portraits/anonymous.jpg" :{}; 
			document.getElementById("portrait").src = src;
		});
	
	//把图片打成Base64上传
	function show_pic(source) {
		if(window.FileReader) {
			var fileReader = new FileReader();
			pic = source.files[0];
			if(/^image\/*/.test(pic.type)){		//js的正则表达式匹配  /.../指定一个模式串，^表示以...开头，\表示转义。
				fileReader.onloadend = function (e) {
					document.getElementById("portrait").src = e.target.result;
					Cluster.add_user_portrait(LogInUID, e.target.result);		//添加这个头像到本地。
				}
				fileReader.readAsDataURL(pic);
			}else{
				img_suffix_legal = false;
				alert("please upload a jpg/png file!");
			}
		} else {
			alert("您的游览器不支持上传图片！");
		}
	}
	
	function add_main_page(source) {
		if(window.FileReader) {
			var fileReader = new FileReader();
			pic = source.files[0];
			if(/^image\/*/.test(pic.type)){		//js的正则表达式匹配  /.../指定一个模式串，^表示以...开头，\表示转义。
				fileReader.onloadend = function (e) {
					Cluster.add_user_main_page(LogInUID, e.target.result);		//添加这个头像到本地。
				}
				fileReader.readAsDataURL(pic);
			}else{
				img_suffix_legal = false;		//有两个图片控制img_suffix_legal变量，只需要变个思路，因为两个只要有一个false，就无法上传，不如就直接设成false啦！
				alert("please upload a jpg/png file!");
			}
		} else {
			alert("您的游览器不支持上传图片！");
		}
		
	}
	
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