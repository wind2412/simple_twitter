<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+"twitter_proj/";
%>

<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>

<!DOCTYPE html>
<base href="<%=basePath%>">
<html>
<head>
	<!-- META -->
	<title>我的粉丝</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="" />
	
	<!-- CSS -->
	<script type="text/javascript" src="js/jquery1-9-1.min.js"></script>		<!-- 每个库所需要的jquery必须精确地放在其之前！！多个jquery库也遵循这个规则！ -->
	<script type="text/javascript" src="js/kickstart.js"></script>
    <link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
	<link rel="stylesheet" type="text/css" href="style.css" media="all" /> 
	<link rel="stylesheet" type="text/css" href="css_us/iconfont.css" />
	     <script src="css_us/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="plugin/jquery_danchu/reveal.css" />
	<script type="text/javascript" src="my_js/focus_list.js"></script>
     <script type="text/javascript" src="css_us/article.js"></script>
     <script type="text/javascript" src="css_us/together.js"></script>
     <script type="text/javascript" src="css_us/articlepage.js"></script>
    <style>
	body{
		text-align:center;
	}
	.headImg {
			width: 70px;
			height: 70px;
			border: 3px #fff solid;
			border-radius: 10px;
			top: 80px;
			left: 30px;
			position: absolute;
		}
		
		.title {
			position: absolute;
			right: 30px;
		}
		.title img{
			height: 30px;
			width: 30px;
			
		}
		
		.cueent {
			
			background-color: dodgerblue;
			color: #fff;
			width: 100px;
			height: 40px;
			border: 0px;
			border-radius: 8px;
			cursor:pointer;
		}
		.name{
			margin: 0px;
			font-size: 15px;
			font-family: "微软雅黑";
			font-weight:bold;
			float: left;
		}
		
		.desc{
			margin-left: 30px;
			margin-right:30px;
		}
		
		.descp1{
			color: #333;
			font-size: 12px;
			font-weight:normal;
			clear: both;
		}
		.descp2{
			color: #333;
			font-size: 15px;
			font-weight:normal;
			clear: both;
		}
		.sankuai{
			border-radius:5px;
			height:290px;
			width:298px;
			float:left;
			margin:5px;
			background:#FFF;
		}
		.zong{
			width:924px;
			float:left;
			margin-left: 20px;
			/*margin-top:-750px;
			margin-left:320px;*/
		}
	.block{
		width:300px;
		float:left;
		margin-left:100px;
	}
	.name{
		margin: 0px;
		font-size: 25px;
		font-family: "微软雅黑";
		font-weight:bold;
		float: left;
	}
	.dest{
		text-align:left;
		color: #8B8378;
		/*font-size: 18px;*/
		font-weight:normal;
		clear: both;
	}
	.smy{
		text-align:left;
		color: #8B8378;
		font-size: 18px;
		font-weight:normal;
		clear: both;
	}
	.smy2{
		text-align:left;
		color: #bbbbbbb;
		font-size: 15px;
		font-weight:normal;
		clear: both;
	}
	
	.follow_topic{
	padding-top:5px;
	width:300px;
	height:673px;
	}
	.recommanded{
	background-color: #fff;
    border: 1px solid #e6ecf0;
    border-radius: 5px 5px 0 0;
	width:300px;
}
.rec-header{
	color:#999;
	height:25px;
	padding-top:10px;
	padding-left:10px;
}
.rec-container{
	padding-top:2px;
	
}
ol.ol_follow{
	margin:0;
	padding:5px;
	display:block;
	list-style: none;
}
li.li_follow{
	height:50px;
	text-align: inherit;
    display: list-item;
	padding:5px;
	margin-top:12px;
}
.account-group:link{
	text-decoration:none;
	color: #000;
	
}
.account-group:hover{
	text-decoration:none;
	color:#06F;
	}
