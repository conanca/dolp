<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();
	
	$("#sysenumList").jqGrid({
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	datatype: "json",
	   	jsonReader:{
	   		repeatitems: false
        },
	    viewrecords: true,
	    caption: "系统枚举列表",
	   	url:'system/sysEnum/getSysEnumGridData',
	    editurl: "system/sysEnum/editSysEnum",
	   	colNames:['id','名称', '描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:100, editable:true},
	   		{name:'description',index:'description', width:300, editable:true, edittype:"textarea"}
	   	],
	   	pager: '#sysenumPager',
	   	sortname: 'id',
	    sortorder: "asc",
	    multiselect: false,
	    loadComplete: function(){
    	    var userData = $("#sysenumList").getUserData();
			$.addMessage(userData);
    	},
		onSelectRow: function(ids) {
			if(ids == null) {
				ids=0;
				if($("#sysenumSubList").getGridParam('records') >0 ) {
					$("#sysenumSubList").setGridParam({url:"system/sysEnum/getSysEnumItemGridData/"+ids,page:1});
					$("#sysenumSubList").trigger('reloadGrid');
				}
			} else {
				$("#sysenumSubList").setGridParam({editurl:"system/sysEnum/editSysEnumItem/"+ids});
				$("#sysenumSubList").setGridParam({url:"system/sysEnum/getSysEnumItemGridData/"+ids,page:1});
				$("#sysenumSubList").trigger('reloadGrid');
			}
		}
	});
	//不显示jqgrid自带的查询按钮
	$("#sysenumList").navGrid('#sysenumPager',{edit:true,add:true,del:true,search:false});
	$("#sysenumList").hideCol(['id']);//隐藏id列
	
	$("#sysenumSubList").jqGrid({
		rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
		jsonReader:{
	   		repeatitems: false
		},
		viewrecords: true,
		caption: "枚举项列表",
	   	url:'system/sysEnum/getSysEnumItemGridData',
		editurl: "system/sysEnum/editSysEnumItem",
		datatype: "json",
	   	colNames:['id','文本', '值','sysEnumId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'text',index:'text', width:100, editable:true},
	   		{name:'value',index:'value', width:100, editable:true},
	   		{name:'sysEnumId',index:'sysEnumId', width:0}
	   	],
	   	pager: '#sysenumSubPager',
	   	sortname: 'id',
		sortorder: "asc",
		multiselect: false, //checkbox
	    loadComplete: function(){
    	    var userData = $("#sysenumSubList").getUserData();
			$.addMessage(userData);
    	}
	});
	//不显示jqgrid自带的增删改查按钮
	$("#sysenumSubList").navGrid('#sysenumSubPager',{edit:true,add:true,del:true,search:false});
	$("#sysenumSubList").hideCol(['id','sysEnumId']);//隐藏id
});
</script>

<table id="sysenumList"></table>
<div id="sysenumPager"></div>
<table id="sysenumSubList"></table>
<div id="sysenumSubPager"></div>