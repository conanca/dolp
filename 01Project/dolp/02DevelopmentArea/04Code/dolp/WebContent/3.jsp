<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/jquery.meio.mask.js" type="text/javascript"></script>
<script type="text/javascript" >
$(function() {
	$('input:text').setMask();
});
function geta(){
	alert(document.getElementById("decimal1").value);
}
</script>
演示自动格式化文本框的输入值<BR>

信用卡号:<input type="text" id="cc" name="cc" alt="cc" /><BR>
时间：<input type="text" id="time_example" alt="time" /><BR>
金额:<input type="text" id="decimal1" name="some_name" alt="decimal-us"/><BR>
日期：<input type="text" id="date_example" alt="date-cn" /><BR>
<button onclick="geta()">get</button>