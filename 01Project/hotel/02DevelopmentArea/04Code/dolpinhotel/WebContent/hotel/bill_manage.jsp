<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();
	
	//获取所有已入住房间的房间号-房间Id键值对
	var url3 = "dolpinhotel/setup/room/getAllRoomForSelectOption.do";
	var allRooms;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url3,function(response){
		allRooms = response;
	});
	
	jQuery("#billList").jqGrid({
	   	url:'dolpinhotel/management/bill/getGridData.do',
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
	    editurl: "dolpinhotel/management/bill/editRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption: "账单列表",
		onSelectRow: function(ids) {
			 if(ids == null) {
				ids=0;
				if($("#billSubList").jqGrid('getGridParam','records') >0 ) {
					$("#billSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/roomoccupancy/getGridData.do?billId="+ids,page:1});
					$("#billSubList").trigger('reloadGrid'); 
				}
			} else {
				$("#billSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/roomoccupancy/getGridData.do?billId="+ids,page:1});
				$("#billSubList").trigger('reloadGrid');
			}
		}
	});
	//不显示jqgrid自带的查询按钮
	jQuery("#billList").jqGrid('navGrid','#billPager',{edit:true,add:false,del:true,search:false});
	jQuery("#billList").jqGrid('hideCol',['id']);//隐藏id列


	jQuery("#billSubList").jqGrid({
	   	url:'dolpinhotel/management/roomoccupancy/getGridData.do?billId=-1',
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
		multiselect: true, //checkbox
		caption: "房间入住情况列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#billSubList").jqGrid('navGrid','#billSubPager',{edit:false,add:false,del:false,search:false});
	jQuery("#billSubList").jqGrid('hideCol',['id','billId']);//隐藏id,billId列
});

function fmtDate(value){
	if(value){
		return value.substr(0,10);
	}else{
		return '';
	}
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
	var number = jQuery("#bill_manage_number").val();
	var amount = jQuery("#bill_manage_amount").val();
	var dateFrom = jQuery("#bill_manage_dateFrom").val();
	var dateTo = jQuery("#bill_manage_dateTo").val();
	var url = "dolpinhotel/management/bill/getGridData.do?number="+number+"&amount="+amount+"&dateFrom="+dateFrom+"&dateTo="+dateTo;
	jQuery("#billList").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
}
function enableAutosubmit(state){
	flAuto = state;
	jQuery("#bill_manage_search_btn").attr("disabled",state);
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
			<input type="text" id="bill_manage_number" onkeydown="doSearch(arguments[0]||event)" onkeydown="doSearch(arguments[0]||event)"/>
		</td>
		<td>
			金额：
		</td>
		<td>
			<input type="text" id="bill_manage_amount" onkeydown="doSearch(arguments[0]||event)" onkeydown="doSearch(arguments[0]||event)"/>
		</td>
	</tr>
	<tr>
		<td>
			入住日期：
		</td>
		<td colspan="3">
			<input type="text" id="bill_manage_dateFrom" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
			-
			<input type="text" id="bill_manage_dateTo" class="datepicker" onkeydown="doSearch(arguments[0]||event)" onblur="doSearch(arguments[0]||event)"/>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="right">
			<input type="button" id="bill_manage_search_btn" value="查询" onclick="gridReload()"/>
			自动查询:
			<input type="checkbox" id="bill_manage_autosearch" onclick="enableAutosubmit(this.checked)">
		</td>
	</tr>
</table>
</fieldset>
<br/>
<table id="billList"></table>
<div id="billPager"></div>
<br/>
<table id="billSubList"></table>
<div id="billSubPager"></div>