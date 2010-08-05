<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();

	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes.do";
	var allRoomTypes;
	$.getJSON(url3,function(response){
		alert(response);
		allRoomTypes = response;
	});
	alert(allRoomTypes);
	
	jQuery("#roomList").jqGrid({
	   	url:'dolpinhotel/setup/room/getGridData.do',
		datatype: "json",
	   	colNames:['id','房间号', '房间类型ID','房间类型','已入住'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:100},
	   		{name:'roomTypeId',index:'roomTypeId', width:0,
		   		formatter:function(cellvalue, options, rowObject){
					
			}},
	   		{name:'roomType',index:'roomType', width:100},
	   		{name:'isOccupancy',index:'isOccupancy', width:100,
				formatter:function(cellvalue, options, rowObject){
		             if(cellvalue==0){
		              temp = "否";
		             } else {
		              temp = "是";
		             }
		             return temp;
			}},
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	pager: '#roomPager',
	   	sortname: 'number',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "dolpinhotel/setup/room/deleteRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption: "房间列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roomList").jqGrid('navGrid','#roomPager',{edit:false,add:false,del:false,search:false});
	jQuery("#roomList").jqGrid('hideCol',['id','roomTypeId']);//隐藏id列,roomTypeId列
	jQuery("#roomList").jqGrid('navButtonAdd','#roomPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#roomInfo").dialog( "open" );
		}
	});
	jQuery("#roomList").jqGrid('navButtonAdd','#roomPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = jQuery("#roomList").jqGrid('getGridParam','selrow');
			if (id) {
				jQuery("#roomList").jqGrid('GridToForm',id,"#roomForm");
				$("#roomInfo").dialog( "open" );
			} else {
				alert("请选择要编辑的记录");
			}
		}
	});
	jQuery("#roomList").jqGrid('navButtonAdd','#roomPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var gr = jQuery("#roomList").jqGrid('getGridParam','selarrrow');
			if( gr != null ){
				jQuery("#roomList").jqGrid('delGridRow',gr,{reloadAfterSubmit:true});
			}
			else{
				alert("请选择要删除的记录");
			}
		}
	});
	
	//初始化用户信息界面
	$("#roomInfo").dialog({width: 580, hide: 'slide' , autoOpen: false,close: function(event, ui) {
			$("#roomId").attr("value",'');	//清空隐藏域的值
			$('#roomForm')[0].reset();	//清空表单的值
		}
	});
	
	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'dolpinhotel/setup/room/save.do',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#roomForm').submit(function() {
		$(this).ajaxSubmit(options);
		//关闭用户信息界面
		$("#roomInfo").dialog( "close" );
		//刷新表格
		$('#roomList').trigger("reloadGrid");
		return false;
	});
	//设置按钮图标——————未起作用
	$("#roomcancel").button( "option", "icons", {primary:'ui-icon-cancel',secondary:'ui-icon-cancel'} );
	$("#roomcancel").click(function() {
		$("#roomInfo").dialog( "close" );
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
<table id="roomList"></table>
<div id="roomPager"></div>

<div id="roomInfo" title="用户信息">
	<form id="roomForm">
	<table>
		<tr>
			<td>
				房间号：
			</td>
			<td>
				<input type="hidden" name="id" id="roomId"/>
				<input type="text" name="number"/>
			</td>
			<td>
				已入住：
			</td>
			<td>
				<input type="text" name="isOccupancy"/>
			</td>
		</tr>
		<tr>
			<td>
				房间类型：
			</td>
			<td colspan="3">
				<input type="text" name="roomTypeId"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存"/>
				<input type="reset" value="重置"/>
				<input id="roomcancel" type="button" value="取消"/>
			</td>
		</tr>
	</table>
	</form>
</div>
