<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"+"twitter_proj/";
%>


<!DOCTYPE html>
<base href="<%=basePath%>">
<html>
<head>
	<!-- META -->
	<title>我的关注</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="" />
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
	<link rel="stylesheet" type="text/css" href="style.css" media="all" />
    <link rel="stylesheet" href="css_us/focus.css" />
    <link rel="stylesheet" href="css_us/trend.css" />
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
			height:330px;
			width:298px;
			float:left;
			margin:5px;
			background:#FFF;
		}
		.zong{
			width:924px;
			float:left;
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
		font-size: 18px;
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
	margin-top:-21px;
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

<div>
	<nav class="navbar" style="border-bottom-color: black; border-bottom-width:thin">	<!-- 去掉了border-style:solid, 否则会感觉header不在垂直的正中央.-->
    	<div class="header">
        	<div class="header-btn-left">
                <ul>
                <li></li>
                <li style=""><a href=""><img src="icons/1.png" height="20px" width="20px"><span>主页</span></a></li>
                <li><a href=""><img src="icons/2.png" height="20px" width="20px"><span>通知</span></a></li>
                <li><a href=""><img src="icons/3.png" height="20px" width="20px"><span>私信</span></a></li>
                </ul>
            </div>
            <div class="logo-center"><a id="logo2"><img src="icons/5.png" style="width: 40px; height: 40px; margin-top:7px"></a></div>
            <div class="header-btn-right">
                <div class="search-back"><input type="text" placeholder="搜索 Twitter" style="font-size: 16px; margin-top:11px"></div>
                <div style="float:left; width:54px; height:54px"><a id="logo1"><img src="" style="width: 40px; height: 40px; margin-top:7px" id="portrait"></a></div>
				<div style="float:left; width:100px; height:54px"><font id="loginusername" style="color: black;"></font></div>               
                <div style="float:left; width:70px; height:54px"><a id="logo"><button class="medium blue" style="margin-top:11px">发推</button></a></div>
            <!--<a id="logo3"><input results="s" type="search" placeholder="搜索 Twitter"></a>-->
    		</div>
            
        </div>
	</nav>
</div>


<div id="bg" style="background-color: #2aa3ef; height: 325px; width: 100%; ">		<!--蓝色状态栏-->
	
</div>

<div>
	<nav class="navbar1">
		<div class="profile-back">
			<a id="other_head">
				<img class="head" id="bighead" src="" style="visibility: hidden;">
			</a>
		
            <ul>
                <li><a href=""><div>推文</div><div id="articles"></div></a></li>
                <li><a href=""><div>正在关注</div><div id="focus"></div></a></li>
                <li><a href=""><div>关注者</div><div id="fans"></div></a></li>
            </ul>
            <div class="logo0-back">
        		<a id="logo0"><button class="medium blue" onclick="window.location='/twitter_proj/edit.jsp'">编辑个人资料</button></a>
            </div>
        </div>
		
	</nav>
	
</div>
<br><br>

<script type="text/javascript">
//注意：LogInUID不是UID就是0. LogInusername不是真·name就是"null"(带双引号). other_UID一定是正确的. other_name一定是正确的。
	//得到session中登录用户的UID
	var LogInUID = <%= request.getSession().getAttribute("LogInUID")%>;		//可能为null
	if(LogInUID == null)	LogInUID = 0;
	//得到session中登录用户的name
	var LogInusername = '<%= request.getSession().getAttribute("LogInusername")%>';		//注意......这里会真的显示Tom....不是字符串“Tom”，而是就是Tom......
	document.getElementById("loginusername").innerHTML = LogInusername;
	//得到query的username
	var other_name = '<%= request.getParameter("usr") %>';		//得到请求末尾的query.		但是要注意，可能是null。
	var other_UID = null;	//如果这里不设置的话，如果query出错的话，比如?usr=zhengxiaoli 那么if(other_uid==0)之后会other_UID未定义。因为并不会定义other_UID. 
	//看query是否合法才能向下进行。因此这里必须同步方式。需要关闭ajax异步。
	dwr.engine.setAsync(false);
	//1.如果query不空，那就检测是否合法，合法就继续走，不合法直接跳页404  2.query空，看是否已经登录，如果登录过(LogInusername不空)则query设为LogInusername 3.否则“请您登录推特主页吧”
	if(other_name != "null")	{		//注意这里即便是null，也变成了"null"字符串了...
		Cluster.is_user_in_DB(other_name, function(other_uid){
			if(other_uid == 0){
				window.location.href = "/twitter_proj/doubi.html";
			} else {
				other_UID = other_uid;		//赋值给other_UID
			}
		})
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
		Cluster.get_user_portrait(other_UID,function(src){
			src==null? src="portraits/anonymous.jpg" :{}; 
			document.getElementById("bighead").src = src;
			document.getElementById("bighead").style.visibility = "visible";		
			if(other_UID != 0)
				get_user_article_msg();	//异步调用ajax获取other_UID的所有推文，正在关注以及关注者信息。
		});
	}
	//得到query用户所有推文，正在关注，以及关注者信息。
	function get_user_article_msg(){
		Cluster.get_user_articles_num(other_UID, function(data){document.getElementById("articles").innerHTML = data;});
		Cluster.get_focus_num(other_UID, function(data){document.getElementById("focus").innerHTML = data;});
		Cluster.get_fans_num(other_UID, function(data){document.getElementById("fans").innerHTML = data;});		
	}
	
	//得到[左方之地]=>other_UID所有信息		//如果dwr中得到一个对象obj的话，那么不用调用方法(因为不是方法)，而是直接调用成员变量。比如obj.name。私有的就可以。
	var other_usr_info;
	//Cluster.get_a_user_by_UID(other_UID, function(user_obj){alert(user_obj.name);});
	
		
	//赋予随机背景颜色
	var main_page = function(){
		return "#" + Math.floor(Math.random() * 16777215).toString(16);		//随机颜色生成
	}
	document.getElementById("bg").style.backgroundColor = main_page();
	
	
	
	
	
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
			document.getElementById("left_time").innerHTML = "加入于" + date.getFullYear() + "年" + date.getMonth() + "月"
		});    	
		
	//	Cluster.get_article_comments_context(7, 0, function(data){
	//		alert(data[0][0].UID);
	//	});	//得到第7篇文章的评论上下文
		
		
    </script>
    
    
    
    <div class="follow_topic">
       <div class="recommanded">
  			<div class="rec-header">推荐关注</div>
			<div class="rec-container">
		    	<ol class="ol_follow" id="recommend_list">
			    	<!--    <li class="li_follow">
				        <div><a class="account-group" href=""><img class="image" src="2.jpg"><strong class="userName">&nbsp;Shinobi Ninja</strong>
				        </a>
				        <button type="button" class="close" onclick="Iclose()" title="关闭">&times;</button></div>
				        <div class="follow-container"><button class="but_follow">关注</button></div>
				      </li>	  原先的代码	-->	
		    	</ol>
		  	</div>
		</div>
		
		<br>
		
		<script type="text/javascript">
				//获得数据
				if(LogInUID != 0)
					Cluster.get_probably_acquaintance(LogInUID, function(set){
						var ptr = 0;		//关注列表一次显示3个。ptr是set的指针。
						var list_num = 0;	//显示在上边的关注列表的人数。如果叉掉，就少一个。然后点击事件会把list_num-1 然后如果set里边还有，即ptr没到set.length，那么list_num再++，
						for(; ptr < set.length && list_num < 3; ptr ++){
						alert(set[ptr]);	//Long还是long?
							get_an_acquaintance(set[ptr]);
							ptr ++;
							list_num ++;
						}
					});
				//推荐关注的动态生成
				function get_an_acquaintance(UID) {
					var ol = document.getElementById("recommend_list");
					
					var li = document.createElement("li");
					li.className = "li_follow";
					var div = document.createElement("div");
					var a = document.createElement("a");
					a.className = "account-group";
					a.href = "";
					var img = document.createElement("img");
					img.className = "image";
					Cluster.get_user_portrait(UID, function(portrait_path){
						img.src = portrait_path;						
					});
					var strong = document.createElement("strong");
					strong.className = "userName";
					Cluster.get_user_name(UID, function(name){
						strong.innerHTML = "&nbsp;"+ name;	//?????????????haha待定						
					});
					var button = document.createElement("button");
					button.className = "close";
					button.onclick = "Iclose()";
					button.title = "关闭";
					button.innerHTML = "&times";
					var div2 = document.createElement("div");
					div2.className = "follow-container";
					var button2 = document.createElement("button");
					button2.className = "but_follow";
					button2.innerHTML = "关注";
					//联合
					div2.appendChild(button2);
					a.appendChild(img);
					a.appendChild(strong);
					div.appendChild(a);
					div.appendChild(button);
					li.appendChild(div);
					li.appendChild(div2);
					
					//放到<ol>中
					ol.appendChild(li);				
				}
				
				
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

