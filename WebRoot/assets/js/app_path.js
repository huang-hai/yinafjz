//js获取项目根路径，如： http://localhost:8083/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
//    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht);
}

/*获取到Url里面的参数*/
function getUrlParam(name) {
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if (r != null) return unescape(r[2]); return null;
}

//或取用户id
function getUser(){
	var userId = 0;
	$.ajax({
		type: "GET",
		async: false,
		//url: "/user/getUser",
		url: getRootPath() + "/user/getUser",
		dataType: "json",
		success: function(res){
			if(res.code == 200){
				userId = res.obj.userId;
			}
		}
	});
	
	return userId;
}

/**
 * @param type GET 或 POST
 * @param url 
 * @param data 参数
 * @param fn 回调方法
 * @param params 回调方法中需要用到的参数
 */
function jsAjax(type,url,data,fn,params,isAsync){
	var xhr;
	if(window.XMLHttpRequest){
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");//IE6浏览器创建ajax对象
	}
	url = getRootPath() + url;
	xhr.open(type,url,isAsync);
	xhr.onreadystatechange = function() {
		// readyState == 4说明请求已完成
		if (xhr.readyState == 4 && xhr.status == 200 || xhr.status == 304) { 
			// 从服务器获得数据 
			fn(eval("("+xhr.responseText+")"),params);  
		}
	};
	if("POST" == type){
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.send(data);
	} else {
		xhr.send();
	}
}