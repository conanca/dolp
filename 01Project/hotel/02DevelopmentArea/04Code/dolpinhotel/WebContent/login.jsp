<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.4.custom.min.js" type="text/javascript"></script>
<link id="jQueryUICssSrc" href="css/themes/ui-lightness/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<style type="text/css" media="all">
	body {font-size:80%;}
</style>
<script type="text/javascript">
$(function() {
	$("#login").dialog();
	$("input:submit,input:reset").button();
});
</script>
</head>
<body>
<div id="login" title="请登录">
	<form action="system/user/login" method="post">
		<table>
			<tr>
				<td>
					用户编号：
				</td>
				<td>
					<input type="text" name="num"/>
				</td>
			</tr>
			<tr>
				<td>
					密&nbsp;&nbsp;码：
				</td>
				<td>
					<input type="password" name="pwd"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<center>
						<input type="submit" value="登录">
						<input type="reset" value="重置">
					</center>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>