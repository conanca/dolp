<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Dolp Main Page</title>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.4.custom.min.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.layout.min-1.2.0.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<script src="js/i18n/messages_cn.js" type="text/javascript"></script>
<script src="js/jquery.pnotify.min.js" type="text/javascript"></script>
<script src="js/system.common.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css"/>
<link href="css/themes/flick/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" id="jQueryUICssSrc"/>
<link href="css/jquery.pnotify.default.css" rel="stylesheet" type="text/css"/>
<link href="css/ui.multiselect.css" rel="stylesheet" type="text/css"/>
<link href="css/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
var maintab;
$(function() {
	var myLayout = $("body").layout( { applyDefaultStyles: true } );
	myLayout.sizePane("north", 100);

	$("#UpPane").load("header.jsp");
	$("#LeftPane").load("menu.jsp");
	$("#tabs-home").load("home.jsp");

	//在menu.jsp中触发maintab的add事件时，需要激活这个新增的tab
	maintab =$('#tabs', '#RightPane').tabs({
        add: function(e, ui) {
            // append close thingy
            $(ui.tab).parents('li:first')
                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="Close Tab"></span>')
                .find('span.ui-tabs-close')
                .click(function() {
                    maintab.tabs( 'remove', $('li', maintab).index($(this).parents('li:first')[0] ) );
                } );
            // select just added tab
            maintab.tabs('select', '#' + ui.panel.id);
        }
    });
});
</script>
</head>
<body>
	<div id="UpPane" class="ui-layout-north ui-widget ui-widget-content" >
	</div>
	<div id="RightPane" class="ui-layout-center ui-helper-reset ui-widget-content" >
		<div id="tabs">
			<ul>
				<li><a href="#tabs-home">默认页</a></li>
			</ul>
			<div id="tabs-home" style="font-size:12px;">
			</div>
		</div>
	</div>
	<div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
	</div>
</body>
</html>