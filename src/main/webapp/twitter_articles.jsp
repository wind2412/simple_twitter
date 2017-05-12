<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/twitter_proj/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="js/jquery1-9-1.min.js"></script>        <!-- 每个库所需要的jquery必须精确地放在其之前！！多个jquery库也遵循这个规则！ -->
    <script type="text/javascript" src="js/kickstart.js"></script>
    <link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
    <link rel="stylesheet" type="text/css" href="style.css" media="all" /> 
    <link rel="stylesheet" type="text/css" href="css_us/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="css_us/together.css" />
         <script src="css_us/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="plugin/jquery_danchu/reveal.css" />
   
     <script type="text/javascript" src="css_us/article.js"></script>
     <script type="text/javascript" src="my_js/focus_list.js"></script>
     <script type="text/javascript" src="css_us/together.js"></script>
     <script type="text/javascript" src="css_us/articlepage.js"></script>
     
     <!-- DWR script -->
	<script type='text/javascript' src='/twitter_proj/dwr/engine.js'></script>
	<script type='text/javascript' src='/twitter_proj/dwr/util.js'></script>
  	<script type='text/javascript' src='/twitter_proj/dwr/interface/Cluster.js'></script>
     
<title>我的推文</title>

</head>

<body>
<!--头部导航栏 -->
<div>
	<nav class="navbar" style="border-bottom-color: black; border-bottom-width:thin">	<!-- 去掉了border-style:solid, 否则会感觉header不在垂直的正中央.-->
    	<div class="header">
        	<div class="header-btn-left">
                <ul>
                <li></li>
                <li style=""><a href=""><img src="icons/1.png" height="20px" width="20px"><span id="head_m">主页</span></a><div class="under_head_m"></div></li>
                <li><a href=""><img src="icons/2.png" height="20px" width="20px"><span id="head_i">通知</span></a><div class="under_head_i"></div></li>
                <li><a href=""><img src="icons/3.png" height="20px" width="20px"><span id="head_c">私信</span></a><div class="under_head_c"></div></li>
                </ul>
            </div>
            <div class="logo-center"><a id="logo2"><img src="icons/5.png" style="width: 40px; height: 40px; margin-top:7px"></a></div>
            <div class="header-btn-right">
                <div class="search-back"><input type="text" placeholder="搜索 Twitter" style="font-size: 16px; margin-top:11px"></div>
                <div style="float:left; width:54px; height:54px"><a id="logo1"><img src="" style="width: 40px; height: 40px; margin-top:7px" id="portrait"></a></div>
				<div style="float:left; width:100px; height:54px"><font id="loginusername" style="color: black;"></font></div>               
                 <div style="float:left; width:70px; height:54px"><a id="logo" data-reveal-id="myModal"><button class="medium blue" style="margin-top:11px" onclick="announce()">发推</button></a></div>
            <!--<a id="logo3"><input results="s" type="search" placeholder="搜索 Twitter"></a>-->
    		</div>
            
        </div>
	</nav>
</div>


<div id="bg" style=" height: auto; min-height:380px; width: 100%; ">		<!--蓝色状态栏-->
	<img id="bg_img" class="the-backgroundpic"  style="width:100%; ">
</div>

<div>
	<nav class="navbar1">
		<div class="profile-back">
			<a id="other_head">
				<img class="head" id="bighead" src="" style="visibility: hidden;">
			</a>
		
            <ul>
                <li><a href="" id="head_articles"><div id="head_t">推文</div><div id="head_t_num"></div></a><div class="under_center_t"></div></li>
                <li><a href="" id="head_focusing"><div id="head_f">正在关注</div><div id="head_f_num"></div></a><div class="under_center_f"></div></li>
                <li><a href="" id="head_fansing"><div id="head_fd">关注者</div><div id="head_fd_num"></div></a><div class="under_center_fd" style="margin-left:12px"></div></li>
            </ul>
            <div class="logo0-back">
        		<a id="logo0"></a>		<!-- 编辑个人资料的按钮 -->
            </div>
        </div>
		
	</nav>
	
</div>
<br><br>

