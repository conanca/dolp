//使用jquery.form.js时的一个公用方法，用于提交获得Respons后显示系统消息
function showResponse(responseText, statusText, xhr, $form)  {
	$.addMessage($.parseJSON(responseText).systemMessage);
}

// zTree异步获取到数据后，在添加到 zTree 之前利用此方法进行数据预处理，以显示系统消息
function ajaxDataFilter(treeId, parentNode, response) {
	if (response) {
		if(response.systemMessage){
			$.addMessage(response.systemMessage);
		}
		if(response.returnData){
			return response.returnData;
		}else{
			return null;
		}
	}
};

$.extend({
	//显示系统消息的函数(消息对象systemMessage)
	addMessage : function(systemMessage) {
		if(!systemMessage){
			return;
		}
		var infoMessages = systemMessage.infoMessages;
		var warnMessages = systemMessage.warnMessages;
		var errorMessages = systemMessage.errorMessages;
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
	//显示系统消息的函数(消息内容infoMessage,warnMessage,errorMessage)
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
	//根据系统枚举名称，获得它所有的枚举值
	getSysEmnuItem: function(SysEnumName) {
		var url = 'system/sysEnum/getSysEnumItemMap/'+SysEnumName;
		var Items = {};
		$.getJSON(url,function(response){
			if(response.systemMessage){
				$.addMessage(response.systemMessage);
			}
			if(response.returnData){
				Items = response.returnData;
			}
		});
		return Items;
    },
	//根据url提交给后台查询Item信息，获得的是JSON格式的map数据
	dolpGet: function(url) {
		var returnData = {};
		$.getJSON(url,function(response){
			if(response.systemMessage){
				$.addMessage(response.systemMessage);
			}
			if(response.returnData){
				returnData = response.returnData;
			}
		});
		return returnData;
	},
    //post的扩展函数，封装了自定义的response数据的返回和系统消息的显示
	dolpPost : function(url, data){
		var returnData = {};
		$.post(url,data,function(response){
			if(response.systemMessage){
				$.addMessage(response.systemMessage);
			}
			if(response.returnData){
				returnData = response.returnData;
			}
		},"json");
		return returnData;
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
		//------------------设为同步模式------------------
		$.ajaxSetup({ async: false});
		var SysEnumItems = $.getSysEmnuItem(SysEnumName);
		$(selectorid).find('option').remove().end();
		   $.each(SysEnumItems,function(text,value){
				var newopt='<option value="'+value+'">'+text+'</option>';					
				$(selectorid).append(newopt);
		});
		$.ajaxSetup({ async: true});
		//------------------设回异步模式------------------
	};
	//设置jqgrid的改增删功能，并增加系统消息的显示
	$.fn.setJqGridCUD = function(pager,para) {
		var selectorid=this.selector;
		$(selectorid).navGrid(pager,para,
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				}
			},
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				}
			},
			{
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				}
			},
			{},{}
		);
	};
	//为grid添加自定义按钮——导出Excel
	$.fn.export2Excel = function(pager) {
		var selectorid=this.selector;
		$(selectorid).navButtonAdd(pager,{caption:"导出",buttonicon:"ui-icon-arrowthickstop-1-s",
			onClickButton:function(){
				var colNames = $(selectorid).getGridParam("colNames");
				var rowDatas = $.toJSON($(selectorid).getRowData());
				$("#GridExportFormColNames").val(colNames);
				$("#GridExportFormRowDatas").val(rowDatas);
				$("#GridExportForm").submit();
			}
		});
	};
})(jQuery);