<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){

	jQuery("#rolelist").jqGrid({
	   	url:'system/role/getGridData',
		datatype: "json",
	   	colNames:['id','名称','描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:100,editable:true},
	   		{name:'description',index:'description', width:300,editable:true,edittype:"textarea"}	//设置弹出窗口中字段类型
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#rolepager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl:"system/role/editRow",	//del:true
	    multiselect: true, //checkbox
	    caption:"角色列表"
	});
	//不显示查询按钮
	jQuery("#rolelist").jqGrid('navGrid','#rolepager',{edit:true,add:true,del:true,search:false});
	jQuery("#rolelist").jqGrid('hideCol',['id']);//隐藏id列

});

</script>
<table id="rolelist"></table>
<div id="rolepager"></div>
