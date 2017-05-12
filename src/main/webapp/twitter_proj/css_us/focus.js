// JavaScript Document
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
		$("#f1").fadeOut(500);
		onfocus = this.blur();
		
		$("#f1").fadeIn(500);});
	$("#close_2").click(function(){
		onfocus = this.blur();
		$("#f2").fadeOut(500);
		$("#f2").fadeIn(500);});
	$("#close_3").click(function(){
		onfocus = this.blur();
		$("#f3").fadeOut(500);
		$("#f3").fadeIn(500);});
});