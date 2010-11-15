<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#roomTypeList").jqGrid({
	   	url:'dolpinhotel/setup/roomtype/getGridData',
		datatype: "json",
	   	colNames:['id','房间类型', '价格','描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:100, editable:true},
	   		{name:'price',index:'price', width:100, editable:true},
	   		{name:'description',index:'description', width:300, editable:true, edittype:'textarea'}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#roomTypePager',
	   	sortname: 'price',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "dolpinhotel/setup/roomtype/editRow",	//del:true
	    multiselect: true, //checkbox
	    caption: "房间类型列表",
	    loadComplete: function(){
			$.addMessage(jQuery("#roomTypeList").getGridParam("userData"));
		}
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roomTypeList").jqGrid('navGrid','#roomTypePager',{edit:true,add:true,del:true,search:false});
	jQuery("#roomTypeList").jqGrid('hideCol',['id']);//隐藏id列
});

</script>
<table id="roomTypeList"></table>
<div id="roomTypePager"></div>