.image{
	float:left;
	width: 48px;
    height: 48px;
    border-radius: 5px;
}
.useName{
	position: absolute;
	font-size: 14px;
	font-weight: bold;
	width: 120px;
	height: 18px;
}
button.close{
	text-align:center;
	margin-top:-8px;
	float:right;
	background:transparent;
	border:0;
	cursor:pointer;
}
.follow-container{
	
	width: 51px;
	height: 18px;
	padding:1px;
}
.but_follow{
	width:65px;
	height:20px;
	margin-left:70px;
	margin-top:-57px;
	text-align:center;
	font-size:13px;
	padding:0;
	background-color: #f5f8fa;
	color: #000;
	border:1px;
	border-radius: 5px;
	cursor:pointer;
}

.search{
	font-size:10px;
	color:#09F;
}

.topic{
	text-align:left;
	background-color: #fff;
    border: 1px solid #e6ecf0;
    border-radius: 5px 5px 0 0;
	width:300px;
}
.topic-header{
	color:#999;
	height:30px;
	padding-top:20px;
	padding-left:10px;
}
.topic-container{
	padding-top:2px;
	padding-bottom:4px;
}
ol.topic_text{
	margin:0;
	padding:5px;
	display:block;
	list-style: none;
}
li.topic_text{
	height:50px;
	text-align: inherit;
    display: list-item;
	margin-top:8px;
	height:40px;
}
a:link{
	color:#66C;
	text-decoration:none;
}
a:hover{
	color:#66C;
	text-decoration:underline;
}
.topic-key{
    font-size:13px;
	font-weight:bold;
}
.key-num{
	font-size:10px;
	color:#999;
}
	</style> 
	
	<!-- Javascript -->
	<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  -->
	
	
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

<div>
	<nav class="navbar" style="border-bottom: 2px solid rgba(0,0,0,0.15)">	<!-- 去掉了border-style:solid, 否则会感觉header不在垂直的正中央.-->
    	<div class="header">
        	<div class="header-btn-left">
                <ul>
                <li></li>
                <li id="head_m"><a href=""><img src="icons/1.png" height="20px" width="20px"><span id="head_m">主页</span></a><div class="under_head_m"></div></li>
                <li id="head_i"><a ><img src="icons/2.png" height="20px" width="20px"><span id="head_i">通知</span></a><div class="under_head_i"></div></li>
                <li id="head_c"><a ><img src="icons/3.png" height="20px" width="20px"><span id="head_c">私信</span></a><div class="under_head_c"></div></li>
                </ul>
            </div>
            <div class="logo-center"><a id="logo2"><img src="icons/5.png" style="width: 40px; height: 40px; margin-top:7px"></a></div>
            <div class="header-btn-right">
                <div class="search-back"><input type="text" placeholder="搜索 Twitter" style="font-size: 16px; margin-top:11px"></div>
                <div style="float:left; width:54px; height:54px"><a id="logo1"><img src="" style="width: 40px; height: 40px; margin-top:7px;border-radius: 90px;" id="portrait"></a></div>
				<div style="float:left; width:100px; height:41px; margin-top: 13px;"><font id="loginusername" style="color: black;"></font></div>               
                <div style="float:left; width:70px; height:54px"><a id="logo"><button class="medium blue" style="margin-top:11px">发推</button></a></div>
            <!--<a id="logo3"><input results="s" type="search" placeholder="搜索 Twitter"></a>-->
    		</div>
            
        </div>
	</nav>
</div>


<div id="bg" style=" height: auto;  width: 100%; ">		<!--蓝色状态栏-->
	<img id="bg_img" class="the-backgroundpic"  style="width:100%; ">
</div>

<div>
	<nav class="navbar1">
		<div class="profile-back">
			<a id="other_head">
				<img class="head" id="bighead" src="" style="visibility: hidden;">
			</a>
		
            <ul>
                <li id="head_t"><a href="" id="head_articles"><div id="head_t">推文</div><div id="head_t_num"></div></a><div class="under_center_t"></div></li>
                <li id="head_f"><a href="" id="head_focusing"><div id="head_f">正在关注</div><div id="head_f_num"></div></a><div class="under_center_f"></div></li>
                <li id="head_fd"><a href="" id="head_fansing"><div id="head_fd" style="color:#2aa3ef">关注者</div><div id="head_fd_num" style="color:#2aa3ef"></div></a><div class="under_center_fd" style="margin-left:12px"></div></li>
            </ul>
            <div class="logo0-back">
        		<a id="logo0"><!-- <button class="medium blue" onclick="window.location='/twitter_proj/edit.jsp'">编辑个人资料</button> --></a>
            </div>
        </div>
		
	</nav>
	
