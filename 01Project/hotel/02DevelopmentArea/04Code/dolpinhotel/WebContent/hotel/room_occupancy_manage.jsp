<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();

	var checkOutIdArr;
	var selStatus=0;

	//获取所有已入住房间的房间号-房间Id键值对
	var url3 = "dolpinhotel/setup/room/getAllRoomForSelectOption";
	var allRooms = $.myGetJSON(url3);
	
	$("#roomOccupancyList").jqGrid({
		rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    datatype: "json",
		jsonReader:{
	   		repeatitems: false
		},
		viewrecords: true,
		caption: "房间入住情况列表",
	   	url:'dolpinhotel/management/roomoccupancy/getGridData?billId=0',
	   	colNames:['id','房间号', '入住日期','预离日期','离开日期','入住天数','金额','状态','billId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0,hidden:true},
	   		{name:'roomId',index:'roomId', width:100,formatter:'select', editoptions:{value:allRooms}},
	   		{name:'enterDate',index:'enterDate', width:120, editable:true, formatter:fmtDate },
	   		{name:'expectedCheckOutDate',index:'expectedCheckOutDate',width:120, editable:true, formatter:fmtDate},
	   		{name:'leaveDate',index:'leaveDate', width:120, editable:true, formatter:fmtDate},
	   		{name:'occupancyDays',index:'occupancyDays', width:120},
	   		{name:'amount',index:'amount', width:100},
	   		{name:'status',index:'status', width:100,formatter:'select', editoptions:{value:"0:入住中;1:已离开"}},
	   		{name:'billId',index:'billId', width:0,hidden:true}
	   	],
	   	pager: '#roomOccupancyPager',
	   	sortname: 'id',
		sortorder: "desc",
		multiselect: true, //checkbox
	    loadComplete: function(){
			$.addMessage($("#roomOccupancyList").getGridParam("userData"));
		},
		onSelectRow: function(ids) {
			 if(ids == null) {
				ids=0;
				if($("#customerSubList").getGridParam('records') >0 ) {
					$("#customerSubList").setGridParam({url:"dolpinhotel/management/customer/getGridDataByRoomOccId/"+ids,page:1});
					$("#customerSubList").trigger('reloadGrid'); 
				}
			} else {
				$("#customerSubList").setGridParam({url:"dolpinhotel/management/customer/getGridDataByRoomOccId/"+ids,page:1});
				$("#customerSubList").trigger('reloadGrid');
				selStatus = $("#roomOccupancyList").getCell(ids,'status');
			}
		}
	});
	//不显示jqgrid自带的增删改查按钮
	$("#roomOccupancyList").navGrid('#roomOccupancyPager',{edit:false,add:false,del:false,search:false});
	$("#roomOccupancyList").navButtonAdd('#roomOccupancyPager',{caption:"结帐离开",buttonicon:"ui-icon-cart",position:"last",
		onClickButton:function(){
			checkOutIdArr = $("#roomOccupancyList").getGridParam('selarrrow');
			if( checkOutIdArr ){
				if(selStatus=='1'){
					$.addMessageStr(null,"该记录已结帐",null);
				}else{
					$("#room_occupancy_manage_checkOutDiv").dialog( "open" );
				}
			}
			else{
				$.addMessageStr(null,"请选择要结帐的房间",null);
			}
		}
	});

	//初始化结帐日期界面
	$("#room_occupancy_manage_checkOutDiv").dialog({width: 300, hide: 'slide' , modal: true , autoOpen: false,close: function(event, ui) {
			$("#room_occupancy_manage_checkOutId").attr("value",'');	//清空隐藏域的值
			$("#room_occupancy_manage_checkOutLeaveDate").attr("value",'');	//清空离开日期的值
		}
	});
	
	$("#room_occupancy_manage_checkOutBtn").click(function() {
		var leaveDate = $("#room_occupancy_manage_checkOutLeaveDate").val();
		$.post("dolpinhotel/management/roomoccupancy/checkOut", { checkOutIdArr: checkOutIdArr, leaveDate: leaveDate},
			function(data) {
				$.addMessageStr("结帐成功",null,null);
				$("#room_occupancy_manage_checkOutLeaveDate").attr("value",'');	//清空离开日期的值
				$("#room_occupancy_manage_checkOutDiv").dialog( "close" );
				$('#roomOccupancyList').trigger("reloadGrid");	//刷新grid
			}
		);
	});
	
	$("#room_occupancy_manage_checkOutCancelBtn").click(function() {
		$("#room_occupancy_manage_checkOutLeaveDate").attr("value",'');	//清空离开日期的值
		$("#room_occupancy_manage_checkOutDiv").dialog( "close" );
	});

	$("#customerSubList").jqGrid({
		rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	datatype: "json",
		jsonReader:{
	   		repeatitems: false
		},
		viewrecords: true,
		caption: "所选房间顾客列表",
	   	url:'dolpinhotel/management/customer/getGridDataByRoomOccId',
	   	colNames:['id','序号', '姓名','性别','证件类型','证件号','籍贯地址','roomOccupancyId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0,hidden:true},
	   		{name:'no',index:'no', width:80},
	   		{name:'name',index:'name', width:80},
	   		{name:'gender',index:'gender', width:80},
	   		{name:'certificateType',index:'certificateType', width:120},		
	   		{name:'credentialNumber',index:'credentialNumber', width:150},		
	   		{name:'address',index:'address', width:300},
	   		{name:'roomOccupancyId',index:'roomOccupancyId', width:0,hidden:true}
	   	],
	   	pager: '#customerSubPager',
	   	sortname: 'no',
		sortorder: "asc",
		multiselect: false, //checkbox
	    loadComplete: function(){
			$.addMessage($("#customerSubList").getGridParam("userData"));
		}
	});
	//不显示jqgrid自带的增删改查按钮
	$("#customerSubList").navGrid('#customerSubPager',{edit:false,add:false,del:false,search:false});

	//查询按钮点击事件
	$("#room_occupancy_manage_search_btn").click(function () { 
		var number = $("#room_occupancy_manage_number").val();
		var enterDateFrom = $("#room_occupancy_manage_enterDateFrom").val();
		var enterDateTo = $("#room_occupancy_manage_enterDateTo").val();
		var expectedCheckOutDateFrom = $("#room_occupancy_manage_expectedCheckOutDateFrom").val();
		var expectedCheckOutDateTo = $("#room_occupancy_manage_expectedCheckOutDateTo").val();
		var leaveDateFrom = $("#room_occupancy_manage_leaveDateFrom").val();
		var leaveDateTo = $("#room_occupancy_manage_leaveDateTo").val();
		var occupancyDays = $("#room_occupancy_manage_occupancyDays").val();
		var status = $("#room_occupancy_manage_status").val();
		var url = "dolpinhotel/management/roomoccupancy/getGridData?number="+number+"&enterDateFrom="+enterDateFrom+"&enterDateTo="+enterDateTo
		+"&expectedCheckOutDateFrom="+expectedCheckOutDateFrom+"&expectedCheckOutDateTo="+expectedCheckOutDateTo+"&leaveDateFrom="+leaveDateFrom
		+"&leaveDateTo="+leaveDateTo+"&occupancyDays="+occupancyDays+"&status="+status;
		$("#roomOccupancyList").setGridParam({url:url,page:1}).trigger("reloadGrid");
    });
});

