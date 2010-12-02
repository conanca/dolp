<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("#rolelist").jqGrid({
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%",
	   	datatype: "json",
	    jsonReader:{
	   		repeatitems: false
        },
	    viewrecords: true,
	    caption:"角色列表",
	   	url:'system/role/getGridData',
	    editurl:"system/role/editRow",
	   	colNames:['id','名称','描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0,hidden:true},
	   		{name:'name',index:'name', width:100,editable:true},
	   		{name:'description',index:'description', width:300,editable:true,edittype:"textarea"}	//设置弹出窗口中字段类型
	   	],
	   	pager: '#rolepager',
	   	sortname: 'id',
	    sortorder: "asc",
	    multiselect: true,
	    loadComplete: function(){
    	    var userData = $("#rolelist").getUserData();
			$.addMessage(userData);
    	}
	});
	$("#rolelist").setJqGridCUD('#rolepager',{edit:true,add:true,del:true,search:false});
});
</script>

<table id="rolelist"></table>
<div id="rolepager"></div>