<div class="zong">
        <div class="sankuai">
        <div style="height: 160px;position: relative;">
			<img style="height: 100px;width: 100%;border-radius:5px;" src="JIM.jpg">
			<img class="headImg" src="CcXer0_P_400x400.jpg">
			<p class="title"><button class="medium blue">正在关注</button><img src="QQ图片20170416191834.png"></p>
		</div>
		<div class="desc">
			<p class="name"><span>Jim Carrey</span></p>
			<img src="QQ图片20170416191034.png" />
			<p class="descp1"><span>@JimCarry</span></p>
			<p class="descp2"><span>Actor Jim Carrey</span></p>
		</div>
        </div>
        
        <div class="sankuai">
        <div style="height: 160px;position: relative;">
			<img style="height: 100px;width: 100%;border-radius:5px;" src="ANIMAL.jpg">
			<img class="headImg" src="s4I7kg-j_400x400.jpg">
			<p class="title"><button class="medium blue">正在关注</button><img src="QQ图片20170416191834.png"></p>
		</div>
		<div class="desc">
			<p class="name"><span>Baby Animals</span></p>
			<img src="QQ图片20170416191034.png" />
			<p class="descp1">@Baby Animals</p>
			<p class="descp2">Showcasing the amazing animals of the world!</p>
		</div>
        </div>
        
        <div class="sankuai">
        <div style="height: 160px;position: relative;">
			<img style="height: 100px;width: 100%;border-radius:5px;" src="CUTE2.jpg">
			<img class="headImg" src="CUTE.jpg">
			<p class="title"><button class="medium blue">正在关注</button><img src="QQ图片20170416191834.png"></p>
		</div>
		<div class="desc">
			<p class="name"><span>Cute Overloads</span></p>
			<img src="QQ图片20170416191034.png" />
			<p class="descp1">@Cute Overloads</p>
			<p class="descp2">we DO NOT own content posted. Everything is submissions. Content will be immediately removed or credited by owners request. Enjoy!</p>
		</div>
        </div>
        </div>
        </div>
</body>
</html>
