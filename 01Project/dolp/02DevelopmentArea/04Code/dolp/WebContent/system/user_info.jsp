<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	$("input:submit,input:reset").button();
	$("#datepicker").datepicker();

	var options = {
	    beforeSubmit:showRequest,
	    success:	showResponse,
		url:		'system/user/add.do',
		type:		'get',
		clearForm:	true,
		resetForm:	true
	};
	$('#form1').submit(function() {
		$(this).ajaxSubmit(options);
			return false;
	});

});

function showRequest(formData, jqForm, options) {
	
}
function showResponse(responseText, statusText, xhr, $form)  {
	alert('保存成功');
}
</script>
<form id="form1" action='system/user/add.do'>
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
		<td colspan="2">
			<input type="submit" value="保存">
			<input type="reset" value="重置">
		</td>
	</tr>
</table>
</form>