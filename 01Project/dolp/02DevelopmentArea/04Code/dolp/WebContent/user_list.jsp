<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User List Page</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/themes/flick/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	jQuery("#list2").jqGrid({
	   	url:'system/user/getGridData.do',
		datatype: "json",
	   	colNames:['id','number', 'name','sex','age','birthday','phone'],
	   	colModel:[
	   		{name:'id',index:'id', width:55},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'sex',index:'sex', width:80},
	   		{name:'age',index:'age', width:80},		
	   		{name:'birthday',index:'birthday', width:80},		
	   		{name:'phone',index:'phone', width:150}		
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#pager2',
	   	sortname: 'name',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"User List"
	});
	jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
});
</script>
</head>
<body>
<table id="list2"></table>
<div id="pager2"></div>
</body>
</html>