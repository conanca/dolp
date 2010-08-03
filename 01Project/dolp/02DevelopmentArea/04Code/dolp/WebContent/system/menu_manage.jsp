<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){

	jQuery("#menulist").jqGrid({
	   	url:'system/menu/getGridData.do',
	   	datatype: "json",
	   	colNames:['id','name','url','roleId','description'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:60,editable:true},
	   		{name:'url',index:'url', width:200,editable:true},
	   		{name:'description',index:'description', width:150,editable:true,edittype:"textarea"},	//设置弹出窗口中字段类型
	   		{name:'roleId',index:'roleId', width:0,editable:true}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	pager: '#menupager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    ExpandColumn: "name",
	    treeGrid: true,
	    ExpandColClick: true,
	    //editurl:"system/menu/editRow.do",	//del:true
	    caption:"菜单列表"
	});
	//不显示查询按钮
	jQuery("#menulist").jqGrid('navGrid','#menupager',{edit:true,add:true,del:true,search:false});
	jQuery("#menulist").jqGrid('hideCol',['id','roleId']);//隐藏id列

});

</script>
<table id="menulist"></table>
<div id="menupager"></div>
