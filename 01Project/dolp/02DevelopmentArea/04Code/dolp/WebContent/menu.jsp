<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
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
	$('#treeList').treeList('closeNode', $('#treeList').find('li'));
});
</script>
<div>
	<input id="expandCollapse" type="button" value="展开"/>
	<ul id="treeList">
		<li class="ui-treeList-open">菜单
			<ul>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab1' , '页面1');$('#newtab1','#tabs').load('3.jsp');">演示页面</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab2' , '页面2');$('#newtab2','#tabs').load('2.jsp');">打开页面2</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab3' , '用户列表页面');$('#newtab3','#tabs').load('system/user_list.jsp');">用户列表页面</a></li>
				<li><a href="javascript:void(null)" onclick="maintab.tabs( 'add' , '#newtab4' , '用户添加页面');$('#newtab4','#tabs').load('system/user_info.jsp');">用户添加页面</a></li>
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