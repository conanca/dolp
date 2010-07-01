<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/jquery.json-2.2.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.formFill.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$("#datepicker").datepicker();	
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#rolelist").jqGrid({
	   	url:'system/role/getGridData.do',
		datatype: "json",
	   	colNames:['id','name','description'],
	   	colModel:[
	   		{name:'id',index:'id', width:55},
	   		{name:'name',index:'name', width:100},
	   		{name:'description',index:'description', width:200}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#rolepager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl:"system/role/deleteRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption:"角色列表"
	});
	//显示删除按钮
	jQuery("#rolelist").jqGrid('navGrid','#rolepager',{edit:false,add:false,del:true});
	jQuery("#rolelist").jqGrid('hideCol',['id']);//隐藏id列
	//初始化角色信息界面
	$("#roleInfo").dialog({width: 550, hide: 'slide' , autoOpen: false });
	
	$("#addRow1").click(function() {
		$("#roleInfo").dialog( "open" );
	});
	
	$("#editSelRow1").click(function() {
		// 获取当前选中行
		var id = jQuery("#rolelist").jqGrid('getGridParam','selrow');
		if (id) {
			var ret = jQuery("#rolelist").jqGrid('getRowData',id);
			// 将当前选中行的数据转换为json格式
			var editRowJsonData = eval('('+$.toJSON(ret)+')');
			$("#roleform").fill(editRowJsonData);
			$("#roleInfo").dialog( "open" );
		} else {
			alert("请选择要编辑的记录");
		} 
	});
	
	$("#delSelRow1").click(function(){
		var gr = jQuery("#rolelist").jqGrid('getGridParam','selarrrow');
		if( gr != null ){
			jQuery("#rolelist").jqGrid('delGridRow',gr,{reloadAfterSubmit:false});
		}
		else{
			alert("请选择要删除的记录");
		}
	});

	var options1 = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'system/role/save.do',
			type:		'get',
			clearForm:	true,
			resetForm:	true
		};
		$('#roleform').submit(function() {
			$(this).ajaxSubmit(options1);
			//关闭角色信息界面
			$("#roleInfo").dialog( "close" );
			//刷新表格
			$('#rolelist').trigger("reloadGrid");
			return false;
		});
});

//提交前
function showRequest(formData, jqForm, options) {
	
}
//提交后获得Respons后
function showResponse(responseText, statusText, xhr, $form)  {
	alert('保存成功');
}
</script>
<table id="rolelist"></table>
<div id="rolepager"></div>
<input id="addRow1" type="button" value="添加"/>
<input id="editSelRow1" type="button" value="编辑"/>
<input id="delSelRow1" type="button" value="删除"/>

<div id="roleInfo" title="角色信息">
	<form id="roleform">
	<table>
		<tr>
			<td>
				角色名称：
			</td>
			<td>
				<input type="hidden" name="id">
				<input type="text" name="name"/>
			</td>
			<td>
				角色描述：
			</td>
			<td>
				<textarea rows="" cols="" name="description"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存">
				<input type="reset" value="重置">
			</td>
		</tr>
	</table>
	</form>
</div>
