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
	   	sortname: 'number',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl:"system/user/deleteRow.do",	//del:true
	    multiselect: true, //checkbox
	    caption:"用户列表"
	});
	//显示删除按钮
	jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:true});
	jQuery("#list").jqGrid('hideCol',['id']);//隐藏id列
	//初始化用户信息界面
	$("#userInfo").dialog({width: 580, hide: 'slide' , autoOpen: false });
	
	$("#addRow").click(function() {
		$("#userInfo").dialog( "open" );
	});
	
	$("#editSelRow").click(function() {
		// 获取当前选中行
		var id = jQuery("#list").jqGrid('getGridParam','selrow');
		if (id) {
			var ret = jQuery("#list").jqGrid('getRowData',id);
			// 将当前选中行的数据转换为json格式
			var editRowJsonData = eval('('+$.toJSON(ret)+')');
			$("#form1").fill(editRowJsonData);
			$("#userInfo").dialog( "open" );
		} else {
			alert("请选择要编辑的记录");
		} 
	});
	
	$("#delSelRow").click(function(){
		var gr = jQuery("#list").jqGrid('getGridParam','selarrrow');
		if( gr != null ){
			jQuery("#list").jqGrid('delGridRow',gr,{reloadAfterSubmit:false});
		}
		else{
			alert("请选择要删除的记录");
		}
	});

	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'system/user/save.do',
			type:		'get',
			clearForm:	true,
			resetForm:	true
		};
	$('#form1').submit(function() {
		$(this).ajaxSubmit(options);
		//关闭用户信息界面
		$("#userInfo").dialog( "close" );
		//刷新表格
		$('#list').trigger("reloadGrid");
		return false;
	});

	$("#userInfocancel").click(function() {
		$('#form1')[0].reset();
		$("#userInfo").dialog( "close" );
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
<table id="list"></table>
<div id="pager"></div>
<input id="addRow" type="button" value="添加"/>
<input id="editSelRow" type="button" value="编辑"/>
<input id="delSelRow" type="button" value="删除"/>

<div id="userInfo" title="用户信息">
	<form id="form1">
	<table>
		<tr>
			<td>
				用户编号：
			</td>
			<td>
				<input type="hidden" name="id">
				<input type="text" name="number"/>
			</td>
			<td>
				用户姓名：
			</td>
			<td>
				<input type="text" name="name"/>
			</td>
		</tr>
		<tr>
			<td>
				登录密码：
			</td>
			<td>
				<input type="password" name="password"/>
			</td>
			<td>
				再次输入密码：
			</td>
			<td>
				<input type="password"/>
			</td>
		</tr>
			<tr>
			<td>
				性别：
			</td>
			<td>
				男:<input type="radio" name="gender" value="M"/>
				女:<input type="radio" name="gender" value="F"/>
			</td>
			<td>
				年龄：
			</td>
			<td>
				<input type="text" name="age"/>
			</td>
		</tr>
		<tr>
			<td>
				出生日期：
			</td>
			<td>
				<input type="text" name="birthday" id="datepicker"/>
			</td>
			<td>
				电话号码：
			</td>
			<td>
				<input type="text" name="phone"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存">
				<input type="reset" value="重置">
				<input id="userInfocancel" type="button" value="取消">
			</td>
		</tr>
	</table>
	</form>
</div>
