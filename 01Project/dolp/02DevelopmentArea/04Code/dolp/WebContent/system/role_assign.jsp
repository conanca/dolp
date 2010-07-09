<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="js/i18n/jquery.localisation-min.js" type="text/javascript"></script>
<script src="js/i18n/ui-multiselect-cn.js" type="text/javascript"></script>
<script src="js/ui.multiselect.js" type="text/javascript"></script>
<link href="css/ui.multiselect.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
$(function(){
	$.localise('ui-multiselect', {language: 'cn', path: 'js/i18n/'});
	// choose either the full version
	$(".multiselect").multiselect();
	// or disable some features
	$(".multiselect").multiselect({sortable: false, searchable: true});

	var url = "system/role/getAllRole.do";
	$.getJSON(url, function (result){
		alert(result);
	});
});
</script>

<div>
<select id="roleIds" class="multiselect" multiple="multiple" name="roleIds">

</select>
</div>