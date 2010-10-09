<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#sysenumList").jqGrid({
	   	url:'system/sysEnum/getSysEnumGridData',
		datatype: "json",
	   	colNames:['id','名称', '描述'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:100, editable:true},
	   		{name:'description',index:'description', width:300, editable:true, edittype:"textarea"}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#sysenumPager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "system/sysEnum/editSysEnum",
	    multiselect: false, //checkbox
	    caption: "系统枚举列表",
		onSelectRow: function(ids) {
			if(ids == null) {
				ids=0;
				if($("#sysenumSubList").jqGrid('getGridParam','records') >0 ) {
					$("#sysenumSubList").jqGrid('setGridParam',{url:"system/sysEnum/getSysEnumItemGridData/"+ids,page:1});
					$("#sysenumSubList").trigger('reloadGrid');
				}
			} else {
				$("#sysenumSubList").jqGrid('setGridParam',{editurl:"system/sysEnum/editSysEnumItem/"+ids});
				$("#sysenumSubList").jqGrid('setGridParam',{url:"system/sysEnum/getSysEnumItemGridData/"+ids,page:1});
				$("#sysenumSubList").trigger('reloadGrid');
			}
		}
	});
	//不显示jqgrid自带的查询按钮
	jQuery("#sysenumList").jqGrid('navGrid','#sysenumPager',{edit:true,add:true,del:true,search:false});
	jQuery("#sysenumList").jqGrid('hideCol',['id']);//隐藏id列
	
	jQuery("#sysenumSubList").jqGrid({
	   	url:'system/sysEnum/getSysEnumItemGridData',
		datatype: "json",
	   	colNames:['id','文本', '值','sysEnumId'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'text',index:'text', width:100, editable:true},
	   		{name:'value',index:'value', width:100, editable:true},
	   		{name:'sysEnumId',index:'sysEnumId', width:0}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
		jsonReader:{
	   		repeatitems: false
		},
	   	pager: '#sysenumSubPager',
	   	sortname: 'id',
		sortorder: "asc",
		viewrecords: true,
		editurl: "system/sysEnum/editSysEnumItem",
		multiselect: false, //checkbox
		caption: "枚举项列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#sysenumSubList").jqGrid('navGrid','#sysenumSubPager',{edit:true,add:true,del:true,search:false});
	jQuery("#sysenumSubList").jqGrid('hideCol',['id','sysEnumId']);//隐藏id
});
</script>
<table id="sysenumList"></table>
<div id="sysenumPager"></div>
<table id="sysenumSubList"></table>
<div id="sysenumSubPager"></div>