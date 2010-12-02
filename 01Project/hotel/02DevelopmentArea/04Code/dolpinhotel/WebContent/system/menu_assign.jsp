<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("#menuAssignList").jqGrid({
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
	   	pager: '#menuAssignPager',
	   	sortname: 'id',
	    sortorder: "asc",
	    multiselect: true,
	    loadComplete: function(){
    	    var userData = $("#menuAssignList").getUserData();
			$.addMessage(userData);
    	}
	});
	$("#menuAssignList").navGrid('#menuAssignPager',{edit:false,add:false,del:false,search:false});

	$("#menuGrid").jqGrid({
		height: "auto",
		//autowidth: true, 为适应IE6而注掉此句
		treeGrid: true,
		treeGridModel: 'nested',
		pager: false,
		ExpandColumn: "menu",
		ExpandColClick: true,
		treeIcons: {leaf:'ui-icon-document-b'},
		datatype: "json",
		rowNum: 200,
		caption: "",
		multiselect: true,
		url: "system/menu/getGridData",
		colNames: ["id","功能菜单","url","description"],
		colModel: [
			{name: "id",width:0,hidden:true,key:true},
			{name: "menu", width:180, resizable: false, sortable:false},
			{name: "url",width:0,hidden:true},
			{name: "description",width:0,hidden:true}
		],
	    loadComplete: function(){
			$.addMessage($("#menuGrid").getGridParam("userData"));
		}
	});
});
</script>

<table id="menuAssignList"></table>
<div id="menuAssignPager"></div>
<br/>
<table id="menuGrid"></table>