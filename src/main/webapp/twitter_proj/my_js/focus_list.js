function focus_list(){
				//获得数据
				if(LogInUID != 0)
					Cluster.get_probably_acquaintance(LogInUID, function(set){
			//		var str = "";
			//		for(var i = 0; i < set.length; i ++){
			//			str += set[i];
			//			str += " ";
			//		}
						var ptr = 0;		//关注列表一次显示3个。ptr是set的指针。
						var list_num = 0;	//显示在上边的关注列表的人数。如果叉掉，就少一个。然后点击事件会把list_num-1 然后如果set里边还有，即ptr没到set.length，那么list_num再++，
						var ol = document.getElementById("recommend_list");
						for(; ptr < set.length && list_num < 3; ptr ++){
							//放到<ol>中
							ol.appendChild(get_an_acquaintance(set[ptr], (ptr+1)));
							list_num ++;
						}
						
						
						//淡入淡出效果  必须要在js生成之后加载才管用。
						$(document).ready(function() {
						    $(".but_follow").mouseenter(function(){//鼠标移至关注变色
								$(this).css({"background":"dodgerblue","cursor":"hand"});
							});
							$(".but_follow").mouseleave(function(){//移开时恢复
								$(this).css("background","#f5f8fa");  
							});
							$(".but_follow").click(function(){//点击关注按钮不聚焦
								onfocus = this.blur();  
							});
							$("#close_1").click(function(){//关闭推荐关注，淡出
								onfocus = this.blur();
								$("#f1").fadeOut(300);
								if(ptr < set.length){
									$("#f1").fadeIn(400);
									sleep(300);
									get_portrait(set[ptr], 1);
									get_name(set[ptr], 1);
									ptr ++;
								}
							});
							$("#close_2").click(function(){
								onfocus = this.blur();
								$("#f2").fadeOut(300);
								if(ptr < set.length){
									$("#f2").fadeIn(400);
									sleep(300);
									get_portrait(set[ptr], 2);
									get_name(set[ptr], 2);
									ptr ++;
								}
							});
							$("#close_3").click(function(){
								onfocus = this.blur();
								$("#f3").fadeOut(300);
								if(ptr < set.length){
									$("#f3").fadeIn(400);
									sleep(300);
									get_portrait(set[ptr], 3);
									get_name(set[ptr], 3);
									ptr ++;
								}
							});
						});
					});
				
				function get_portrait(UID, id){
					Cluster.get_user_portrait(UID, function(portrait_path){
					//	alert(UID + "..." + portrait_path);
						document.getElementById("img_"+id).src = portrait_path == null ? "portraits/anonymous.jpg" : portrait_path;						
					});
				}
				
				function get_name(UID, id){
					Cluster.get_user_name(UID, function(name){
						document.getElementById("link_"+id).href = "/twitter_proj/twitter_focus.jsp?usr="+name+"&timestamp="+new Date().getTime();
						document.getElementById("name_"+id).innerHTML = "&nbsp;"+ name;					
					});
				}
				
				function sleep(numberMillis) { 
					var now = new Date(); 
					var exitTime = now.getTime() + numberMillis; 
					while (true) { 
						now = new Date(); 
						if (now.getTime() > exitTime) return; 
					} 
				}
				
				//推荐关注的动态生成
				function get_an_acquaintance(UID, id) {
					var big_div = document.createElement("div");
					big_div.setAttribute("id", "f"+id);
					var li = document.createElement("li");
					li.className = "li_follow";
					var div = document.createElement("div");
					var a = document.createElement("a");
					a.className = "account-group";
					a.setAttribute("id", "link_"+id);
					var img = document.createElement("img");
					img.className = "image";
					img.setAttribute("id", "img_"+id);
					Cluster.get_user_portrait(UID, function(portrait_path){
				//	alert(UID + "..." + portrait_path);
						img.src = portrait_path == null ? "portraits/anonymous.jpg" : portrait_path;						
					});
					var strong = document.createElement("strong");
					strong.className = "userName";
					strong.setAttribute("id", "name_"+id);
					Cluster.get_user_name(UID, function(name){
						a.href = "/twitter_proj/twitter_focus.jsp?usr="+name+"&timestamp="+new Date().getTime();
						strong.innerHTML = "&nbsp;"+ name;					
					});
					var button = document.createElement("button");
					button.setAttribute("id", "close_"+id);
					button.className = "close";
					button.onclick = "Iclose()";
					button.title = "关闭";
					button.innerHTML = "&times";
					
					
					
					var div2 = document.createElement("div");
					div2.className = "follow-container";
					var button2 = document.createElement("button");
					button2.className = "but_follow";
					button2.innerHTML = "关注";
					button2.onclick = function(){
						if(LogInUID == 0)	return;
						//点击按钮要修改前端静态页面的关注人数哦
						var focus_div = document.getElementById("head_f_num");		
						if(button2.innerHTML == "正在关注"){
							button2.innerHTML = "&nbsp;&nbsp;关注&nbsp;&nbsp;";
							Cluster.focus_cancelled_oh_no(LogInUID, UID);		//取消关注
							if(other_UID == LogInUID){		//如果是自己的页面才加。
								focus_div.innerHTML = parseInt(focus_div.innerHTML) - 1;							
							}
						}else{
							button2.innerHTML = "正在关注";							
							Cluster.focus_a_user(LogInUID, UID);		//关注
							if(other_UID == LogInUID){		//如果是自己的页面才加。
								focus_div.innerHTML = parseInt(focus_div.innerHTML) + 1;							
							}
						}
					}
					
					//联合
					big_div.appendChild(li);
					div2.appendChild(button2);
					a.appendChild(img);
					a.appendChild(strong);
					div.appendChild(a);
					div.appendChild(button);
					li.appendChild(div);
					li.appendChild(div2);
					
					return big_div;
				}
				
}