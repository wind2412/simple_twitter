function create_an_article(){
	var li = document.createElement("li");
	li.className = "stream-item";
	var div_text = document.createElement("div");
	div_text.className = "text";
	var div_ao = document.createElement("div");
	div_ao.className = "article-others";
	div_ao.role = "others";		//???
	var div_ai = document.createElement("div");
	div_ai.className = "article-inner";
	var div_a = document.createElement("div");
	div_a.className = "article";
	div_a.style.marginTop = "1px";
	var div_al = document.createElement("div");
	div_a.className = "article-left";
	var img_hp = document.createElement("img");
	div_a.appendChild(img_hp);	//append
	var div_ar = document.createElement("div");
	div_ar.className = "article-right";
	var div_arh = document.createElement("div");
	div_arh.className = "article-right-header";
	var a_1 = document.createElement("a");
	a_1.src="";		//????
	a_1.style.float = "left";
	a_1.style.cursor = "pointer";
	var div_aru = document.createElement("div");
	div_aru.className = "article-right-username";
	var span_div_aru_1 = document.createElement("span");
	span_div_aru_1.style.fontFamily = "微软雅黑";
	span_div_aru_1.style.color = "dodgerblue";
	span_div_aru_1.innerHTML = "欧摩西罗伊";	//???
	
}