<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();

	// 查询出所有的房间类型
	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes.do";
	var allRoomTypes;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url3,function(response){
		allRoomTypes = response;
	});
	$.each(allRoomTypes,function(value,text) {
		$("#room_manage_roomTypeId").append(new Option(text,value));
	});
	
	jQuery("#roomList").jqGrid({
	   	url:'dolpinhotel/setup/room/getJqgridData.do',
		datatype: "json",
	   	colNames:['id','房间号', '房间类型','已入住'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:100, editable:true},
	   		{name:'roomTypeId',index:'roomTypeId', width:100, editable:true, edittype:'select', formatter:'select', editoptions:{value:allRoomTypes}},
	   		{name:'isOccupancy',index:'isOccupancy', width:100, editable:true, edittype:'select', formatter:'select', editoptions:{value:"0:否;1:是"}},
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
	    editurl: "dolpinhotel/setup/room/editRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption: "房间列表"
	});
	//不显示jqgrid自带的查询按钮
	jQuery("#roomList").jqGrid('navGrid','#roomPager',{edit:true,add:true,del:true,search:false});
	jQuery("#roomList").jqGrid('hideCol',['id']);//隐藏id列
});

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

<fieldset>
<legend>房间查询</legend>
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
</fieldset>

<table id="roomList"></table>
<div id="roomPager"></div>