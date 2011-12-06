//jquery.pnotify.min.js插件的自定义扩展，实现将后台返回的response消息数据显示在前台(右下角显示)
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
var opts = {
	pnotify_delay: 5000,
	pnotify_addclass: "stack-bottomright",
	pnotify_stack: stack_bottomright
};

// 设置遮罩
$.blockUI.defaults.fadeOut = 500;
$.blockUI.defaults.message = '<img src="images/commons/busy.gif" />';
//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

// 设置ajax请求的超时时限
$.ajaxSetup({timeout:10000});

// 设置ajax请求失败时报错
$(document).ajaxError(function(e,xhr,opt){
	$.unblockUI();
	$.addMessageStr(null,null,xhr.statusText + "(" + xhr.status + ") requesting " + opt.url);
});

// 覆盖jqGrid的全局参数，以设置默认值
$.extend($.jgrid.defaults, {
	rowNum:10,
   	rowList:[10,20,50],
   	autowidth: true,
   	height: "100%",
	datatype: "json",
	viewrecords: true,
	loadComplete: function(){
		$.addMessage($(this).getGridParam('userData').systemMessage);
	}
});

// TODO 暂时将jqgrid的请求都设为post方式，以解决查询中文乱码问题
$.jgrid.ajaxOptions.type = 'post';

// 用户关闭浏览器或重定向到其他页面时，自动登出
$(window).unload(function(){
	$.dolpGet("logout");
	window.location = "index.html";
});

// 定义用到的全局变量
var genders;
var genders1;
$.getSysEmnuItem("gender",function(returnData){
	genders = returnData;
	genders1 = $.swapJSON(genders);
});