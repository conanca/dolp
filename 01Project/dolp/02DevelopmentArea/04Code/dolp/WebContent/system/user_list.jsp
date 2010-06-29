<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/jquery.json-2.2.min.js" type="text/javascript"></script>
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

	$("#userInfo").dialog({width: 550, hide: 'slide' , autoOpen: false });
	
	$("#addRow").click(function() {
		$("#userInfo").dialog( "open" );
	});
	$("#editSelRow").click(function() {
		// 获取当前选中行
		var id = jQuery("#list").jqGrid('getGridParam','selrow');
		if (id) {
			var ret = jQuery("#list").jqGrid('getRowData',id);
			// 将当前选中行的数据转换为json格式
			var userInfoData = $.toJSON(ret);
			alert(userInfoData);
			$("#form1").fill(userInfoData);
		} else {
			alert("Please select row");
		} 
		$("#userInfo").dialog( "open" );
	});
	$("#delSelRow").click(function(){
		var gr = jQuery("#list").jqGrid('getGridParam','selarrrow');
		if( gr != null ){
			jQuery("#list").jqGrid('delGridRow',gr,{reloadAfterSubmit:false});
		}
		else{
			alert("Please Select Row to delete!");
		}
	}); 
});
</script>
<table id="list"></table>
<div id="pager"></div>
<input id="addRow" type="button" value="添加"/>
<input id="editSelRow" type="button" value="编辑"/>
<input id="delSelRow" type="button" value="删除"/>
<div id="userInfo" title="用户信息">
	<%@include file="/system/user_info.jsp" %>
</div>