</div>
<br><br>

<script type="text/javascript">
	var this_page = 3;
if (isSafari()) {
	$(window).bind("pageshow", function (event) {
		if (event.originalEvent.persisted && $('body').hasClass("no-cache")) {
			document.body.style.display = "none";
			window.location.reload();
		}
	});
}

function isSafari() {
	if (navigator.userAgent.indexOf("Safari") > -1) {
		return true;
	}
	return false;
}


//注意：LogInUID不是UID就是0. LogInusername不是真·name就是"null"(带双引号). other_UID一定是正确的/异步调用的话需要检查是否为null. other_name一定是正确的/异步调用需要检查是否为"null"。=>因为会并发执行。
	//得到session中登录用户的UID
	var LogInUID = <%= request.getSession().getAttribute("LogInUID")%>;		//可能为null
	if(LogInUID == null)	LogInUID = 0;
	//得到session中登录用户的name
	var LogInusername = '<%= request.getSession().getAttribute("LogInusername")%>';		//注意......这里会真的显示Tom....不是字符串“Tom”，而是就是Tom......
	document.getElementById("loginusername").innerHTML = LogInusername;
	//得到query的username   【注意！！网络中url的传输全是ISO-8859-1编码格式！因此要强转为UTF-8！！！】
	var other_name = '<%= request.getParameter("usr") == null ? null : new String(request.getParameter("usr").getBytes("iso-8859-1"), "utf-8" ) %>';		//得到请求末尾的query.		但是要注意，可能是null。
	var other_UID = null;	//如果这里不设置的话，如果query出错的话，比如?usr=zhengxiaoli 那么if(other_uid==0)之后会other_UID未定义。因为并不会定义other_UID. 
	//设置头的“正在关注”等连接
	document.getElementById("logo1").href = "/twitter_proj/twitter_focus.jsp?usr="+LogInusername+"&timestamp="+new Date().getTime();		//这里需要改。应该先跳到对方的推文列表。
	document.getElementById("head_articles").href = "/twitter_proj/twitter_articles.jsp?usr="+other_name+"&timestamp="+new Date().getTime();;
	document.getElementById("head_focusing").href = "/twitter_proj/twitter_focus.jsp?usr="+other_name+"&timestamp="+new Date().getTime();
	document.getElementById("head_fansing").href = "/twitter_proj/twitter_fans.jsp?usr="+other_name+"&timestamp="+new Date().getTime();
	//看query是否合法才能向下进行。因此这里必须同步方式。需要关闭ajax异步。
	dwr.engine.setAsync(false);
	//1.如果query不空，那就检测是否合法，合法就继续走，不合法直接跳页404  2.query空，看是否已经登录，如果登录过(LogInusername不空)则query设为LogInusername 3.否则“请您登录推特主页吧”
	if(other_name != "null")	{		//注意这里即便是null，也变成了"null"字符串了...
		Cluster.is_user_in_DB(other_name, function(other_uid){
			if(other_uid == 0){
				window.location.href = "/twitter_proj/doubi.html";
			} else {
				other_UID = other_uid;		//赋值给other_UID
				
				//得到header的button按钮  为了在自己页面变成关注
				var a = document.getElementById("logo0");
				var button = document.createElement("button");
				button.className = "medium blue";
				a.appendChild(button);
				if(other_UID == LogInUID){
					button.onclick = function(){		//竟然直接设置button.oncick = "window.location='...'"不好使，然而在html中指定onclick是好使的。
						window.location.href="/twitter_proj/edit.jsp";					
					}
					button.innerHTML = "编辑个人资料";
					button.style.marginRight = "50px";
				}else{
					Cluster.focus_or_not(LogInUID, other_uid, function(data){
						button.style.width = "100px";
						button.style.marginRight = "50px";
						if(data == true){//<button class="medium blue" id="edit_or_focus" ></button>
							if(LogInUID == 0)	return;
							button.innerHTML = "正在关注";
							button.onclick = function(){
								if(LogInUID == 0)	return;
								if(button.innerHTML == "正在关注"){
									button.innerHTML = "关注";
									Cluster.focus_cancelled_oh_no(LogInUID, other_UID, function(){
										var fans_div = document.getElementById("head_fd_num");
										fans_div.innerHTML = parseInt(fans_div.innerHTML) - 1;
									});		//取消关注
								}else{
									button.innerHTML = "正在关注";							
									Cluster.focus_a_user(LogInUID, other_UID, function(){
										var fans_div = document.getElementById("head_fd_num");
										fans_div.innerHTML = parseInt(fans_div.innerHTML) + 1;
									});		//关注
								}
							}
						}else{
							button.innerHTML = "关注";
							button.onclick = function(){
								if(LogInUID == 0)	return;
								if(button.innerHTML == "正在关注"){
									button.innerHTML = "关注";
									Cluster.focus_cancelled_oh_no(LogInUID, other_UID, function(){
										var fans_div = document.getElementById("head_fd_num");
										fans_div.innerHTML = parseInt(fans_div.innerHTML) - 1;
									});		//取消关注
								}else{
									button.innerHTML = "正在关注";							
									Cluster.focus_a_user(LogInUID, other_UID, function(){
										var fans_div = document.getElementById("head_fd_num");
										fans_div.innerHTML = parseInt(fans_div.innerHTML) + 1;
									});			//关注
								}
							}
						}
					});
				}
			}
		});
	}else if(LogInusername != "null")	{
		window.location.href = "/twitter_proj/twitter_fans.jsp?usr="+LogInusername+"&timestamp="+new Date().getTime();
	}else{
		window.location.href = "/twitter_proj/login.jsp";
	}
	//此时已经获取到最重要的other_UID，可以开启异步了。
	dwr.engine.setAsync(true);
	//鼠标放到头像上，显示名字
	document.getElementById("other_head").title = other_name;
	//得到登录的用户LogInusername头像		//必须在得到UID的同时才能执行。因为是异步，不知道什么时候才能读取到啊。
