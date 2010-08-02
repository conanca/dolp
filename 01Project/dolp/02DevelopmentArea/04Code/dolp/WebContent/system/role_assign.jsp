<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/i18n/jquery.localisation-min.js" type="text/javascript"></script>
<script src="js/ui.multiselect.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/ui.multiselect.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("input:submit").button();

	jQuery("#roleAssignUserList").jqGrid({
	   	url:'system/user/getGridData.do',
		datatype: "json",
	   	colNames:['id','number', 'name','gender','age','birthday','phone'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'gender',index:'gender', width:80,resizable:false},//不可调整宽度
	   		{name:'age',index:'age', width:80},		
	   		{name:'birthday',index:'birthday', width:80},		
	   		{name:'phone',index:'phone', width:150}		
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
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
			$("#assigningUserID").val(id);
			
			$("#roleIds option").each(function(){
				if($(this).attr("selected") == true){
					$(this).removeAttr("selected");
				}
			});
			
			var url1 = "system/user/getCurrentRoleIDs.do";
			var para = {"userId":id};
			$.getJSON(url1,para,function (data){
				$.each(data,function(index,value){
					$("#roleIds option[value='"+value+"']").attr("selected",true);
				});
			});
			
			$("#roleIds").multiselect("destroy");
			$.localise('ui-multiselect', {language: 'zh', path: 'js/i18n/'});
			$("#roleIds").multiselect();
		}
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#roleAssignUserList").jqGrid('navGrid','#roleAssignUserPager',{edit:false,add:false,del:false,search:false});
	jQuery("#roleAssignUserList").jqGrid('hideCol',['id']);//隐藏id列
	
	var url2 = "system/role/getAllRole.do";
	$.getJSON(url2, function (data){
		$.each(data,function(text,value) {
			$("#roleIds").append(new Option(text,value));
		});
	});

	//$.localise('ui-multiselect', {language: 'zh', path: 'js/i18n/'});
	//$("#roleIds").multiselect();

	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'system/user/assignRole.do',
			type:		'get',
			clearForm:	true,
			resetForm:	true
		};
	$('#roleAssignForm').submit(function() {
		$(this).ajaxSubmit(options);
		return false;
	});
});

//提交前
function showRequest(formData, jqForm, options) {
	
}
//提交后获得Respons后
function showResponse(responseText, statusText, xhr, $form)  {
	alert('分配成功');
}
</script>
<table id="roleAssignUserList"></table>
<div id="roleAssignUserPager"></div>
<br>
<form id="roleAssignForm">
	<input type="hidden" name="userId" id="assigningUserID">
	<select id="roleIds" name="assignedRoleIds[]" class="multiselect" multiple="multiple" size="6">
	</select>
	<input type="submit" value="分配">
</form>