<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {

	var theme = $.cookie('theme');
	if(theme){
		$('#jQueryUICssSrc').attr('href', 'css/themes/' + theme + '/jquery-ui-1.8.4.custom.css');
		$("#jQueryUICssSwitch option:selected").each(function() {
			$(this).attr("selected", false);
		});
		$("#jQueryUICssSwitch").val(theme);
	}
	$('#jQueryUICssSwitch').change(function() {
		if($.cookie('theme')){
			$.cookie('theme', $('#jQueryUICssSwitch').val());
		}else{
			var date = new Date();
			date.setTime(date.getTime() + (1 * 24 * 60 * 60 * 1000));
			$.cookie('theme', $('#jQueryUICssSwitch').val(), { expires: date, path: '/'});
		}
		$('#jQueryUICssSrc').attr('href', 'css/themes/' + $('#jQueryUICssSwitch').val() + '/jquery-ui-1.8.4.custom.css');
	});
	
	var url = 'system/user/getCurrentUserName';
	$.ajaxSetup({ async: false});
	$.getJSON(url,function(respData){
		$('#CurrentUserName').text(respData.returnData);
		var sysMsg = respData.userdata;
		if(sysMsg){
			$.addMessage(sysMsg);
		}
	});
});
</script>
<h1><b>AAAA</b></h1>
<div align="right">
	欢迎：<label id="CurrentUserName"></label>&nbsp;|&nbsp;
	<a href="system/user/logout">登出</a>&nbsp;|&nbsp;
	换肤：
	<select id="jQueryUICssSwitch">
		<option value="black-tie">black-tie</option>
		<option value="blitzer">blitzer</option>
		<option value="cupertino">cupertino</option>
		<option value="dark-hive">dark-hive</option>
		<option value="dot-luv">dot-luv</option>
		<option value="eggplant">eggplant</option>
		<option value="excite-bike">excite-bike</option>
		<option value="flick" selected="selected">flick</option>
		<option value="hot-sneaks">hot-sneaks</option>
		<option value="humanity">humanity</option>
		<option value="le-frog">le-frog</option>
		<option value="mint-choc">mint-choc</option>
		<option value="overcast">overcast</option>
		<option value="pepper-grinder">pepper-grinder</option>
		<option value="redmond">redmond</option>
		<option value="smoothness">smoothness</option>
		<option value="south-street">south-street</option>
		<option value="start">start</option>
		<option value="sunny">sunny</option>
		<option value="swanky-purse">swanky-purse</option>
		<option value="trontastic">trontastic</option>
		<option value="ui-darkness">ui-darkness</option>
		<option value="ui-lightness">ui-lightness</option>
		<option value="vader">vader</option>
	</select>
</div>