//alert(LogInUID + "..." + other_UID);		//test
	if(LogInUID != null)
		Cluster.get_user_portrait(LogInUID, function(src){
			src==null? src="portraits/anonymous.jpg" :{}; 
			document.getElementById("portrait").src = src;
			//如果LogInUID和other_UID相等，复制大头像
			if(LogInUID == other_UID){	
			//alert("yes!!!");
				document.getElementById("bighead").src = document.getElementById("portrait").src;		
				document.getElementById("bighead").style.visibility = "visible";	
				if(other_UID != 0)
					get_user_article_msg();	//异步调用ajax获取other_UID的所有推文，正在关注以及关注者信息。
			}	//	else alert("no!!!");
			//上边的两个alert。ajax太诡异了！开了同步，也会在没同步完的时候所有ajax全都异步调用一遍？？然后同步完之后后边的ajax又会重新调用一遍......
		});
	//所以就因为这种诡异......这里的if里边必须要设置other_UID!=null条件，来防止第一次全规模的全异步调用......
	if(LogInUID != other_UID && other_UID != null){	//卧槽？？？js里边这src==null?...就少些个等号=，src竟然就变成object了？？？？？
		Cluster.get_user_portrait(other_UID, function(src){
			src==null? src="portraits/anonymous.jpg" :{}; 
			document.getElementById("bighead").src = src;
			document.getElementById("bighead").style.visibility = "visible";		
			if(other_UID != 0)
				get_user_article_msg();	//异步调用ajax获取other_UID的所有推文，正在关注以及关注者信息。
		});
	}
	//得到other_UID的background。
	if(other_UID != null){		//由于是异步，所以这里无论如何都会执行。因此，必须设置null把关啊。
		Cluster.get_user_main_page(other_UID, function(src){
			if(src != null){		//有就设置，没有就不设置。
				document.getElementById("bg_img").src = src;
			}else{
				document.getElementById("bg").style.height = "259px";				
			}
		});
	}
	
	//得到query用户所有推文，正在关注，以及关注者信息。
	function get_user_article_msg(){
		Cluster.get_user_articles_num(other_UID, function(data){document.getElementById("head_t_num").innerHTML = data;});
		Cluster.get_focus_num(other_UID, function(data){document.getElementById("head_f_num").innerHTML = data;});
		Cluster.get_fans_num(other_UID, function(data){document.getElementById("head_fd_num").innerHTML = data;});		
	}
	
	//得到[左方之地]=>other_UID所有信息		//如果dwr中得到一个对象obj的话，那么不用调用方法(因为不是方法)，而是直接调用成员变量。比如obj.name。私有的就可以。
	var other_usr_info;
	//Cluster.get_a_user_by_UID(other_UID, function(user_obj){alert(user_obj.name);});
	
		
	//赋予随机背景颜色
	var main_page = function(){
		var color_string = Math.floor(Math.random() * 16775680).toString(16);
		if(color_string.length < 6){
			var diff = 6 - color_string.length;
			var str = "";
			while(diff > 0){
				str += "E";
				diff --;
			}
			color_string = str + color_string;
		}
		return "#" + color_string;		//随机颜色生成
	}
	//alert(document.getElementById("bg_img").src == "");	//如果src这个属性没写，确实是等于""的.
	document.getElementById("bg").style.backgroundColor = main_page();	//随机设置一个颜色 如果用户有自己的大图片，就覆盖了。
	
	
	</script>


