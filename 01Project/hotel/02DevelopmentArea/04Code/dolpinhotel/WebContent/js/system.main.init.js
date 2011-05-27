//jquery.pnotify.min.js插件的自定义扩展，实现将后台返回的response消息数据显示在前台(右下角显示)
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
var opts = {
	pnotify_addclass: "stack-bottomright",
	pnotify_stack: stack_bottomright
};

$.blockUI.defaults.message='<h1>请稍候...</h1>';
$.blockUI.defaults.fadeOut = 200;
//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

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
	}
});