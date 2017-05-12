"use strict";
/*
    ***********************关闭*******************************
*/
function exit_article(aid){
	//document.getElementsByClassName("article-container")[0].style.display="none";
	document.getElementById("article-container").style.display="none";
	//document.getElementById(id+"-miss").style.display="none";
	
	//将所有改变id的标签全都改回去。
	document.getElementById("article:"+aid+"-main").setAttribute("id","article-main");//主框
	document.getElementById("head:"+aid+ "-pic").setAttribute("id","head-pic");//头像
	document.getElementById("article:"+aid+ "-nickname").setAttribute("id","article-nickname");//名字
	document.getElementById("article:"+aid+"-uid").setAttribute("id","article-uid");//uid
	document.getElementById("article:"+aid+"-text-content").setAttribute("id","article-text-content");//内容
	document.getElementById("article:"+aid+"-t-num").setAttribute("id","aritcle-t-num");//转发数的框
	document.getElementById("article:"+aid+"-v-num").setAttribute("id","article-v-num");//点赞数的框
	document.getElementById("article:"+aid+"-timer").setAttribute("id","article-timer");//时间
	document.getElementById("article:"+aid+"-action-t-num").setAttribute("id","article-action-t-num");//转发按钮
	document.getElementById("article:"+aid+"-action-r-num").setAttribute("id","article-action-r-num");//评论按钮
}
//***********************************************************

/*
	***********************关注按钮相关************************
*/
//*************************未关注按钮**************************
function follow(){
	document.getElementById("user-action-following").style.display="none";
	document.getElementById("user-action-followed").style.display="block";
}
function follow_btn_on(btn){
	//document.getElementById(id).style.background="dodgerblue";
	btn.style.background="dodgerblue";
}
function follow_btn_out(btn){
	//document.getElementById(id).style.background="rgba(170,184,194,1.00)";
	btn.style.background="rgba(170,184,194,1.00)";
}
//************************已关注按钮***************************
function unfollow(){
	document.getElementById("user-action-followed").style.display="none";
	document.getElementById("user-action-following").style.display="block";
}
function followed_btn_on(btn){
	//document.getElementById(id)
	btn.style.background="#C50101";
	//document.getElementById(id)
	btn.innerHTML="取消关注";
}
function followed_btn_out(btn){
	//document.getElementById(id)
	btn.style.background="dodgerblue";
	//document.getElementById(id)
	btn.innerHTML="已关注";
}
//************************************************************

/*
	************************内容******************************
*/
//**************************图片******************************
function pic_cli( ){
	//显示大图
}
//***********************************************************

/*
    ************************转推和点赞栏***********************
*/
//**************************转发数****************************
function t_cli(){
	//显示转发界面
}
//**************************点赞数****************************
function v_cli(){
	//显示点赞列表
}
//**************************点赞用户头像***********************
function user_on( ){
	//显示个人信息
}
function user_out(){
	//关闭个人信息
}
//***********************************************************

/*
	************************文章操作栏************************
*/
//**************************转发按钮**************************
function action_btn_t_on(btn){
	//document.getElementById("a-action-t")
	btn.style.color="#1FA3DB";
}
function action_btn_t_out(btn){
	//document.getElementById("a-action-t")
	btn.style.color="rgba(170,184,194,1.00)";
}
//**************************回复按钮**************************
function action_btn_r_on(btn){
	//document.getElementById("a-action-r")
	btn.style.color="#40DB48";
}
function action_btn_r_out(btn){
	//document.getElementById("a-action-r")
	btn.style.color="rgba(170,184,194,1.00)";
}
//**************************点赞按钮**************************
function action_btn_v_on(btn){
	//document.getElementById("a-action-v")
	btn.style.color="#EB2462";
}
function action_btn_v_out(btn){
	//document.getElementById("a-action-v")
	btn.style.color="rgba(170,184,194,1.00)";
}
function action_btn_v_cli(){
	document.getElementById("a-action-v").style.display="none";
	document.getElementById("a-action-ved").style.display="block";
}
function action_btn_ved_cli(){
	document.getElementById("a-action-ved").style.display="none";
	document.getElementById("a-action-v").style.display="block";
}

/*
	***********************回复栏*****************************
*/
//*************************回复框*****************************
function comment_write(){
	document.getElementById("article-commenting").style.height="292px";
	document.getElementById("article-comment-text-box-back").style.height="192px";
	document.getElementById("article-comment-text-box").style.height="168px";
	document.getElementById("comment-btn-back").style.display="block";
}
function comment_text_box_blur(){
	document.getElementById("article-commenting").style.height="102px";
	document.getElementById("article-comment-text-box-back").style.height="70px";
	document.getElementById("article-comment-text-box").style.height="38px";
	document.getElementById("comment-btn-back").style.display="none";
}

//*************************回复用按钮*************************
function comment_btn_on(btn){
	//document.getElementById(id)
	btn.style.color="#E8F5FD";
	//document.getElementById(id)
	btn.style.background="#1292C0";
}
function comment_btn_out(btn){
	//document.getElementById(id)
	btn.style.color="#1292C0";
	//document.getElementById(id)
	btn.style.background="inherit";
}

//***********************发送********************************




