<script>
	function id(a){
		return document.getElementById(a);
	}
	
	
</script>


<div style="width:1425px; min-height:30px">
<div class="block"><!--左侧大的div-->

	<!-- 左方之地 -->
	<div>
    <p class="name" id="left_name"></p>
    <br>
    <p class="dest" id="left_at"></p>
    <p class="smy2" id="left_introduction"></p>
    <p class="smy2" id="left_nationality"></p>
    <p class="smy2" id="left_website"></p>
    <p class="smy" id="left_time"></p>
    </div>
    
    <script type="text/javascript">
    	document.getElementById("left_name").innerHTML = other_name;
    	document.getElementById("left_at").innerHTML = "@"+other_name;
		Cluster.get_a_user_by_UID(other_UID, function(User){
			if(User.introduction != null) 	document.getElementById("left_introduction").innerHTML = User.introduction;
			if(User.position != null)		document.getElementById("left_nationality").innerHTML = User.position;
			if(User.website != null)		document.getElementById("left_website").innerHTML = User.website;
			var date = new Date(User.time * 1000);
			document.getElementById("left_time").innerHTML = "加入于" + date.getFullYear() + "年" + (date.getMonth()+1) + "月";		//注意月份是从0开始记得，因此要+1.。
		});    	
		
	//	Cluster.get_article_comments_context(7, 0, function(data){
	//		alert(data[0][0].UID);
	//	});	//得到第7篇文章的评论上下文
		
		
    </script>
    
    
    
    <div class="follow_topic">
       <div class="recommanded">
  			<div class="rec-header">推荐关注</div>
			<div class="rec-container">
		    	<ol class="ol_follow" id="recommend_list" style="text-align:left;">
			  <!--  <li class="li_follow">
				        <div><a class="account-group" href=""><img class="image" src="2.jpg"><strong class="userName">&nbsp;Shinobi Ninja</strong>
				        </a>
				        <button type="button" class="close" onclick="Iclose()" title="关闭">&times;</button></div>
				        <div class="follow-container"><button class="but_follow">关注</button></div>
				     </li>	 原先的代码！应该改下按钮的	CSS！！ -->  	 	  
				     
		    	</ol>
		  	</div>
		</div>
		
		<br>
		
		<script type="text/javascript">
				focus_list();			//关注列表
		</script>

		<!-- 下边的趋势可以被舍弃 也可以和时间流匹配 -->
		<div class="topic">
		  <div class="topic-header">趋势</div>
		  <div class="topic-container">
		    <ol class="topic_text">
		      <li class="topic_text">
		        <div><a class="topic-key" href="">#同棲してる2人の日常</a></div>
		        <div class="key-num">6,091推文</div>
		      </li>
		      <li class="topic_text">
		        <div><a class="topic-key" href="">#seibulions</a></div>
		        <div class="key-num">1.04万推文</div>
		      </li>
		      <li class="topic_text">
		        <div><a class="topic-key" href="">#秋葉原駅</a></div>
		        <div class="key-num">7.68万推文</div>
		      </li>
		      <li class="topic_text">
		        <div><a class="topic-key" href="">#村上佳菜子</a></div>
		        <div class="key-num">1.2万推文</div>
		      </li>
		      <li class="topic_text">
		        <div><a class="topic-key" href="">#sundaysongbook</a></div>
		        <div class="key-num">1,589推文</div>
		      </li>
		    </ol>
		  </div>
		</div>
		<br><br><br>
		
	</div>
  </div>

