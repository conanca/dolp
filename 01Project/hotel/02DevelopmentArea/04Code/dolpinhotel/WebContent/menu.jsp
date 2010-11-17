<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
	jQuery("#west-grid").jqGrid({
		url: "tree.xml",
		datatype: "xml",
		height: "auto",
		pager: false,
		colNames: ["id","Items","url"],
		colModel: [
			{name: "id",width:1,hidden:true, key:true},
			{name: "menu", width:150, resizable: false, sortable:false},
			{name: "url",width:1,hidden:true}
		],
		treeGrid: true,
		caption: "功能菜单",
		ExpandColumn: "menu",
		autowidth: true,
		rowNum: 200,
		ExpandColClick: true,
		treeIcons: {leaf:'ui-icon-document-b'},
		onSelectRow: function(rowid) {
			var treedata = $("#west-grid").jqGrid('getRowData',rowid);
			if(treedata.isLeaf=="true") {
				//treedata.url
				var st = "#t"+treedata.id;
				if($(st).html() != null ) {
					maintab.tabs('select',st);
				} else {
					maintab.tabs('add',st, treedata.menu);
					$(st,"#tabs").load(treedata.url);
				}
			}
		}
	});
});
</script>
<table id="west-grid"></table>