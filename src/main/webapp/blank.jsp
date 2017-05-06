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

<script type="text/javascript">      
	var xmlHttp;    
    function createXMLHttp(){ 
		if(window.XMLHttpRequest) { //Mozilla 浏览器  
    		xmlHttp = new XMLHttpRequest();  
		}  
		else if (window.ActiveXObject) { // IE浏览器  
    		try {  
    			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
    		} catch (e) {  
        			try {  
        				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
        			} catch (e) {}  
    		}  
		}         
	}          
    function follow(){                
    	createXMLHttp(); 
        xmlHttp.onreadystatechange = followCallback; 
		var url = "Check.jsp";               
		xmlHttp.open("GET",url,true);               
        xmlHttp.send(null);       
	}          
    function followCallback(){        
		if(xmlHttp.readyState==4){                  
			if(xmlHttp.status==200){
				var info = xmlHttp.responseText.substr(0, 4); 
			alert(info);      
            	document.getElementById("follow").value=info;                                      
			}             
		}         
	}
</script>

<div style="z-index: 1001">
	<nav class="navbar" style="border-bottom-color: black; border-bottom-width:thin">	<!-- 去掉了border-style:solid, 否则会感觉header不在垂直的正中央.-->
		<a id="logo"><button class="medium blue">发推</button></a>
		<a id="logo1"><img src="anonymous.jpg" style="width: 40px; height: 40px"></a>
		<a id="logo2" style="text-align: center"><img src="icons/5.png" style="width: 40px; height: 40px"></a>
		<a id="logo3"><input type="text" placeholder="搜索 Twitter" style="font-size: 16px"></a>
		<!--<a id="logo3"><input results="s" type="search" placeholder="搜索 Twitter"></a>-->

		<ul>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
		<li style=""><a href=""><img src="icons/1.png" height="20px" width="20px"><span>主页</span></a></li>
		<li><a href=""><img src="icons/2.png" height="20px" width="20px"><span>通知</span></a></li>
		<li><a href=""><img src="icons/3.png" height="20px" width="20px"><span>私信</span></a></li>
		</ul>
	</nav>
</div>


<div style="background-color: #2aa3ef; height: 225px; width: 100%; ">		<!--蓝色状态栏-->
	
</div>

<div>
	<nav class="navbar1">
		
		<div class="head" style="z-index: 0"><img src="icons/doge.png"></div>
		<a id="logo0"><button class="medium blue">编辑个人资料</button></a>
		
		<ul>
		<li><a href=""><div>推文</div><div>1</div></a></li>
		<li><a href=""><div>正在关注</div><div>1</div></a></li>
		<li><a href=""><div>关注者</div><div>1</div></a></li>
		</ul>
		
	</nav>
	
</div>




<hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr><hr>


</body>
</html>
