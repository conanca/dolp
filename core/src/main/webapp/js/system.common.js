// zTree异步获取到数据后，在添加到 zTree 之前利用此方法进行数据预处理，以显示系统消息
function ajaxDataFilter(treeId, parentNode, response) {
	if (response) {
		if(response.notices){
			$.showNotices(response.notices);
		}
		if(response.logic){
			return response.logic;
		}else{
			return null;
		}
	}
};

$.extend({
	//显示系统通知的函数(通知对象notices)
	showNotices : function(notices) {
		if(!notices){
			return;
		}
		$.each(notices, function(k,notic) {
			opts.pnotify_type = notic.type;
			opts.pnotify_title = notic.title;
			opts.pnotify_text = notic.text;
			$.pnotify(opts);
		});
	},
	
	// 显示通知
	showNotice : function(text){
		if (text) {
			opts.pnotify_title = "通知";
			opts.pnotify_type = "notice";
			opts.pnotify_text = text;
			$.pnotify(opts);
		}
	},
	// 显示信息
	showInfo : function(text){
		if (text) {
			opts.pnotify_title = "消息";
			opts.pnotify_type = "info";
			opts.pnotify_text = text;
			$.pnotify(opts);
		}
	},
	// 显示错误
	showError : function(text){
		if (text) {
			opts.pnotify_title = "错误";
			opts.pnotify_type = "error";
			opts.pnotify_text = text;
			$.pnotify(opts);
		}
	},

	//根据系统枚举名称，获得它所有的枚举值
	getSysEmnuItem: function(SysEnumName, afterRequest) {
		var url = 'getSysEnumItemMap/'+SysEnumName;
		var Items = null;
		$.getJSON(url,function(response){
			if(response){
				if(response.notices){
					$.showNotices(response.notices);
				}
				if(response.logic){
					Items = response.logic;
				}
				if($.isFunction(afterRequest)){
					afterRequest(Items);
				}
			}else{
				$.showError("Error requesting " + url + ": no response content");
			}
		});
		return Items;
    },
	//$.getJSON的扩展函数，封装了自定义的response数据的返回和系统消息的显示
	dolpGet: function(url, data, afterRequest) {
		var returnData = null;
		$.getJSON(url,data,function(response){
			if(response){
				if(response.notices){
					$.showNotices(response.notices);
				}
				if(response.logic){
					returnData = response.logic;
				}
				if($.isFunction(afterRequest)){
					afterRequest(returnData);
				}
			}else{
				$.showError("Error requesting " + url + ": no response content");
			}
		});
		return returnData;
	},
    //$.post的扩展函数，封装了自定义的response数据的返回和系统消息的显示;ajax请求时显示阴影遮罩
	dolpPost : function(url, data, afterRequest){
		var returnData = null;
		$.post(url,data,function(response){
			$.unblockUI();
			if(response){
				if(response.notices){
					$.showNotices(response.notices);
				}
				if(response.logic){
					returnData = response.logic;
				}
				if($.isFunction(afterRequest)){
					afterRequest(returnData);
				}
			}else{
				$.showError("Error requesting " + url + ": no response content");
			}
		},"json");
		return returnData;
	},
	//为JSON格式的map数据做键值互换,并增加一个空字符串的项
    swapJSON: function(json) {
        var o = {'':''};
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
				closeAfterEdit: true,
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
					if($.parseJSON(xhr.responseText)){
						$.showNotices($.parseJSON(xhr.responseText).notices);
					}
					return [true];
				}
			},
			{
				closeAfterAdd: true,
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
					if($.parseJSON(xhr.responseText)){
						$.showNotices($.parseJSON(xhr.responseText).notices);
					}
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
					if($.parseJSON(xhr.responseText)){
						$.showNotices($.parseJSON(xhr.responseText).notices);
					}
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
	$.fn.addExport2ExcelBtn = function(pager) {
		$(this).jqGrid('navButtonAdd',pager,{caption:"",title:"导出",buttonicon:"ui-icon-arrowthickstop-1-s",
			onClickButton:function(){
				var colNames = $(this).getGridParam("colNames");
				var rowDatas = $.toJSON($(this).getRowData());
				$("#GridExportFormColNames").val(colNames);
				$("#GridExportFormRowDatas").val(rowDatas);
				$("#GridExportForm").submit();
			}
		});
	};
	//为grid添加自定义按钮——查询栏开关
	$.fn.addSearchToolbar = function(pager) {
		$(this).jqGrid('navButtonAdd',pager,{caption:"",title:"查询栏开关", buttonicon :'ui-icon-search', onClickButton:function(){
				if(!$(this)[0].toggleToolbar){
					$(this).jqGrid('filterToolbar',{searchOnEnter:false});
				}else{
					$(this)[0].toggleToolbar();
				}
			}
		});
	};
	
	// 上传文件
	$.fn.dolpUpload = function(url,data,afterRequest){
		$.blockUI();
		$(this).upload(url,data,function(response) {
			$.unblockUI();
			if(response){
				if(response.notices){
					$.showNotices(response.notices);
				}
				if($.isFunction(afterRequest)){
					afterRequest(response.logic);
				}
			}else{
				$.showError("Error requesting " + url + ": no response content");
			}
	    }, "json");
	};
})(jQuery);