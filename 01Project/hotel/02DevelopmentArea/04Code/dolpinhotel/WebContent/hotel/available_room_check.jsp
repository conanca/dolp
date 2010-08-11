<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	jQuery("#availableRoomCheckList").jqGrid({
	   	url:'dolpinhotel/management/availableroomcheck/getGridData.do',
		datatype: "json",
	   	colNames:['房间类型','可用房数量'],
	   	colModel:[
	   		{name:'roomType',index:'roomType', width:90},
	   		{name:'count',index:'count', width:60},
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	pager: '#availableRoomCheckPager',
	   	sortname: 'count',
		sortorder: "desc",
		viewrecords: true,
		caption:"可用房列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#availableRoomCheckList").jqGrid('navGrid','#availableRoomCheckPager',{edit:false,add:false,del:false,search:false});
});
</script>
<table id="availableRoomCheckList"></table>
<div id="availableRoomCheckPager"></div>