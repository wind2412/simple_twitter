function safari(){
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
}



function start_up(){
		//设置头的“正在关注”等连接
		document.getElementById("logo1").href = "/twitter_proj/twitter_focus.jsp?usr="+LogInusername+"&timestamp="+new Date().getTime();		//这里需要改。应该先跳到对方的推文列表。
		document.getElementById("head_articles").href = "/twitter_proj/twitter_articles.jsp?usr="+other_name+"&timestamp="+new Date().getTime();
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
								button.innerHTML = "正在关注";
								button.onclick = function(){
									if(LogInUID == 0)	return;
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
									if(LogInUID == 0)	return;
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
		
			
		//alert(document.getElementById("bg_img").src == "");	//如果src这个属性没写，确实是等于""的.
		document.getElementById("bg").style.backgroundColor = main_page();	//随机设置一个颜色 如果用户有自己的大图片，就覆盖了。
		
		
		
		
		
	
}

