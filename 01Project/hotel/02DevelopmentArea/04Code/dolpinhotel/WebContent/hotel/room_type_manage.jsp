<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();
	
	$("#roomTypeList").jqGrid({
		rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    datatype: "json",
	    jsonReader:{
	   		repeatitems: false
        },
	    caption: "房间类型列表",
	    viewrecords: true,
	   	url:'dolpinhotel/setup/roomtype/getGridData',
	    editurl: "dolpinhotel/setup/roomtype/editRow",	//del:true
	   	colNames:['id','房间类型', '价格','描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0,hidden:true},
	   		{name:'name',index:'name', width:100, editable:true},
	   		{name:'price',index:'price', width:100, editable:true},
	   		{name:'description',index:'description', width:300, editable:true, edittype:'textarea'}
	   	],
	   	pager: '#roomTypePager',
	   	sortname: 'price',
	    sortorder: "asc",
	    multiselect: true, //checkbox
	    loadComplete: function(){
			$.addMessage($("#roomTypeList").getGridParam("userData"));
		}
	});
	//开启jqgrid自带的增删改功能
	$("#roomTypeList").setJqGridCUD('#roomTypePager',{edit:true,add:true,del:true,search:false});
});
</script>

<table id="roomTypeList"></table>
<div id="roomTypePager"></div>