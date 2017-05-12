<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/twitter_proj/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<base href="<%=basePath%>">
<!--link rel="stylesheet" type="text/css" href="block.css" /-->
	<!-- Javascript -->
	<!--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>-->
    
<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
<link rel="stylesheet" type="text/css" href="style.css" media="all" /> 
<link rel="stylesheet" type="text/css" href="css_us/together.css" media="all" />
<link rel="stylesheet" type="text/css" href="css_us/iconfont.css" />
<link rel="stylesheet" type="text/css" href="plugin/jquery_danchu/reveal.css" />
<!--link rel="stylesheet" type="text/css" href="block.css" /-->
  <!-- Javascript -->
  <!--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>-->
    <script type="text/javascript" src="js/jquery1-9-1.min.js"></script>
  <script type="text/javascript" src="js/kickstart.js"></script>
    <script type="text/javascript" src="css_us/focus.js"></script>
     <script type="text/javascript" src="css_us/article.js"></script>
     <script type="text/javascript" src="css_us/together.js"></script>
     <script type="text/javascript" src="css_us/articlepage.js"></script>
     <!--<script src="http://www.jq22.com/jquery/jquery-1.6.2.js"></script>-->
     <script src="css_us/jquery.min.js"></script>
     <!--<script>
     $('a[data-reveal-id]').live('click', function(e) {
		alert("hello");
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
	});
     </script>-->
     
<title>个人主页</title>

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
                <div style="float:left; width:54px; height:54px"><a id="logo1"><img src="anonymous.jpg" style="width: 40px; height: 40px; margin-top:7px"></a></div>
                <div style="float:left; width:70px; height:54px"><a id="logo"><button class="medium blue" style="margin-top:11px">发推</button></a></div>
            <!--<a id="logo3"><input results="s" type="search" placeholder="搜索 Twitter"></a>-->
    		</div>
            
        </div>
	</nav>
</div>


<div style="background-color: #2aa3ef; height: 225px; width: 100%; ">		<!--蓝色状态栏-->
	
</div>

<div>
	<nav class="navbar1">
		<div class="profile-back">
			<img class="head" src="icons/狗.png">
		
            <ul>
                <li><a href=""><div id="head_t">推文</div><div id="head_t_num">1</div></a><div class="under_center_t"></div></li>
                <li><a href=""><div id="head_f">正在关注</div><div id="head_f_num">1</div></a><div class="under_center_f"></div></li>
                <li><a href=""><div id="head_fd">关注者</div><div id="head_fd_num">1</div></a><div class="under_center_fd" style="margin-left:12px"></div></li>
            </ul>
            <div class="logo0-back">
        		<a id="logo0"><button class="medium blue">编辑个人资料</button></a>
            </div>
        </div>
		
	</nav>
	
</div>
<br><br>


<!--页面部分 -->
<div class="page_container">
  <div class="page_cell">
     <!--左侧博主简介 -->
     <div class="left_column">
        <div class="block">
    	  <div class="intro">
        	<a class="name">Baby Animals</a>
            <p>&nbsp;</p>
            <a class="dest">@Baby Animals</a>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <span>加入于2017年4月</span>
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
                   <div class="content">
                     <div class="stream-item-header"> <a class="account-group" href=""> <img class="image" src="个人主页推文浏览/1.jpg" /> <span><strong id="my-name" class="userName">用户名</strong> </span> <span class="UserNameBreak" style="width:4px; height:16px">&nbsp;</span> </a> <small class="time"> <a href="" class="data-time" data-original-title=""><span class="">4小时</span></a> </small>
                       <button class="but_more" title="更多">...</button>
                       <div class="list">
                         <ul>
                           <li><a class="delete" href="" >删除</a></li>
                         </ul>
                       </div>
                     </div>
                     <div class="text-container">
                       <a href="#" class="big-link" data-reveal-id="myModal" title="点开全文"><p class="text-size">哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈阿哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈哈和哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊</p></a>
                     </div>
                     <div class="stream-item-footer">
                     
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

                     
                       <!--<div class="actionList">
                         <button class="medium blue" onmouseover="this.style.color='#333'" onmouseout="this.style.color='#fff'">回复</button>
                         <button class="medium blue" style="margin-left:100px" onmouseover="this.style.color='#333'" onmouseout="this.style.color='#fff'">转发</button>
                         <button id="favorite" class="medium blue"  style="margin-left:100px" onmouseover="this.style.color='#333'" onmouseout="this.style.color='#fff'">喜欢</button>
                       </div>-->
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
    <ol class="ol_follow">
      <div id="f1"><li class="li_follow">
        <div><a class="account-group" href=""><img class="image" src="个人主页推文浏览/2.jpg"><strong class="userName">&nbsp;Shinobi Ninja</strong>
        </a>
        <button id="close_1" type="button" class="close" onclick="Iclose()" title="关闭">&times;</button></div>
        <div class="follow-container"><button class="but_follow">关注</button></div>

      </li></div>
      <div id="f2"><li class="li_follow">
        <div><a class="account-group" href=""><img class="image" src="个人主页推文浏览/3.jpg"><strong class="userName">&nbsp;用户名</strong>
        </a>
        <button id="close_2" type="button" class="close" onclick="Iclose()" title="关闭">&times;</button></div>
        <div class="follow-container"><button class="but_follow">关注</button></div>
      </li></div>
      <div id="f3"><li class="li_follow">
        <div><a class="account-group" href=""><img class="image" src="个人主页推文浏览/4.jpg"><strong class="userName">&nbsp;用户名</strong>
        </a>
        <button id="close_3" type="button" class="close" onclick="Iclose()" title="关闭">&times;</button></div>
        <div class="follow-container"><button class="but_follow">关注</button></div>
      </li></div>
      <li class="li_follow"><div class="search"><a href="">查找好友</a></div></li>
    </ol>
  </div>
</div>
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
        <div class="article-others" role="others">
            <div class="article-inner">
                <div class="article" style="margin-top:1px">
                    <div class="article-left">
                    	<img src="推文页面缩略/head.jpg" class="head-pic">
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
                    	<div class="article-coment-text">
                        	<br>うんうん、面白い。<br>
                            (*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；<br>
                            (*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)。
                        </div>
                        <div class="article-pic-back">
                            	<img onClick="pic_cli()" src="推文页面缩略/pic.png" class="article-pic" alt>
                                <!--<img src="pic2.jpg" class="article-pic" alt>-->
                        </div>
                        <div class="article-action-list">
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-t" onMouseOver="action_btn_t_on()" onMouseOut="action_btn_t_out()">
                                    	<i class="iconfont icon-zhuanfafa"></i>
                                    	<span>0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-r" onMouseOver="action_btn_r_on()" onMouseOut="action_btn_r_out()">
                                    	<i class="iconfont icon-huifu"></i>
                                   		<span>0</span>
                                    </button>
                                </div>
                                <div class="article-action-btn-back">
                                	<button class="article-action-btn" id="a-action-v" onMouseOver="action_btn_v_on()" onClick="action_btn_v_cli()" onMouseOut="action_btn_v_out()">
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
    <a class="close-reveal-modal" title="关闭">&#215;</a>
    </div>
<!--<div id="myModal" class="reveal-modal">
<h1>jquery导出层</h1>

			<p>This is a default modal in all its glory, but any of the styles here can easily be changed in the CSS.</p>

			<a class="close-reveal-modal">&#215;</a>
</div>-->
</body>
</html>