<script type="text/javascript">
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
	var other_name = '<%= request.getParameter("usr") == null ? null : new String(request.getParameter("usr").getBytes("iso-8859-1"), "utf-8" ) %>';	//得到请求末尾的query.		但是要注意，可能是null。
	//设置头的“正在关注”等连接 以及头的头像的链接	
	document.getElementById("logo1").href = "/twitter_proj/twitter_focus.jsp?usr="+LogInusername+"&timestamp="+new Date().getTime();
	document.getElementById("head_articles").href = "";		//未设置????????????????在另一边fans也要设置
	document.getElementById("head_focusing").href = "/twitter_proj/twitter_focus.jsp?usr="+other_name+"&timestamp="+new Date().getTime();
	document.getElementById("head_fansing").href = "/twitter_proj/twitter_fans.jsp?usr="+other_name+"&timestamp="+new Date().getTime();
	var other_UID = null;	//如果这里不设置的话，如果query出错的话，比如?usr=zhengxiaoli 那么if(other_uid==0)之后会other_UID未定义。因为并不会定义other_UID. 
	//看query是否合法才能向下进行。因此这里必须同步方式。需要关闭ajax异步。
	dwr.engine.setAsync(false);
	//1.如果query不空，那就检测是否合法，合法就继续走，不合法直接跳页404  2.query空，看是否已经登录，如果登录过(LogInusername不空)则query设为LogInusername 3.否则“请您登录推特主页吧”
	if(other_name != "null")	{		
		Cluster.is_user_in_DB(other_name, function(other_uid){		//通过DWR框架直接调用后端判断用户是否注册过的代码
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
							button.innerHTML = "正在关注";
							button.onclick = function(){
								if(button.innerHTML == "正在关注"){
									button.innerHTML = "关注";
									Cluster.focus_cancelled_oh_no(LogInUID, UID);		//取消关注
								}else{
									button.innerHTML = "正在关注";							
									Cluster.focus_a_user(LogInUID, UID);		//关注
								}
							}
						}else{
							button.innerHTML = "关注";
							button.onclick = function(){
								if(button.innerHTML == "正在关注"){
									button.innerHTML = "关注";
									Cluster.focus_cancelled_oh_no(LogInUID, UID);		//取消关注
								}else{
									button.innerHTML = "正在关注";							
									Cluster.focus_a_user(LogInUID, UID);		//关注
								}
							}
						}
					});
				}
			}
		});
	}else if(LogInusername != "null")	{
		window.location.href = "/twitter_proj/twitter_focus.jsp?usr="+LogInusername+"&timestamp="+new Date().getTime();
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

<!--页面部分 -->
<div class="page_container">
  <div class="page_cell">
     <!--左侧博主简介 -->
     <div class="left_column">
        <div class="block">
    	  <div class="intro">
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
        </div>
       </div>
     </div>
     <!--中间推文 -->
     <div class="blog">
        <div class="artical-wide">
   <div class="artical-heading">
      <div class="heading-spacer"></div>
      <div class="artical-heading-content">
         <ul class="heading-toggle">
            <li class="toggle-item">推文</li>
            <li class="toggle-item">回复</li>
         </ul>
      </div>
   </div>
   <div id="artical" class="artical-content">
      <div class="stream-container">
         <div class="stream">
            <ol id="stream-items-id" class="stream-items">
               <li class="stream-item">
                 <div class="text">
                   
                   
                   
                   <div class="article-others" role="others">
            <div class="article-inner">
                <div class="article" style="margin-top:1px">
                    <div class="article-left">
                    	<img src="head.jpg" class="head-pic">
                    </div>
                    <div class="article-right">
                    	<div class="article-right-header">
                        	<a src="" style="float:left; cursor:pointer"><div class="article-right-username">
                            	<span style="font-family:'微软雅黑';	color:dodgerblue;">欧摩西罗伊</span>
                                <span class="Aid" dir="ltr">
                                    ID:
                                    <b>jxc</b>
                                </span>
                            </div></a>
                            <div class="article-others-time">·21:20 2017/5/8</div>
                        </div>
                    	<a style="color:#000000; text-decoration:none" data-reveal-id="myModal" title="点开全文"><div class="article-coment-text">
                        	うんうん、面白い。<br>
                            (*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；<br>
                            (*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)。
                        </div></a>
                        <div class="article-pic-back">
                            	<!--<img onClick="pic_cli()" src="pic.png" class="article-pic" alt>-->
                                <img src="pic2.jpg" class="article-pic" alt>
                        </div>
                        <div class="article-action-list">
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-t" onMouseOver="action_btn_t_on(this)" onMouseOut="action_btn_t_out(this)">
                                    	<i class="iconfont icon-zhuanfafa"></i>
                                    	<span>0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-r" onMouseOver="action_btn_r_on(this)" onMouseOut="action_btn_r_out(this)">
                                    	<i class="iconfont icon-huifu"></i>
                                   		<span>0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-v" onMouseOver="action_btn_v_on(this)" onClick="action_btn_v_cli()" onMouseOut="action_btn_v_out(this)">
                                        <i class="iconfont icon-jinlingyingcaiwangtubiao24"></i>
                                        <span>1</span>
                                    </button>
                                    <button class="article-action-btn" id="a-action-ved" onClick="action_btn_ved_cli()">
                                        <i class="iconfont icon-jinlingyingcaiwangtubiao24"></i>
                                        <span>2</span>
                                    </button>

                                </div>
                            </div>
                        <div class="article-others-footer">
                        
                        </div>
                    </div>
                   
                </div>
            </div>
        </div>
                   
                   
                   
                   
                 </div>
               </li>
            </ol>
            </div>
         </div>
      </div>
</div>
     </div>
     <!--右侧推荐和话题 -->
     <div class="follow_topic">
       <div class="recommanded">
  <div class="rec-header">推荐关注</div>
  <div class="rec-container">
    <ol class="ol_follow"  id="recommend_list">
    	
    </ol>
  </div>
</div>

	<script type="text/javascript">
		focus_list();
	</script>
     <!--趋势-->
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
     </div>
  </div>
</div>

<div id="myModal" class="reveal-modal">
 <div class="article-container">
        <div class="article-miss"  id="article-miss" onclick="exit_article()">
                </div>
        		<div class="article-main" role="main" id="article-main">
                	<div class="article" id="main-article">
                        	<div class="article-header">
                            	<div class="user-link-in-a">
                                    <a href="" class="user-in-a" onMouseOver="user_on()" onMouseOut="user_out()">
                                    	<div class="user-head">
                                            	<img src="head.jpg" class="head-pic" id="head-pic" alt>
                                        </div>
                                        <div class="user-name">
                                            <div class="nickname" id="article-nickname">欧摩西罗伊</div>
                                            
                                            <span class="Aid" dir="ltr">
                                                ID:
                                                <b id="article-uid">jxc</b>
                                            </span>
                                        </div>
                                    </a>
                                </div>
                                <div class="follow-btn-back">
                                    <button class="user-action-follow-btn" type="button" onClick="follow()" onMouseOver="follow_btn_on(this)" id="user-action-following" onMouseOut="follow_btn_out(this)">
                                        关注
                                    </button>
                                    <button class="user-action-follow-btn" type="button" onClick="unfollow()" onMouseOver="followed_btn_on(this)" id="user-action-followed" onMouseOut="followed_btn_out(this)">
                                        已关注
                                    </button>
                                </div>
                            </div>
                            <div class="article-text">
                            	<p class="article-text-content" id="article-text-content">
                                	2333333333333333<br>
                                	23333333333333333<br>
                                	233333333333333333333333333333333333333333333333333333333333333333333333333333333
                                </p>
                            </div>
                            <div class="article-pic-back">
                            	<img onClick="pic_cli()" src="pic.png" id="article-pic" class="article-pic" alt>
                                <!--<img src="pic2.jpg" class="article-pic" alt>-->
                            </div>
                            <div class="t-v-back">
                            	<ul class="t-v">
                                	<li id="t" class="t-v-li" onClick="t_cli()">
                                    	<div id="t-text">转推</div>
                                        <span class="t-v-num" id="aritcle-t-num" >0</span>
                                    </li>
                                    <li id="v" class="t-v-li" onClick="v_cli()">
                                    	<div id="v-text">点赞</div>
                                        <span class="t-v-num" id="article-v-num">0</span>
                                    </li>
                                    <li id="v-user" class="t-v-li">
                                    	<a href="" class="v-user-head-a" onMouseOver="user_on()" onMouseOut="user_out()">
                                        	<img src="head.jpg" class="v-user-head">
                                        </a>
                                        <a href="" class="v-user-head-a" onMouseOver="user_on()" onMouseOut="user_out()">
                                        	<img src="head2.jpg" class="v-user-head" >
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="article-time">
                            	<span class="article-time-text">
                                	<span id="article-timer">16:36 &nbsp;&nbsp; 2017/5/6</span>
                                </span>
                            </div> 
                            <div class="article-action-list">
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-t" onMouseOver="action_btn_t_on(this)" onMouseOut="action_btn_t_out(this)">
                                    	<i class="iconfont icon-zhuanfafa"></i>
                                    	<span id="article-action-t-num">0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-r" onMouseOver="action_btn_r_on(this)" onMouseOut="action_btn_r_out(this)">
                                    	<i class="iconfont icon-huifu"></i>
                                   		<span id="article-action-r-num">0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-v" onMouseOver="action_btn_v_on(this)" onClick="action_btn_v_cli()" onMouseOut="action_btn_v_out(this)">
                                        <i class="iconfont icon-jinlingyingcaiwangtubiao24"></i>
                                        <span>1</span>
                                    </button>
                                    <button class="article-action-btn" id="a-action-ved" onClick="action_btn_ved_cli()">
                                        <i class="iconfont icon-jinlingyingcaiwangtubiao24"></i>
                                        <span>2</span>
                                    </button>

                                </div>
                            </div>
                    	</div>
                       	<div style="width:100%; height:39px;" id="space-between-c-a">
                        	
                        </div>
                        <div class="article-comment">
                        	
                        	<div class="commenting" id="article-commenting">
                                <div class="article-comment-text-box-back" id="article-comment-text-box-back">
                                    <form class="article-comment-text-box-f">
                                        <img src="head.jpg" class="article-commenter-head">
                                        <textarea type="text" class="article-comment-text-box" maxlength="140" id="article-comment-text-box" placeholder="发布你的回复" onFocus="comment_write()" onBlur="comment_text_box_blur()"></textarea>
                                        
                                    </form>
                                </div>
                                <div class="comment-btn-back" id="comment-btn-back">
                                    <div class="comment-pic-btn-back">
                                        <button class="comment-btn" id="aritcle-1-add-pic-btn" onMouseOver="comment_btn_on(this)" onMouseOut="comment_btn_out(this)">
                                        	<i class="iconfont icon-xiangji"></i>
                                        </button>
                                        <button class="comment-btn" id="article-1-add-gif-btn" onMouseOver="comment_btn_on(this)" onMouseOut="comment_btn_out(this)">
                                        	<i class="iconfont icon-gif"></i>
                                        </button>
                                        <button class="comment-btn" id="article-1-add-location-btn" onMouseOver="comment_btn_on(this)" onMouseOut="comment_btn_out(this)">
                                        	<i class="iconfont icon-icon"></i>
                                        </button>
                                    </div>
                                    <div class="comment-up">
                                    	<button class="comment-up-btn" id="comment-up-btn">发送</button>
                                    </div>
                                </div>
                            </div>
                            <!--                    *********没有内容***********     -->
                            
                            
					                            
                        </div>
                        <!--                        *********      评论        ***********                             -->
                        
                
                </div>
    </div>
    <a class="close-reveal-modal" title="关闭">&#215;</a>
    </div>
<!--<div id="myModal" class="reveal-modal">
<h1>jquery导出层</h1>

			<p>This is a default modal in all its glory, but any of the styles here can easily be changed in the CSS.</p>

			<a class="close-reveal-modal">&#215;</a>
</div>-->



<script>
	function announce(){
		document.getElementById("space-between-c-a").style.display="none";
		document.getElementById("main-article").style.display="none";
		document.getElementById("article-commenting").style.height="256px";
		document.getElementById("article-comment-text-box-back").style.height="192px";
		document.getElementById("article-comment-text-box").style.height="168px";
		document.getElementById("comment-btn-back").style.display="block";
		
	}
	
	
	
		
		
</script>




</body>
</html>
