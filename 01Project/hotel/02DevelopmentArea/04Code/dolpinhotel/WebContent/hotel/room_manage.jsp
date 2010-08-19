<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();

	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes.do";
	var allRoomTypes;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url3,function(response){
		allRoomTypes = response;
	});
	
	jQuery("#roomList").jqGrid({
	   	url:'dolpinhotel/setup/room/getJqgridData.do',
		datatype: "json",
	   	colNames:['id','房间号', '房间类型','已入住'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:100},
	   		{name:'roomTypeId',index:'roomTypeId', width:100,formatter:'select', editoptions:{value:allRoomTypes}},
	   		{name:'isOccupancy',index:'isOccupancy', width:100,formatter:'select', editoptions:{value:"0:否;1:是"}},
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	jsonReader:{
	   		repeatitems: false
        },
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
	jQuery("#roomList").jqGrid('hideCol',['id']);//隐藏id列
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
	
	$.each(allRoomTypes,function(value,text) {
		$("#roomTypeSelect").append(new Option(text,value));
		$("#room_manage_roomTypeId").append(new Option(text,value));
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

var timeoutHnd;
var flAuto = false;
function doSearch(ev)
{
	if(!flAuto){
		return; // var elem = ev.target||ev.srcElement;
	}
	if(timeoutHnd){
		clearTimeout(timeoutHnd);
	}
	timeoutHnd = setTimeout(gridReload,500);
}
function gridReload(){
	var number = jQuery("#room_manage_number").val();
	var isOccupancy = jQuery("#room_manage_isOccupancy").val();
	var roomTypeId = jQuery("#room_manage_roomTypeId").val();
	jQuery("#roomList").jqGrid('setGridParam',{url:"dolpinhotel/setup/room/getJqgridData.do?number="+number+"&isOccupancy="+isOccupancy+"&roomTypeId="+roomTypeId,page:1}).trigger("reloadGrid");
}
function enableAutosubmit(state){
	flAuto = state;
	jQuery("#room_manage_search_btn").attr("disabled",state);
}
</script>

<table>
	<tr>
		<td>
			房间号：
		</td>
		<td>
			<input type="text" name="number" id="room_manage_number" onkeydown="doSearch(arguments[0]||event)"/>
		</td>
		<td>
			已入住：
		</td>
		<td>
			<select name="isOccupancy" id="room_manage_isOccupancy" onchange="doSearch(arguments[0]||event)">
				<option value="-1" selected></option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
		<td>
			房间类型：
		</td>
		<td>
			<select name="roomTypeId" id="room_manage_roomTypeId" onchange="doSearch(arguments[0]||event)">
				<option value="-1" selected></option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="right">
			<input type="button" id="room_manage_search_btn" value="查询" onclick="gridReload()"/>
			自动查询:
			<input type="checkbox" id="room_manage_autosearch" onclick="enableAutosubmit(this.checked)">
		</td>
	</tr>
</table>

<table id="roomList"></table>
<div id="roomPager"></div>

<div id="roomInfo" title="房间信息">
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
				<select name="isOccupancy">
					<option value="0" selected>否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				房间类型：
			</td>
			<td colspan="3">
				<select name="roomTypeId" id="roomTypeSelect">
				</select>
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
