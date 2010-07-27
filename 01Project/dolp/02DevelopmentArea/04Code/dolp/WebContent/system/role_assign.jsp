<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.twosidedmultiselect.js" type="text/javascript"></script>
<script src="js/jquery.tinysort.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/twosidedmultiselect.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){;
	jQuery("#roleAssignUserList").jqGrid({
	   	url:'system/user/getGridData.do',
		datatype: "json",
	   	colNames:['id','number', 'name','gender','age','birthday','phone'],
	   	colModel:[
	   		{name:'id',index:'id', width:55},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'gender',index:'gender', width:80,resizable:false},//不可调整宽度
	   		{name:'age',index:'age', width:80},		
	   		{name:'birthday',index:'birthday', width:80},		
	   		{name:'phone',index:'phone', width:150}		
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	height: "100%", //自动调整高度(无滚动条)
		jsonReader:{
	   		repeatitems: false
		},
	   	pager: '#roleAssignUserPager',
	   	sortname: 'number',
		sortorder: "asc",
		viewrecords: true,
		caption:"用户列表",
		onSelectRow: function(id){
			var url1 = "system/user/getCurrentRoleIDs.do";
			var para = {"userId":id};
			$.getJSON(url1,para,function (data){
				$.each(data,function(index,value){
					$("#roleIds option[value='"+value+"']").attr("selected","true");
				});	
			});
		}
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roleAssignUserList").jqGrid('navGrid','#roleAssignUserPager',{edit:false,add:false,del:false,search:false});
	jQuery("#roleAssignUserList").jqGrid('hideCol',['id']);//隐藏id列
	
	var url2 = "system/role/getAllRole.do";
	$.getJSON(url2, function (data){
		$.each(data,function(text,value) {
			$("#roleIds")[0].options.add(new Option(text,value));
		});
	});
	$(".multiselect").twosidedmultiselect(true);
});
</script>
<table id="roleAssignUserList"></table>
<div id="roleAssignUserPager"></div>
<br>
<div>
	<select id="roleIds" name="roleIds[]" class="multiselect" multiple="multiple" size="6">
	</select>
	<br><br><br><br><br><br><br>
</div>