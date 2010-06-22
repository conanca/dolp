<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<link href="css/widgetTreeList.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/widgetTreeList.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("input:button").button();
});
$(function() {
	$('#treeList').treeList();
	$('#treeList').treeList('closeNode', $('#treeList').find('li'));
});
</script>
<div>
	<input type="button" value="Expand All"  onclick="$('#treeList').treeList('openNode', $('#treeList').find('li'));" />
	<input type="button" value="Collapse All"  onclick="$('#treeList').treeList('closeNode', $('#treeList').find('li'));" />
	<ul id="treeList">
		<li class="ui-treeList-open">父节点1
			<ul>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab1' , '页面1');$('#newtab1','#tabs').load('1.jsp');">打开页面1</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab2' , '页面2');$('#newtab2','#tabs').load('2.jsp');">打开页面2</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab3' , '用户列表页面');$('#newtab3','#tabs').load('system/user_list.jsp');">打开页面3</a></li>
				<li>Node b</li>
				<li>Node c</li>
				<li>Node d</li>
			</ul>
		</li>
		<li class="ui-treeList-open">Node 2
			<ul>
				<li>Node a</li>
				<li>Node b</li>
			</ul>
		</li>
	</ul>
</div>