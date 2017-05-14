function create_another_article(){
			var article_others=document.createElement("div");
			article_others.className="article-others";
			article_others.setAttribute("id","article-others");
			var article_inner=document.createElement("div");
			article_inner.className="article-inner";
			var article=document.createElement("div");
			
			article.className="article";
			article.style="margin-top:1px";
			
			//左边的部分。
			var article_left=document.createElement("div");
			article_left.className="article-left";
			article_left.setAttribute("id","article-left")
			var head_pic=document.createElement("img");
			head_pic.setAttribute("id","head-pic");
			head_pic.className="head-pic";
			head_pic.src="head.jpg";
			article_left.appendChild(head_pic);
			
			article.appendChild(article_left);
			
			//右边的部分
			var article_right=document.createElement("div");
			article_right.className="article-right";
			article_right.setAttribute("id","article-right");
			
			//右边头部
			var article_right_header=document.createElement("div");
			article_right_header.className="article-right-header";
			var user_link=document.createElement("a");
			user_link.src="";
			user_link.style="float:left; cursor:pointer";
			var article_right_username=document.createElement("div");
			article_right_username.className="article-right-username";
			var username=document.createElement("span");
			username.setAttribute("id","article-nickname");
			username.style="font-family:'微软雅黑';	color:dodgerblue;";
			username.innerHTML="欧摩西罗伊";
			article_right_username.appendChild(username);
			
			var userID=document.createElement("span");
			userID.className="Aid";
			userID.dir="ltr";
			userID.innerHTML="ID:";
			var uid=document.createElement("b");
			uid.setAttribute("id","article-uid");
			uid.innerHTML="jxc";
			userID.appendChild(uid);
			article_right_username.appendChild(userID);
			user_link.appendChild(article_right_username);
			article_right_header.appendChild(user_link);
			
			var article_others_time=document.createElement("div");
			article_others_time.setAttribute("id","article-others-time");
			article_others_time.className="article-others-time";
			article_others_time.innerHTML="11:15  2017/5/12";
			article_right_header.appendChild(article_others_time);
			
			article_right.appendChild(article_right_header);
			
			//内容部分
			var article_coment_text=document.createElement("div");
			article_coment_text.setAttribute("id","article-coment-text");
			article_coment_text.className="article-coment-text";
			article_coment_text.innerHTML="うんうん、面白い。<br>(*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；<br>(*・ω・)(*-ω-)(*・ω・)(*-ω-)ウンウン♪；(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)(*´Д｀)。";
			article_right.appendChild(article_coment_text);
			
			//图片
			var article_pic_back=document.createElement("div");
			article_pic_back.className="article-pic-back";
			var article_pic=document.createElement("img");
			article_pic.className="article-pic";
			article_pic.src="";
			article_pic_back.appendChild(article_pic);
			article_right.appendChild(article_pic_back);
			
			//动作按钮
			var article_action_list=document.createElement("div");
			article_action_list.className="article-action-list";
			
			//转发
			var trans_action_btn_back=document.createElement("div");
			trans_action_btn_back.className="article-action-btn-back";
			var a_action_t=document.createElement("button");
			a_action_t.className="article-action-btn";
			a_action_t.setAttribute("id","article-action-t");
			a_action_t.onmouseover=function(){action_btn_t_on(this);};
			a_action_t.onmouseout=function(){action_btn_t_out(this);};
			var t_icon=document.createElement("i");
			t_icon.className="iconfont icon-zhuanfafa";
			a_action_t.appendChild(t_icon);
			var t_num=document.createElement("span");
			t_num.setAttribute("id", "article-action-t-num");
			t_num.innerHTML="0";
			a_action_t.appendChild(t_num);
			trans_action_btn_back.appendChild(a_action_t);
			
			article_action_list.appendChild(trans_action_btn_back);
			
			//回复
			var re_action_btn_back=document.createElement("div");
			re_action_btn_back.className="article-action-btn-back";
			var a_action_r=document.createElement("button");
			a_action_r.className="article-action-btn";
			a_action_r.setAttribute("id","article-action-r");
			a_action_r.onmouseover=function(){action_btn_r_on(this);};
			a_action_r.onmouseout=function(){action_btn_r_out(this);};
			var r_icon=document.createElement("i");
			r_icon.className="iconfont icon-huifu";
			a_action_r.appendChild(r_icon);
			var r_num=document.createElement("span");
			r_num.setAttribute("id","article-action-r-num");
			r_num.innerHTML="0";
			a_action_r.appendChild(r_num);
			re_action_btn_back.appendChild(a_action_r);
			
			article_action_list.appendChild(re_action_btn_back);
			
			
			
			article_right.appendChild(article_action_list);
			article.appendChild(article_right);
			article_inner.appendChild(article);
			article_others.appendChild(article_inner);

			document.getElementById("comment-c").appendChild(article_others);
		}