<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();

	// 查询出所有的房间类型
	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes";
	var allRoomTypes = $.getItem(url3);
	
	$("#room_manage_roomTypeId").addItems(allRoomTypes);
	//swapJsonKV(allRoomTypes);
	var allRoomTypes1 = $.swapJSON(allRoomTypes); 
	
	jQuery("#roomList").jqGrid({
	   	url:'dolpinhotel/setup/room/getJqgridData',
		datatype: "json",
	   	colNames:['id','房间号', '房间类型','已入住'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:100, editable:true},
	   		{name:'roomTypeId',index:'roomTypeId', width:100, editable:true, edittype:'select', formatter:'select', editoptions:{value:allRoomTypes1}},
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
	    editurl: "dolpinhotel/setup/room/editRow",	//del:true
	    multiselect: true, //checkbox
	    caption: "房间列表",
	    loadComplete: function(){
			$.addMessage(jQuery("#roomList").getGridParam("userData"));
		}
	});
	//不显示jqgrid自带的查询按钮
	jQuery("#roomList").jqGrid('navGrid','#roomPager',{edit:true,add:true,del:true,search:false});
	jQuery("#roomList").jqGrid('hideCol',['id']);//隐藏id列

	//查询按钮点击事件
	$("#room_manage_search_btn").click(function () { 
		var number = jQuery("#room_manage_number").val();
		var isOccupancy = jQuery("#room_manage_isOccupancy").val();
		var roomTypeId = jQuery("#room_manage_roomTypeId").val();
		url = "dolpinhotel/setup/room/getJqgridData?number="+number+"&isOccupancy="+isOccupancy+"&roomTypeId="+roomTypeId;
		jQuery("#roomList").jqGrid('setGridParam',{url:url, page:1}).trigger("reloadGrid");
    });
});
</script>

<fieldset>
<legend>房间查询</legend>
<table>
	<tr>
		<td>
			房间号：
		</td>
		<td>
			<input type="text" name="number" id="room_manage_number"/>
		</td>
		<td>
			已入住：
		</td>
		<td>
			<select name="isOccupancy" id="room_manage_isOccupancy">
				<option value="-1" selected="selected"></option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
		<td>
			房间类型：
		</td>
		<td>
			<select name="roomTypeId" id="room_manage_roomTypeId">
				<option value="-1" selected="selected"></option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="right">
			<input type="button" id="room_manage_search_btn" value="查询" onclick="setUrl();gridReload()"/>
		</td>
	</tr>
</table>
</fieldset>

<table id="roomList"></table>
<div id="roomPager"></div>