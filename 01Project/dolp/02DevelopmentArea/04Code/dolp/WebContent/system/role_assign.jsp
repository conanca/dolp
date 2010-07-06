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

});
</script>

<div>
<select id="roleIds" class="multiselect" multiple="multiple" name="roleIds">
  <option value="1">角色1</option>
  <option value="2" selected="selected">角色2</option>
  <option value="3">角色3</option>
  <option value="4">角色4</option>
  <option value="5">角色5</option>
  <option value="6">角色6</option>
  <option value="7">角色7</option>
  <option value="8">角色8</option>
  <option value="9" selected="selected">角色9</option>
</select>
</div>