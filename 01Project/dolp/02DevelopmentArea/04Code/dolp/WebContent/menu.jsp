<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<link href="css/widgetTreeList.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/widgetTreeList.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$('#expandCollapse').click(function() {
		if($('#expandCollapse')[0].value=='展开'){
			$('#treeList').treeList('openNode', $('#treeList').find('li'));
			$('#expandCollapse').val('收起');
		}else{
			$('#treeList').treeList('closeNode', $('#treeList').find('li'));
			$('#expandCollapse').val('展开');
		}
	});
	
	$('#treeList').treeList();
	//$('#treeList').treeList('closeNode', $('#treeList').find('li'));
});
</script>
<div>
	<input id="expandCollapse" type="button" value="收起"/>
	<ul id="treeList">
		<li class="ui-treeList-open">系统管理
			<ul>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab1' , '演示页面1');$('#newtab1','#tabs').load('3.jsp');">演示页面</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab3' , '用户管理');$('#newtab3','#tabs').load('system/user_manage.jsp');">用户管理</a></li>
				<li>Node b</li>
				<li>Node c</li>
				<li>Node d</li>
			</ul>
		</li>
		<li class="ui-treeList-open">酒店设置
			<ul>
				<li>Node a</li>
				<li>Node b</li>
			</ul>
		</li>
	</ul>
</div>