<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<style type="text/css" media="all">
html, body {
	margin: 0;			/* Remove body margin/padding */
	padding: 0;
	overflow: hidden;	/* Remove scroll bars on browser window */	
	font-size: 80%;
}
#jQueryUICssSwitch {width:150px;}

#LeftPane {
	/* optional, initial splitbar position */
	overflow: auto;
}
/*
 * Right-side element of the splitter.
*/

#RightPane {
	padding: 2px;
	overflow: auto;
}

.ui-tabs-nav li {position: relative;}
.ui-tabs-selected a span {padding-right: 10px;}
.ui-tabs-close {display: none;position: absolute;top: 3px;right: 0px;z-index: 800;width: 16px;height: 14px;font-size: 10px; font-style: normal;cursor: pointer;}
.ui-tabs-selected .ui-tabs-close {display: block;}
.ui-layout-west .ui-jqgrid tr.jqgrow td { border-bottom: 0px none;}

</style>
<script type="text/javascript">
var maintab;
$(function() {
	$("input:button,input:submit,input:reset").button();

	var myLayout = $('body').layout({ applyDefaultStyles: true });
	myLayout.sizePane("north", 100);

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
});
</script>
</head>
<body>
	<div id="UpPane" class="ui-layout-north ui-widget ui-widget-content" >
		<%@include file="/header.jsp" %>
	</div>
	<div id="RightPane" class="ui-layout-center ui-helper-reset ui-widget-content" >
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">默认页</a></li>
			</ul>
			<div id="tabs-1" style="font-size:12px;">
				这是默认页
			</div>
		</div>
	</div>
	<div id="LeftPane" class="ui-layout-west ui-widget ui-widget-content">
		<%@include file="/menu.jsp" %>
		<div id="jQueryUICssSwitch"></div>
	</div>
</body>
</html>