function fmtDate(value){
	if(value){
		return value.substr(0,10);
	}else{
		return '';
	}
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
			<input type="text" id="room_occupancy_manage_number" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
		</td>
		<td>
			入住日期：
		</td>
		<td>
			<input type="text" id="room_occupancy_manage_enterDateFrom" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
			-
			<input type="text" id="room_occupancy_manage_enterDateTo" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
		</td>
	</tr>
	<tr>
		<td>
			状态：
		</td>
		<td>
			<select id="room_occupancy_manage_status" onchange="doSearch(arguments[0]||event)">
				<option value="-1"></option>
				<option value="0" selected="selected">入住中</option>
				<option value="1">已离开</option>
			</select>
		</td>
		<td>
			离开日期：
		</td>
		<td>
			<input type="text" id="room_occupancy_manage_leaveDateFrom" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
			-
			<input type="text" id="room_occupancy_manage_leaveDateTo" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
		</td>
	</tr>
	<tr>
		<td>
			入住天数：
		</td>
		<td>
			<input type="text" id="room_occupancy_manage_occupancyDays" onkeydown="doSearch(arguments[0]||event)"/>
		</td>
		<td>
			预离日期：
		</td>
		<td>
			<input type="text" id="room_occupancy_manage_expectedCheckOutDateFrom" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
			-
			<input type="text" id="room_occupancy_manage_expectedCheckOutDateTo" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="right">
			<input type="button" id="room_occupancy_manage_search_btn" value="查询"/>
		</td>
	</tr>
</table>
</fieldset>

<table id="roomOccupancyList"></table>
<div id="roomOccupancyPager"></div>
<br/>
<table id="customerSubList"></table>
<div id="customerSubPager"></div>

<div id="room_occupancy_manage_checkOutDiv"  title="请选择离开日期">
	<table>
		<tr>
			<td>
				离开日期:
				<input type="text" id="room_occupancy_manage_checkOutLeaveDate" class="datepicker"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" id="room_occupancy_manage_checkOutBtn" value="确定"/>
				<input type="button" id="room_occupancy_manage_checkOutCancelBtn" value="取消"/>
			</td>
		</tr>
	</table>
</div>