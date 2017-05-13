// JavaScript Document


function exit_article(aid){
	//document.getElementsByClassName("article-container")[0].style.display="none";
	//document.getElementById("article-container").style.display="none";
	//document.getElementById(id+"-miss").style.display="none";
	
	//将所有改变id的标签全都改回去。
	document.getElementById("comment-c").innerHTML="";
	document.getElementById("comment-t").innerHTML="";
	document.getElementById("article:"+aid+"-main").setAttribute("id","article-main");//主框
	document.getElementById("head:"+aid+ "-pic").setAttribute("id","head-pic");//头像
	document.getElementById("article:"+aid+ "-nickname").setAttribute("id","article-nickname");//名字
	document.getElementById("article:"+aid+"-uid").setAttribute("id","article-uid");//uid
	document.getElementById("article:"+aid+"-text-content").setAttribute("id","article-text-content");//内容
	document.getElementById("article:"+aid+"-t-num").setAttribute("id","aritcle-t-num");//转发数的框
	document.getElementById("article:"+aid+"-v-num").setAttribute("id","article-v-num");//点赞数的框
	document.getElementById("article:"+aid+"-timer").setAttribute("id","article-timer");//时间
	document.getElementById("article:"+aid+"-action-v-num").setAttribute("id","article-action-v-num");//点赞按钮
	document.getElementById("article:"+aid+"-action-v").setAttribute("id","article-action-v");
	document.getElementById("article:"+aid+"-action-t-num").setAttribute("id","article-action-t-num");//转发按钮
	document.getElementById("article:"+aid+"-action-t").setAttribute("id","article-action-t");
	document.getElementById("article:"+aid+"-action-r-num").setAttribute("id","article-action-r-num");//评论按钮
	document.getElementById("article:"+aid+"-action-r").setAttribute("id","article-action-r");
}


