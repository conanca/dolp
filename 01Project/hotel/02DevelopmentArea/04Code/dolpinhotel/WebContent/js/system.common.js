//使用jquery.form.js时的一个公用方法，用于提交获得Respons后显示系统消息
function showResponse(responseText, statusText, xhr, $form)  {
	$.addMessage($.parseJSON(responseText).userdata);
}

//jquery.pnotify.min.js插件的自定义扩展，实现将后台返回的response消息数据显示在前台(右下角显示)
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 15, "firstpos2": 15};
var opts = {
	pnotify_addclass: "stack-bottomright",
	pnotify_stack: stack_bottomright
};

$.extend({
	//显示系统消息的函数
	addMessage : function(userData) {
		if(!userData){
			return;
		}
		var infoMessages = userData.infoMessages;
		var warnMessages = userData.warnMessages;
		var errorMessages = userData.errorMessages;
		if (infoMessages) {
			opts.pnotify_title = "消息";
			opts.pnotify_type = "notice";
			$.each(infoMessages, function(k,infoMessage) {
				opts.pnotify_text = infoMessage;
				$.pnotify(opts);
	        });
		}
		if (warnMessages) {
			opts.pnotify_title = "警告";
			opts.pnotify_type = "error";
			opts.pnotify_error_icon = 'ui-icon-notice';
			$.each(warnMessages, function(k,warnMessage) {
				opts.pnotify_text = warnMessage;
				$.pnotify(opts);
	        });
		}
		if (errorMessages) {
			opts.pnotify_title = "错误";
			opts.pnotify_type = "error";
			$.each(errorMessages, function(k,errorMessage) {
				opts.pnotify_text = errorMessage;
				$.pnotify(opts);
	        });
		}
	},
	addMessageStr : function(infoMessage,warnMessage,errorMessage) {
		if (infoMessage) {
			opts.pnotify_title = "消息";
			opts.pnotify_type = "notice";
			opts.pnotify_text = infoMessage;
			$.pnotify(opts);
		}
		if (warnMessage) {
			opts.pnotify_title = "警告";
			opts.pnotify_text = warnMessage;
			opts.pnotify_type = "error";
			pnotify_error_icon: 'ui-icon-notice';
			$.pnotify(opts);
		}
		if (errorMessage) {
			opts.pnotify_title = "错误";
			opts.pnotify_text = errorMessage;
			opts.pnotify_type = "error";
			$.pnotify(opts);
		}
	},
	//根据url提交给后台查询Item信息，获得的是JSON格式的map数据
	getItem: function(url) {
		var Items = {};
		$.ajaxSetup({ async: false});//设为同步模式
		$.getJSON(url,function(response){
			if(response.userdata){
				$.addMessage(response.userdata);
				return;
			}
			Items = response;
		});
		return Items;
	},
	//根据系统枚举名称，获得它所有的枚举值
	getSysEmnuItem: function(SysEnumName) {
		var url = 'system/sysEnum/getSysEnumItems/'+SysEnumName;
		var Items = {};
		$.getJSON(url,function(response){
			if(response.userdata){
				$.addMessage(response.userdata);
				return;
			}
			Items = response;
		});
		return Items;
    },
	//为JSON格式的map数据做键值互换
    swapJSON: function(json) {
        var o = {};
        $.each(json, function(k, v) {
            o[v] = k;
        });
        return o;
    }
});

//getJSON的扩展函数，封装了自定义的response数据的返回和系统消息的显示
$.extend({
	myGetJSON : function(url, data){
		var returnData;
		$.getJSON(url,data,function(response){
			if(response.returnData){
				returnData = response.returnData;
			}else{
				returnData = response;
			}
			if(response.userdata){
				$.addMessage(response.userdata);
			}
		});
		return returnData;
	}
});

(function($) {
	//将表单数据封装成对象，各个控件的name为属性名，value为属性值
	$.fn.serializeObject = function()
	{
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	//为该下拉列表框添加指定的Items作为option，此处的Items为JSON格式的map数据
	$.fn.addItems = function(Items) {
		var selectorid=this.selector;
		   $.each(Items,function(text,value){
				var newopt='<option value="'+value+'">'+text+'</option>';					
				$(selectorid).append(newopt);
		});
	};
	//为该下拉列表框添加指定系统枚举值作为option
	$.fn.addSysEmnuItems = function(SysEnumName) {
		var selectorid=this.selector;
		var SysEnumItems = $.getSysEmnuItem(SysEnumName);
		$(selectorid).find('option').remove().end();
		   $.each(SysEnumItems,function(text,value){
				var newopt='<option value="'+value+'">'+text+'</option>';					
				$(selectorid).append(newopt);
		});
	};
	//设置jqgrid的改增删功能，并增加系统消息的显示
	$.fn.setJqGridCUD = function(pager,para) {
		var selectorid=this.selector;
		$(selectorid).navGrid(pager,para,
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).userdata);
					return [true];
				}
			},
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).userdata);
					return [true];
				}
			},
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).userdata);
					return [true];
				}
			},
			{},{}
		);
	};
})(jQuery);