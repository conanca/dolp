<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){

	//获取所有已入住房间的房间号-房间Id键值对
	var url3 = "dolpinhotel/setup/room/getAllRoomForSelectOption.do";
	var allRooms;
	$.ajaxSetup({ async: false});//设为同步模式
	$.getJSON(url3,function(response){
		allRooms = response;
	});
	
	jQuery("#roomOccupancyList").jqGrid({
	   	url:'dolpinhotel/management/roomoccupancy/getGridData.do',
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
	   	pager: '#roomOccupancyPager',
	   	sortname: 'id',
		sortorder: "desc",
		viewrecords: true,
		multiselect: false, //checkbox
		caption: "房间入住情况列表",
		onSelectRow: function(ids) {
			 if(ids == null) {
				ids=0;
				if($("#customerSubList").jqGrid('getGridParam','records') >0 ) {
					$("#customerSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/customer/getGridDataByRoomOccId.do?roomOccId="+ids,page:1});
					$("#customerSubList").trigger('reloadGrid'); 
				}
			} else {
				$("#customerSubList").jqGrid('setGridParam',{url:"dolpinhotel/management/customer/getGridDataByRoomOccId.do?roomOccId="+ids,page:1});
				$("#customerSubList").trigger('reloadGrid');
			}
		}
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roomOccupancyList").jqGrid('navGrid','#roomOccupancyPager',{edit:false,add:false,del:false,search:false});
	jQuery("#roomOccupancyList").jqGrid('hideCol',['id','billId']);//隐藏id列


	jQuery("#customerSubList").jqGrid({
	   	url:'dolpinhotel/management/customer/getGridDataByRoomOccId.do',
		datatype: "json",
	   	colNames:['id','序号', '姓名','性别','证件类型','证件号','籍贯地址','roomOccupancyId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'no',index:'no', width:20},
	   		{name:'name',index:'name', width:80},
	   		{name:'gender',index:'gender', width:50},
	   		{name:'certificateType',index:'certificateType', width:60},		
	   		{name:'credentialNumber',index:'credentialNumber', width:150},		
	   		{name:'address',index:'address', width:300},
	   		{name:'roomOccupancyId',index:'roomOccupancyId', width:0}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
		jsonReader:{
	   		repeatitems: false
		},
	   	pager: '#customerSubPager',
	   	sortname: 'no',
		sortorder: "asc",
		viewrecords: true,
		multiselect: false, //checkbox
		caption: "所选房间顾客列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#customerSubList").jqGrid('navGrid','#customerSubPager',{edit:false,add:false,del:false,search:false});
	jQuery("#customerSubList").jqGrid('hideCol',['id','roomOccupancyId']);//隐藏id列
});

function fmtDate(value){
	if(value){
		return value.substr(0,10);
	}else{
		return '';
	}
}
</script>

<table id="roomOccupancyList"></table>
<div id="roomOccupancyPager"></div>
<br/>
<table id="customerSubList"></table>
<div id="customerSubPager"></div>