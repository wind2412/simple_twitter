"use strict";
/*
    ***********************关闭*******************************
*/
function exit_article(){
	
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
function follow_btn_on(){
	document.getElementById("user-action-following").style.background="dodgerblue";
}
function follow_btn_out(){
	document.getElementById("user-action-following").style.background="rgba(170,184,194,1.00)";
}
//************************已关注按钮***************************
function unfollow(){
	document.getElementById("user-action-followed").style.display="none";
	document.getElementById("user-action-following").style.display="block";
}
function followed_btn_on(){
	document.getElementById("user-action-followed").style.background="#C50101";
	document.getElementById("user-action-followed").innerHTML="取消关注";
}
function followed_btn_out(){
	document.getElementById("user-action-followed").style.background="dodgerblue";
	document.getElementById("user-action-followed").innerHTML="已关注";
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
function action_btn_t_on(){
	document.getElementById("a-action-t").style.color="#1FA3DB";
}
function action_btn_t_out(){
	document.getElementById("a-action-t").style.color="rgba(170,184,194,1.00)";
}
//**************************回复按钮**************************
function action_btn_r_on(){
	document.getElementById("a-action-r").style.color="#40DB48";
}
function action_btn_r_out(){
	document.getElementById("a-action-r").style.color="rgba(170,184,194,1.00)";
}
//**************************点赞按钮**************************
function action_btn_v_on(){
	document.getElementById("a-action-v").style.color="#EB2462";
}
function action_btn_v_out(){
	document.getElementById("a-action-v").style.color="rgba(170,184,194,1.00)";
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
function coment_write(){
	document.getElementById("comenting").style.height="292px";
	document.getElementById("article-coment-text-box-back").style.height="192px";
	document.getElementById("article-coment-text-box").style.height="168px";
	document.getElementById("coment-btn-back").style.display="block";
}
function coment_text_box_blur(){
	document.getElementById("comenting").style.height="102px";
	document.getElementById("article-coment-text-box-back").style.height="70px";
	document.getElementById("article-coment-text-box").style.height="38px";
	document.getElementById("coment-btn-back").style.display="none";
}

//*************************回复用按钮*************************
function coment_btn_on(id){
	document.getElementById(id).style.color="#E8F5FD";
	document.getElementById(id).style.background="#1292C0";
}
function coment_btn_out(id){
	document.getElementById(id).style.color="#1292C0";
	document.getElementById(id).style.background="inherit";
}




















