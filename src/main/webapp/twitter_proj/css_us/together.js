// JavaScript Document
$(document).ready(function(){
	//头部的js效果
	$("span#head_m,span#head_i,span#head_c,#head_t,#head_f,#head_fd,#head_t_num,#head_f_num,#head_fd_num").mouseenter(function(){
		$(this).css("color","#0084B4")});
	$("span#head_m,span#head_i,span#head_c,#head_t,#head_f,#head_fd,#head_t_num,#head_f_num,#head_fd_num").mouseleave(function(){
		$(this).css("color","#6E6D73")});
	$("span#head_m").mouseenter(function(){
		$(".under_head_m").show();});
	$("span#head_m").mouseleave(function(){
		$(".under_head_m").hide();});
	$("span#head_i").mouseenter(function(){
		$(".under_head_i").show();});
	$("span#head_i").mouseleave(function(){
		$(".under_head_i").hide();});
		$("span#head_c").mouseenter(function(){
		$(".under_head_c").show();});
	$("span#head_c").mouseleave(function(){
		$(".under_head_c").hide();});
	//中间js效果
	$("#head_t").mouseenter(function(){
		$(".under_center_t").show();});
	$("#head_t").mouseleave(function(){
		$(".under_center_t").hide();});
	$("#head_f").mouseenter(function(){
		$(".under_center_f").show();});
	$("#head_f").mouseleave(function(){
		$(".under_center_f").hide();});
	$("#head_fd").mouseenter(function(){
		$(".under_center_fd").show();});
	$("#head_fd").mouseleave(function(){
		$(".under_center_fd").hide();});
	$("#head_t_num").mouseenter(function(){
		$(".under_center_t").show();});
	$("#head_t_num").mouseleave(function(){
		$(".under_center_t").hide();});
	$("#head_f_num").mouseenter(function(){
		$(".under_center_f").show();});
	$("#head_f_num").mouseleave(function(){
		$(".under_center_f").hide();});
	$("#head_fd_num").mouseenter(function(){
		$(".under_center_fd").show();});
	$("#head_fd_num").mouseleave(function(){
		$(".under_center_fd").hide();});

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