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
	getSysEmnuItem: function(SysEnumName, afterRequest) {
		var url = 'getSysEnumItemMap/'+SysEnumName;
		var Items = {};
		$.getJSON(url,function(response){
			if(response){
				if(response.systemMessage){
					$.addMessage(response.systemMessage);
				}
				if(response.returnData){
					Items = response.returnData;
				}
				if($.isFunction(afterRequest)){
					afterRequest(Items);
				}
			}else{
				$.addMessageStr(null,null,"Error requesting " + url + ": no response content");
			}
		});
		return Items;
    },
	//$.getJSON的扩展函数，封装了自定义的response数据的返回和系统消息的显示
	dolpGet: function(url, data, afterRequest) {
		var returnData = {};
		$.getJSON(url,data,function(response){
			if(response){
				if(response.systemMessage){
					$.addMessage(response.systemMessage);
				}
				if(response.returnData){
					returnData = response.returnData;
				}
				if($.isFunction(afterRequest)){
					afterRequest(returnData);
				}
			}else{
				$.addMessageStr(null,null,"Error requesting " + url + ": no response content");
			}
		});
		return returnData;
	},
    //$.post的扩展函数，封装了自定义的response数据的返回和系统消息的显示;ajax请求时显示阴影遮罩
	dolpPost : function(url, data, afterRequest){
		var returnData = {};
		$.blockUI();
		$.post(url,data,function(response){
			$.unblockUI();
			if(response){
				if(response.systemMessage){
					$.addMessage(response.systemMessage);
				}
				if(response.returnData){
					returnData = response.returnData;
				}
				if($.isFunction(afterRequest)){
					afterRequest(returnData);
				}
			}else{
				$.addMessageStr(null,null,"Error requesting " + url + ": no response content");
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
		$.getSysEmnuItem(SysEnumName,function(returnData){
			var SysEnumItems = returnData;
			$(selectorid).addItems(SysEnumItems);
		});
	};
	//增强jqgrid的form edit 设置:1.增加系统消息的显示;2.处理删除时的id(将id设置0，增加idArr);3.执行指定的方法;4.ajax请求时显示阴影遮罩
	$.fn.setJqGridCUD = function(pager,para,afterSubmitTodo) {
		$(this).navGrid(pager,para,
			{
				beforeSubmit : function(postdata, formid) {
					$.blockUI();
					return[true]; 
				},
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.unblockUI();
					if(afterSubmitTodo && afterSubmitTodo.edit){
						afterSubmitTodo.edit();
					}
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				}
			},
			{
				beforeSubmit : function(postdata, formid) { 
					$.blockUI();
					return[true]; 
				},
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.unblockUI();
					if(afterSubmitTodo && afterSubmitTodo.add){
						afterSubmitTodo.add();
					}
					$(this.selector).resetSelection();
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				}
			},
			{
				beforeSubmit : function(postdata, formid) { 
					$.blockUI();
					return[true]; 
				},
				reloadAfterSubmit:true,
				afterSubmit: function(xhr, postdata) {
					$.unblockUI();
					if(afterSubmitTodo && afterSubmitTodo.del){
						afterSubmitTodo.del();
					}
					$.addMessage($.parseJSON(xhr.responseText).systemMessage);
					return [true];
				},
				serializeDelData : function(postdata) {
					var id = postdata['id'];
					if(id){
						if(parseInt(id) != id){
							postdata['ids'] = postdata['id'];
							postdata['id'] = 0;
						}else{
							postdata['ids'] = postdata['id'];
						}
					}
					return postdata;
				}
			},
			{},{}
		);
	};
	//为grid添加自定义按钮——导出Excel
	$.fn.export2Excel = function(pager) {
		$(this).navButtonAdd(pager,{caption:"导出",buttonicon:"ui-icon-arrowthickstop-1-s",
			onClickButton:function(){
				var colNames = $(this).getGridParam("colNames");
				var rowDatas = $.toJSON($(this).getRowData());
				$("#GridExportFormColNames").val(colNames);
				$("#GridExportFormRowDatas").val(rowDatas);
				$("#GridExportForm").submit();
			}
		});
	};
	// 上传文件
	$.fn.dolpUpload = function(url,data,afterRequest){
		$.blockUI();
		$(this).upload(url,data,function(response) {
			$.unblockUI();
			if(response){
				if(response.systemMessage){
					$.addMessage(response.systemMessage);
				}
				if($.isFunction(afterRequest)){
					afterRequest(response.returnData);
				}
			}else{
				$.addMessageStr(null,null,"Error requesting " + url + ": no response content");
			}
	    }, "json");
	};
})(jQuery);