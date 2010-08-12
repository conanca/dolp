<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){

	jQuery("#customerlist").jqGrid({
	   	url:'#',
		datatype: "json",
	   	colNames:['id','姓名','性别','证件类型','证件号码','籍贯地址','orderId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:60,editable:true},
	   		{name:'gender',index:'gender', width:50,editable:true},
	   		{name:'certificateType',index:'certificateType', width:60,editable:true},
	   		{name:'credentialNumber',index:'credentialNumber', width:150,editable:true},
	   		{name:'address',index:'address', width:300,editable:true},
	   		{name:'orderId',index:'orderId', width:0,editable:true}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#customerpager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    loadonce: true,
	    editurl:"#",	//del:true
	    caption:"顾客信息列表"
	});
	//不显示查询按钮
	jQuery("#customerlist").jqGrid('navGrid','#customerpager',{});
	jQuery("#customerlist").jqGrid('hideCol',['id','orderId']);//隐藏id列,orderId列

});

</script>
<div id="roomInfo" title="用户信息">
	<form id="orderFrom">
		<table>
			<tr>
				<td>
					入住日期：
				</td>
				<td>
					<input type="text" name="birthday" class="datepicker"/>
				</td>
				<td>
					预离日期：
				</td>
				<td>
					<input type="text" name="birthday" class="datepicker"/>
				</td>
			</tr>
		</table>
		<table id="customerlist"></table>
		<div id="customerpager"></div>
	</form>
</div>