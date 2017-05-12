// JavaScript Document
$(document).ready(function() {
    $(".text").mouseenter(function(){
		$(this).css("background","#e6ecf0");
	});
	$(".text").mouseleave(function(){
		$(this).css("background","#fff");  
	});
	$(".userName").mouseenter(function(){
		$(this).css({"color":"#2aa3ef","text-decoration":"underline"});
	});
	$(".userName").mouseleave(function(){
		$(this).css({"color":"#000","text-decoration":"none"});
	});
	$(".image").mouseenter(function(){
		$("#my-name").css({"color":"#2aa3ef","text-decoration":"underline"});
	});
	$(".image").mouseleave(function(){
		$("#my-name").css({"color":"#000","text-decoration":"none"});
	});
});