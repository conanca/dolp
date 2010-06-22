<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dolp Main Page</title>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/jquery.layout.min-1.2.0.js" type="text/javascript"></script>
<script src="js/ui-combobox.js" type="text/javascript"></script>
<link id="jQueryUICssSrc" href="css/themes/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" type="text/css" />
<link href="css/ui-combobox.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/layout-tabs.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css" media="all">
	body {font-size:80%;}
	#jQueryUICssSwitch {width:150px;}
</style>
<script type="text/javascript">
var myLayout;
var maintab;
$(function () {
	myLayout = $('body').layout({ applyDefaultStyles: true });
	myLayout.sizePane("north", 100);
});
$(function() {
	maintab =jQuery('#tabs','#RightPane').tabs({
        add: function(e, ui) {
            // append close thingy
            $(ui.tab).parents('li:first')
                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="Close Tab"></span>')
                .find('span.ui-tabs-close')
                .click(function() {
                    maintab.tabs('remove', $('li', maintab).index($(this).parents('li:first')[0]));
                });
            // select just added tab
            maintab.tabs('select', '#' + ui.panel.id);
        }
    });
	
	$('#jQueryUICssSwitch').combobox({
	    list: [
	            { value: "flick", text: "flick" },
	            { value: "smoothness", text: "smoothness" },
	            { value: "ui-lightness", text: "ui-lightness", selected: true },
	            { value: "le-frog", text: "le-frog"}
	            ]
	        , changed: function(e, ui) {
	            $('#jQueryUICssSrc').attr('href', 'css/themes/' + ui.value + '/jquery-ui-1.8.2.custom.css');
	        }
	});
	$("button, input:submit,input:button", ".demo").button();
});
</script>
</head>
<body>
	<div id="UpPane" class="ui-layout-north ui-widget ui-widget-content" >
		当前用户：${logonUser.name}
	</div>
	<div id="RightPane" class="ui-layout-center ui-helper-reset ui-widget-content" >
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">默认页</a></li>
			</ul>
			<div id="tabs-1" style="font-size:12px;">
				这是默认页
				<div class="demo">
					<button onclick="$.each('north,west,east'.split(','), function(){myLayout.close(this);})">Close All Panes</button>
					&nbsp;
					<button onclick="$.each('north,west,east'.split(','), function(){myLayout.toggle(this);})">Toggle All Panes</button>
				</div>
			</div>
		</div>
	</div>
	<div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
		<div class="demo">
			<button onclick="myLayout.close('west')">隐藏</button>
		</div>
		<%@include file="/menu.jsp" %>
		<div id="jQueryUICssSwitch"></div>
	</div>
</body>
</html>