<div class="zong" id="my_focus">
    <!--     <div class="sankuai">
	        <div style="height: 160px;position: relative;">
				<div 插入><img style="height: 100px;width: 100%;border-radius:5px;" src="portraits/page_1.jpg"></div>
				<img class="headImg" src="CcXer0_P_400x400.jpg">
				<p class="title"><button class="medium blue">正在关注</button></p>
			</div>
			<div class="desc">
				<p class="name"><span>Jim Carrey</span></p>
				<img src="QQ图片20170416191034.png" />
				<p class="descp1"><span>@JimCarry</span></p>
				<p class="descp2"><span>Actor Jim Carrey</span></p>
			</div>
        </div>	 -->
</div>

<script type="text/javascript">
		
		dwr.engine.setAsync(false);	//同步
	
		var focus_num;		
		var page_num;		
		var page_cur = 0;

		Cluster.get_fans_num(other_UID, function(data){		//调用名称改了
			focus_num = data;
			page_num = Math.floor(focus_num/18);	//18是一页的量
		});
		Cluster.get_fans_by_page(other_UID, page_cur, function(set){
			place_focus_in_grid(set);		//排列出来
			page_cur ++;
		});
		
		dwr.engine.setAsync(true);	//同步
		
		
		function create_a_focus(UID){
			var div = document.createElement("div");
			div.className = "sankuai";
			var a = document.createElement("a");
				//1st
				var div_in_1 = document.createElement("div");
				div_in_1.style.height = "160px";
				div_in_1.style.position = "relative";
				var div_insert = document.createElement("div");
				var img_1 = document.createElement("img");
				img_1.style.height = "100px";
				img_1.style.width = "100%";
				img_1.style.borderRadius = "5px";
				div_insert.appendChild(img_1);
				Cluster.get_user_main_page(UID, function(main_path){
					if(main_path != null){
						img_1.src = main_path;
					}else{
						div_insert.style.backgroundColor = main_page();	//随机颜色
					}
				});
				var img_2 = document.createElement("img");
				img_2.className = "headImg";
				Cluster.get_user_portrait(UID, function(portrait){
					img_2.src = (portrait == null) ? "portraits/anonymous.jpg" : portrait;			
				});
				var p_1 = document.createElement("p");
				p_1.className = "title";
				var button;
				if(UID != LogInUID){//如果是自己  不显示按钮
					button = document.createElement("button");
					button.style.width = "100px";
					button.className = "medium blue";	//改
					//这里，与关注页面不同的是，因为即使是粉丝，也有可能关注了/没关注。因此这里必须插入一个判断：focus_or_not
					Cluster.focus_or_not(LogInUID, UID, function(is_focus){
						if(is_focus == true)	button.innerHTML = "正在关注";
						else button.innerHTML = "关注";
					});
					button.onclick = function(){
						if(LogInUID == 0)	return;
						//点击按钮要修改前端静态页面的关注人数哦
						var focus_div = document.getElementById("head_f_num");
						if(button.innerHTML == "正在关注"){
							button.innerHTML = "&nbsp;&nbsp;关注&nbsp;&nbsp;";
							Cluster.focus_cancelled_oh_no(LogInUID, UID);		//取消关注
							if(other_UID == LogInUID){		//如果是自己的页面才加。
								focus_div.innerHTML = parseInt(focus_div.innerHTML) - 1;							
							}
						}else{
							button.innerHTML = "正在关注";							
							Cluster.focus_a_user(LogInUID, UID);		//关注
							if(other_UID == LogInUID){		//如果是自己的页面才加。
								focus_div.innerHTML = parseInt(focus_div.innerHTML) + 1;							
							}
						}
					}
				}
				//连接
				div_in_1.appendChild(a);
				div_in_1.appendChild(img_2);
				div_in_1.appendChild(p_1);
				if(UID != LogInUID){
					p_1.appendChild(button);
				}
				//2nd
				var div_in_2 = document.createElement("div");
				div_in_2.className = "desc";
				var p_2 = document.createElement("p");	p_2.className = "name";
				var p_3 = document.createElement("p");	p_3.className = "dest"; p_3.style.marginTop = "20px";
				p_2.appendChild(document.createElement("br"));
				Cluster.get_user_name(UID, function(name){
					a.href = "/twitter_proj/twitter_focus.jsp?usr="+name+"&timestamp="+new Date().getTime();		//在粉丝中点击某个粉丝，还是会跳到对方的关注页。
					p_2.innerHTML = name;			
					p_3.innerHTML = "@"+name;	
				});
				var p_4 = document.createElement("p");	p_4.className = "descp2";
				Cluster.get_user_introduction(UID, function(intro){
					p_4.innerHTML = (intro == null) ? "lazy person didn't leave anything :)" : intro;	
				});
				//连接
				div_in_2.appendChild(p_2);
				div_in_2.appendChild(p_3);
				div_in_2.appendChild(p_4);
			//大连接
			a.appendChild(div_insert);
			div.appendChild(div_in_1);
			div.appendChild(div_in_2);
			return div;
		}
		
		function make_a_line(UID1, UID2, UID3){
			var div = document.createElement("div");
			if(UID1 != null){
				div.appendChild(create_a_focus(UID1));
			}
			if(UID2 != null){
				div.appendChild(create_a_focus(UID2));
			}
			if(UID3 != null){
				div.appendChild(create_a_focus(UID3));
			}
			return div;
		}
	
		function append_a_line(line_div) {
			var div = document.getElementById("my_focus");
			div.appendChild(line_div);
		}
		
		function place_focus_in_grid(set) {
			var line_num = Math.floor(set.length/3);		//一行3个。
			var line_cur = 0;		//从第0行开始计数
			for(; line_cur <= line_num; line_cur ++){
				if(line_cur == line_num){		//最后一排，可能会只有一行1个/2个
					var remain_num = set.length - line_cur * 3;
					if(remain_num == 1){
						append_a_line(make_a_line(set[line_cur*3], null, null));
					}else if(remain_num == 2){
						append_a_line(make_a_line(set[line_cur*3], set[line_cur*3+1], null));
					}else if(remain_num == 3){
						append_a_line(make_a_line(set[line_cur*3], set[line_cur*3+1], set[line_cur*3+2]));					
					}
				}else{
					append_a_line(make_a_line(set[line_cur*3], set[line_cur*3+1], set[line_cur*3+2]));
				}
			}
		}
			
		//检测到达页面的底部
		var $document = $(document);//缓存一下$(document)
	    $(window).scroll(function(){
	    　　var $this = $(this),
	            scrollTop = $this.scrollTop(),
	            scrollHeight = $document.height(),
	            windowHeight = $this.height();
	    　　if(scrollTop + windowHeight >= scrollHeight){
	    		//到达底部		//获取一页focus
	    		if(page_cur > page_num)	return;		//如果页指针指向最后，那么一定是到达底端而且全部加载出来了。
				else Cluster.get_fans_by_page(LogInUID, page_cur, function(focus_num){
					place_focus_in_grid(focus_num);		//排列出来
					page_cur ++;
				});
	    　　}
	    });
	
</script>

</body>
</html>
