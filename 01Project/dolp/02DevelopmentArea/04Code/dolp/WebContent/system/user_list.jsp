<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#list").jqGrid({
	   	url:'system/user/getGridData.do',
		datatype: "json",
	   	colNames:['id','number', 'name','gender','age','birthday','phone'],
	   	colModel:[
	   		{name:'id',index:'id', width:55},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'gender',index:'gender', width:80},
	   		{name:'age',index:'age', width:80},		
	   		{name:'birthday',index:'birthday', width:80},		
	   		{name:'phone',index:'phone', width:150}		
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#pager',
	   	sortname: 'name',
	    viewrecords: true,
	    sortorder: "desc",
	    editurl:"system/user/deleteRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption:"用户列表"
	});
	jQuery("#list").jqGrid('navGrid','#pager',{edit:true,add:true,del:true});

	$("#addRow").click(function() {
		
	});
	$("#editSelRow").click(function() {
		
	});
});
</script>
<table id="list"></table>
<div id="pager"></div>
<input id="addRow" type="button" value="添加"/>
<input id="addRow" type="button" value="编辑"/>
