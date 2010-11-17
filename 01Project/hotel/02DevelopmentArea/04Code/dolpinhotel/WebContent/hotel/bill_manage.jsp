<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/jquery.PrintArea.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();
	
	//获取所有已入住房间的房间号-房间Id键值对
	var url3 = "dolpinhotel/setup/room/getAllRoomForSelectOption";
	var allRooms;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url3,function(response){
		allRooms = response;
	});
	
	jQuery("#billList").jqGrid({
	   	url:'dolpinhotel/management/bill/getGridData',
		datatype: "json",
	   	colNames:['id','账单号', '金额','日期'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:100, editable:true},
	   		{name:'amount',index:'amount', width:100, editable:true},
	   		{name:'date',index:'date', width:100, editable:true, sorttype:'date', formatter:fmtDate},
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#billPager',
	   	sortname: 'number',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "dolpinhotel/management/bill/editRow",	//del:true
	    multiselect: false, //checkbox
	    caption: "账单列表",
	    loadComplete: function(){
			$.addMessage(jQuery("#billList").getGridParam("userData"));
		},
		onSelectRow: function(ids) {
			if(ids == null) {
				ids=0;
				if($("#billSubList").jqGrid('getGridParam','records') >0 ) {
					$("#billSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/roomoccupancy/getGridData?billId="+ids,page:1});
					$("#billSubList").trigger('reloadGrid');
				}
			} else {
				$("#billSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/roomoccupancy/getGridData?billId="+ids,page:1});
				$("#billSubList").trigger('reloadGrid');
			}
			var no = $("#billList").jqGrid('getCell',ids,'number');
			var amount = $("#billList").jqGrid('getCell',ids,'amount');
			var date = $("#billList").jqGrid('getCell',ids,'date');
			$("#bill_manage_print_number").val(no);
			$("#bill_manage_print_amount").val(amount);
			$("#bill_manage_print_date").val(date);
		}
	});
	//不显示jqgrid自带的查询按钮
	jQuery("#billList").jqGrid('navGrid','#billPager',{edit:true,add:false,del:true,search:false});
	jQuery("#billList").jqGrid('hideCol',['id']);//隐藏id列
	jQuery("#billList").jqGrid('navButtonAdd','#billPager',{caption:"打印",buttonicon:"ui-icon-print",
		onClickButton:function(){
			$("#billInfoPrint1").show();
			$("#billInfoPrint2").show();
			$("#billPrintDiv").printArea();
			$("#billInfoPrint1").hide();
			$("#billInfoPrint2").hide();
		}
	});


	jQuery("#billSubList").jqGrid({
	   	url:'dolpinhotel/management/roomoccupancy/getGridData?status=3',
		datatype: "json",
	   	colNames:['id','房间号', '入住日期','预离日期','离开日期','入住天数','金额','状态','billId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'roomId',index:'roomId', width:100,formatter:'select', editoptions:{value:allRooms}},
	   		{name:'enterDate',index:'enterDate', width:100, editable:true, formatter:fmtDate },
	   		{name:'expectedCheckOutDate',index:'expectedCheckOutDate',width:100, editable:true, formatter:fmtDate},
	   		{name:'leaveDate',index:'leaveDate', width:100, editable:true, formatter:fmtDate},
	   		{name:'occupancyDays',index:'occupancyDays', width:80},
	   		{name:'amount',index:'amount', width:100},
	   		{name:'status',index:'status', width:80,formatter:'select', editoptions:{value:"0:入住中;1:已离开"}},
	   		{name:'billId',index:'billId', width:0}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
		jsonReader:{
	   		repeatitems: false
		},
	   	pager: '#billSubPager',
	   	sortname: 'id',
		sortorder: "desc",
		viewrecords: true,
		multiselect: false, //checkbox
		caption: "房间入住情况列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#billSubList").jqGrid('navGrid','#billSubPager',{edit:false,add:false,del:false,search:false});
	jQuery("#billSubList").jqGrid('hideCol',['id','billId','status']);//隐藏id,billId,状态列

	// 隐藏打印DIV
	$("#billInfoPrint1").hide();
	$("#billInfoPrint2").hide();

	//查询按钮点击事件
	$("#bill_manage_search_btn").click(function () { 
		var number = jQuery("#bill_manage_number").val();
		var amount = jQuery("#bill_manage_amount").val();
		var dateFrom = jQuery("#bill_manage_dateFrom").val();
		var dateTo = jQuery("#bill_manage_dateTo").val();
		var url = "dolpinhotel/management/bill/getGridData?number="+number+"&amount="+amount+"&dateFrom="+dateFrom+"&dateTo="+dateTo;
		jQuery("#billList").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
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
<legend>账单查询</legend>
<table>
	<tr>
		<td>
			账单号：
		</td>
		<td>
			<input type="text" id="bill_manage_number"/>
		</td>
		<td>
			金额：
		</td>
		<td>
			<input type="text" id="bill_manage_amount"/>
		</td>
	</tr>
	<tr>
		<td>
			日期：
		</td>
		<td colspan="3">
			<input type="text" id="bill_manage_dateFrom" class="datepicker"/>
			-
			<input type="text" id="bill_manage_dateTo" class="datepicker"/>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="right">
			<input type="button" id="bill_manage_search_btn" value="查询"/>
		</td>
	</tr>
</table>
</fieldset>
<br/>
<table id="billList"></table>
<div id="billPager"></div>
<br/>
<div id="billPrintDiv">
		<table id="billInfoPrint1">
			<tr>
				<td colspan="2">
					XX宾馆消费账单
				</td>
			</tr>
			<tr>
				<td>
					账单号：
					<input type="text" id="bill_manage_print_number"/>
				</td>
				<td>
					总金额：
					<input type="text" id="bill_manage_print_amount"/>
				</td>
			</tr>
		</table>
	
	<table id="billSubList"></table>
	
		<table id="billInfoPrint2">
			<tr>
				<td>
					收银员：
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					日期：
					<input type="text" id="bill_manage_print_date"/>
				</td>
			</tr>
		</table>
	
</div>

<div id="billSubPager"></div>