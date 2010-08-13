<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#roomTypeList").jqGrid({
	   	url:'dolpinhotel/setup/roomtype/getGridData.do',
		datatype: "json",
	   	colNames:['id','房间类型', '价格','描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:100},
	   		{name:'price',index:'price', width:100},
	   		{name:'description',index:'description', width:300}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#roomTypePager',
	   	sortname: 'price',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "dolpinhotel/setup/roomtype/deleteRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption: "房间类型列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roomTypeList").jqGrid('navGrid','#roomTypePager',{edit:false,add:false,del:false,search:false});
	jQuery("#roomTypeList").jqGrid('hideCol',['id']);//隐藏id列
	jQuery("#roomTypeList").jqGrid('navButtonAdd','#roomTypePager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#roomType").dialog( "open" );
		}
	});
	jQuery("#roomTypeList").jqGrid('navButtonAdd','#roomTypePager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = jQuery("#roomTypeList").jqGrid('getGridParam','selrow');
			if (id) {
				jQuery("#roomTypeList").jqGrid('GridToForm',id,"#roomTypeForm");
				$("#roomType").dialog( "open" );
			} else {
				alert("请选择要编辑的记录");
			}
		}
	});
	jQuery("#roomTypeList").jqGrid('navButtonAdd','#roomTypePager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var gr = jQuery("#roomTypeList").jqGrid('getGridParam','selarrrow');
			if( gr != null ){
				jQuery("#roomTypeList").jqGrid('delGridRow',gr,{reloadAfterSubmit:true});
			}
			else{
				alert("请选择要删除的记录");
			}
		}
	});
	
	//初始化用户信息界面
	$("#roomType").dialog({width: 580, hide: 'slide' , autoOpen: false,close: function(event, ui) {
			$("#roomTypeId").attr("value",'');	//清空隐藏域的值
			$('#roomTypeForm')[0].reset();	//清空表单的值
		}
	});
	
	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'dolpinhotel/setup/roomtype/save.do',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#roomTypeForm').submit(function() {
		$(this).ajaxSubmit(options);
		//关闭用户信息界面
		$("#roomType").dialog( "close" );
		//刷新表格
		$('#roomTypeList').trigger("reloadGrid");
		return false;
	});
	//设置按钮图标——————未起作用
	$("#roomTypecancel").button( "option", "icons", {primary:'ui-icon-cancel',secondary:'ui-icon-cancel'} );
	$("#roomTypecancel").click(function() {
		$("#roomType").dialog( "close" );
	});
});

//提交前
function showRequest(formData, jqForm, options) {
	
}
//提交后获得Respons后
function showResponse(responseText, statusText, xhr, $form)  {
	alert('保存成功');
}
</script>
<table id="roomTypeList"></table>
<div id="roomTypePager"></div>

<div id="roomType" title="房间类型信息">
	<form id="roomTypeForm">
	<table>
		<tr>
			<td>
				房间类型名称：
			</td>
			<td>
				<input type="hidden" name="id" id="roomTypeId"/>
				<input type="text" name="name"/>
			</td>
			<td>
				价格（元/天）：
			</td>
			<td>
				<input type="text" name="price"/>
			</td>
		</tr>
		<tr>
			<td>
				房间类型描述：
			</td>
			<td colspan="3">
				<textarea name="description"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存"/>
				<input type="reset" value="重置"/>
				<input id="roomTypecancel" type="button" value="取消"/>
			</td>
		</tr>
	</table>
	</form>
</div>
