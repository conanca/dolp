//jquery.pnotify.min.js插件的自定义扩展，实现将后台返回的response消息数据显示在前台(右下角显示)
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
var opts = {
	pnotify_addclass: "stack-bottomright",
	pnotify_stack: stack_bottomright
};

$.blockUI.defaults.fadeOut = 500;
$.blockUI.defaults.message = '<img src="images/commons/busy.gif" />';
//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

// ajax请求失败时报错
$(document).ajaxError(function(e,xhr,opt){
	$.addMessageStr(null,null,"Error requesting " + opt.url + ": " + xhr.status + " " + xhr.statusText);
});

//覆盖jqGrid的全局参数，以设置默认值
$.extend($.jgrid.defaults, {
	rowNum:10,
   	rowList:[10,20,50],
   	autowidth: true,
   	height: "100%",
	datatype: "json",
	viewrecords: true,
	loadComplete: function(){
		$.addMessage($(this).getGridParam('userData').systemMessage);
	},
	loadError: function(xhr,status,error){
		$.addMessageStr(null,null,"Error loading grid "+$(this).attr("id")+":"+status+"{"+error+"}");
	}
});

//用户关闭浏览器时，自动登出
$(window).unload(function(){
	$.dolpPost("logout");
});

//定义用到的全局变量
//------------------设为同步模式------------------
$.ajaxSetup({ async: false});
var genders = $.getSysEmnuItem("gender");
var genders1 = $.swapJSON(genders);
$.ajaxSetup({ async: true});
//------------------设回异步模式------------------