<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.json-2.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("input:button,input:reset").button();
});

$(function() {
	$("#datepicker").datepicker();
});

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function add(){
	var form1 = document.getElementById("form1");
	var addedUser = $.toJSON($('form').serializeObject());
	form1.action = "system/user/add.do?addedUser=" + addedUser;
	form1.submit();
}

</script>
<form action="/system/user/add.do?addedUser=" method="post" id="form1">
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
			<input type="button" value="保存" onclick="add()">
			<input type="reset" value="重置">
		</td>
	</tr>
</table>
</form>