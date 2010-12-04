<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("#menuAssignList").jqGrid({
	   	rowNum:10,
	   	rowList:[10,20,30],
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
	    loadComplete: function(){
    	    var userData = $("#menuAssignList").getUserData();
			$.addMessage(userData);
    	}
	});
	$("#menuAssignList").navGrid('#menuAssignPager',{edit:false,add:false,del:false,search:false});

	$("#menuGrid").jqGrid({
		height: "auto",
		treeGrid: true,
		treeGridModel: 'nested',
		pager: false,
		ExpandColumn: "menu",
		ExpandColClick: true,
		treeIcons: {leaf:'ui-icon-document-b'},
		datatype: "json",
		rowNum: 200,
		caption: "菜单列表",
		url: "system/menu/getGridData1",
		colNames: ["id","功能菜单","url","描述","已分配"],
		colModel: [
			{name: "id",index:"id", width:0,hidden:true,key:true},
			{name: "menu",index:"menu", width:200, resizable: false, sortable:false},
			{name: "url",index:"url", width:0,hidden:true},
			{name: "description",index:"description", width:300},
			{name: 'enbl', index:'enbl', width: 60, align:'center', formatter:'checkbox', formatoptions:{disabled:false}, editoptions:{value:'1:0',
					dataEvents: [
						{ type: 'click', fn: function(e) { alert('test'); } }
					]
				}
			}
		],
		pager: '#menuPager',
		multiselect: true,
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
<div id="menuPager"></div>