//JavaScript Document
$(document).ready(function(){
	//头部的js效果
	$("li#head_m").mouseenter(function(){
		$("span#head_m").css("color","#0084B4");
		$(".under_head_m").show();});
	$("li#head_m").mouseleave(function(){
		$("span#head_m").css("color","#6E6D73");
		$(".under_head_m").hide();});
	$("li#head_i").mouseenter(function(){
		$("span#head_i").css("color","#0084B4");
		$(".under_head_i").show();});
	$("li#head_i").mouseleave(function(){
		$("span#head_i").css("color","#6E6D73");
		$(".under_head_i").hide();});
	$("li#head_c").mouseenter(function(){
		$("span#head_c").css("color","#0084B4");
		$(".under_head_c").show();});
	$("li#head_c").mouseleave(function(){
		$("span#head_c").css("color","#6E6D73");
		$(".under_head_c").hide();});
	//中间js效果
	if(this_page != 1){
		$("li#head_t").mouseenter(function(){
			$("div#head_t").css("color","#2aa3ef");
			$("div#head_t_num").css("color","#2aa3ef");
			$(".under_center_t").show();});
		$("li#head_t").mouseleave(function(){
			$("div#head_t").css("color","#6E6D73");
			$("div#head_t_num").css("color","#6E6D73");
			$(".under_center_t").hide();});		
	}else{
			$("div#head_t").css("color","#2aa3ef");
			$(".under_center_t").show();		
	}
	if(this_page != 2){
		$("li#head_f").mouseenter(function(){
			$("div#head_f").css("color","#2aa3ef");
			$("div#head_f_num").css("color","#2aa3ef");
			$(".under_center_f").show();});
		$("li#head_f").mouseleave(function(){
			$("div#head_f").css("color","#6E6D73");
			$("div#head_f_num").css("color","#6E6D73");
			$(".under_center_f").hide();});
	}else{
			$("div#head_f").css("color","#2aa3ef");
			$(".under_center_f").show();	
	}
	if(this_page != 3){
		$("li#head_fd").mouseenter(function(){
			$("div#head_fd").css("color","#2aa3ef");
			$("div#head_fd_num").css("color","#2aa3ef");
			$(".under_center_fd").show();});
		$("li#head_fd").mouseleave(function(){
			$("div#head_fd").css("color","#6E6D73");
			$("div#head_fd_num").css("color","#6E6D73");
			$(".under_center_fd").hide();});		
	}else{
			$("div#head_fd").css("color","#2aa3ef");
			$(".under_center_fd").show();		
	}
	
	//收起/下拉列表
	$(".but_more").click(function(){
		onfocus = this.blur();
		$(".list").toggle();});
	$("a.delete").mouseenter(function(){
		$(".list").css("background","#f5f8fa");
		});
	$("a.delete").mouseleave(function(){
		$(".list").css("background","#FFF");
		});
	//删除推文
	$("a.delete").click(function(){
		$("li.stream-item").remove();
		alert("已删除！")});
	$("#favorite").click(function(){
		$(this).css("color","red")});
		
		
		//推文弹出框效果
		$('a[data-reveal-id]').live('click', function(e) {
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
	});
	    /*---------------------------
 Defaults for Reveal
----------------------------*/
	 
/*---------------------------
 Listener for data-reveal-id attributes
----------------------------*/

	

/*---------------------------
 Extend and Execute
----------------------------*/

    $.fn.reveal = function(options) {
        
        
        var defaults = {  
	    	animation: 'fadeAndPop', //fade, fadeAndPop, none
		    animationspeed: 300, //how fast animtions are
		    closeonbackgroundclick: true, //if you click background will modal close?
		    dismissmodalclass: 'close-reveal-modal' //the class of a button or element that will close an open modal
    	}; 
    	
        //Extend dem' options
        var options = $.extend({}, defaults, options); 
	
        return this.each(function() {
        
/*---------------------------
 Global Variables
----------------------------*/
        	var modal = $(this),
        		topMeasure  = parseInt(modal.css('top')),
				topOffset = modal.height() + topMeasure,
          		locked = false,
				modalBG = $('.reveal-modal-bg');

/*---------------------------
 Create Modal BG
----------------------------*/
			if(modalBG.length == 0) {
				modalBG = $('<div class="reveal-modal-bg" />').insertAfter(modal);
			}		    
     
/*---------------------------
 Open & Close Animations
----------------------------*/
			//Entrance Animations
			modal.bind('reveal:open', function () {
			  modalBG.unbind('click.modalEvent');
				$('.' + options.dismissmodalclass).unbind('click.modalEvent');
				if(!locked) {
					lockModal();
					if(options.animation == "fadeAndPop") {
						modal.css({'top': $(document).scrollTop()-topOffset, 'opacity' : 0, 'visibility' : 'visible'});
						modalBG.fadeIn(options.animationspeed/2);
						modal.delay(options.animationspeed/2).animate({
							"top": $(document).scrollTop()+topMeasure + 'px',
							"opacity" : 1
						}, options.animationspeed,unlockModal());					
					}
					if(options.animation == "fade") {
						modal.css({'opacity' : 0, 'visibility' : 'visible', 'top': $(document).scrollTop()+topMeasure});
						modalBG.fadeIn(options.animationspeed/2);
						modal.delay(options.animationspeed/2).animate({
							"opacity" : 1
						}, options.animationspeed,unlockModal());					
					} 
					if(options.animation == "none") {
						modal.css({'visibility' : 'visible', 'top':$(document).scrollTop()+topMeasure});
						modalBG.css({"display":"block"});	
						unlockModal()				
					}
				}
				modal.unbind('reveal:open');
			}); 	

			//Closing Animation
			modal.bind('reveal:close', function () {
			  if(!locked) {
					lockModal();
					if(options.animation == "fadeAndPop") {
						modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
						modal.animate({
							"top":  $(document).scrollTop()-topOffset + 'px',
							"opacity" : 0
						}, options.animationspeed/2, function() {
							modal.css({'top':topMeasure, 'opacity' : 1, 'visibility' : 'hidden'});
							unlockModal();
						});					
					}  	
					if(options.animation == "fade") {
						modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
						modal.animate({
							"opacity" : 0
						}, options.animationspeed, function() {
							modal.css({'opacity' : 1, 'visibility' : 'hidden', 'top' : topMeasure});
							unlockModal();
						});					
					}  	
					if(options.animation == "none") {
						modal.css({'visibility' : 'hidden', 'top' : topMeasure});
						modalBG.css({'display' : 'none'});	
					}		
				}
				modal.unbind('reveal:close');
			});     
   	
/*---------------------------
 Open and add Closing Listeners
----------------------------*/
        	//Open Modal Immediately
    	modal.trigger('reveal:open')
			
			//Close Modal Listeners
			var closeButton = $('.' + options.dismissmodalclass).bind('click.modalEvent', function () {
			  modal.trigger('reveal:close')
			});
			
			if(options.closeonbackgroundclick) {
				modalBG.css({"cursor":"pointer"})
				modalBG.bind('click.modalEvent', function () {
				  modal.trigger('reveal:close')
				});
			}
			$('body').keyup(function(e) {
        		if(e.which===27){ modal.trigger('reveal:close'); } // 27 is the keycode for the Escape key
			});
			
			
/*---------------------------
 Animations Locks
----------------------------*/
			function unlockModal() { 
				locked = false;
			}
			function lockModal() {
				locked = true;
			}	
			
        });//each call
    }//orbit plugin call
	});