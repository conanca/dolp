<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$("input:button,input:submit,input:reset").button();

	$("#home_btn1").click(function() {
		var st = "#t12";
		if($(st).html() != null ) {
			maintab.tabs('select',st);
		} else {
			maintab.tabs('add',st, "可用房确认");
			$(st,"#tabs").load("hotel/available_room_check.jsp");
		}
	});


	$("#home_btn2").click(function() {
		var st = "#t13";
		if($(st).html() != null ) {
			maintab.tabs('select',st);
		} else {
			maintab.tabs('add',st, "入住情况");
			$(st,"#tabs").load("hotel/room_occupancy_manage.jsp");
		}
	});

	$("#home_btn3").click(function() {
		var st = "#t14";
		if($(st).html() != null ) {
			maintab.tabs('select',st);
		} else {
			maintab.tabs('add',st, "登记入住");
			$(st,"#tabs").load("hotel/check_in.jsp");
		}
	});

	$("#home_btn4").click(function() {
		var st = "#t13";
		if($(st).html() != null ) {
			maintab.tabs('select',st);
		} else {
			maintab.tabs('add',st, "结帐离开");
			$(st,"#tabs").load("hotel/room_occupancy_manage.jsp");
		}
	});
});
</script>
<br/>
<table>
	<tr>
		<td>
			<input type="button" id="home_btn1" value="可用房确认"/>
		</td>
		<td>
			<input type="button" id="home_btn2" value="入住情况"/>
		</td>
	</tr>
		<tr>
		<td>
			<input type="button" id="home_btn3" value="登记入住"/>
		</td>
		<td>
			<input type="button" id="home_btn4" value="结帐离开"/>
		</td>
	</tr>
</table>