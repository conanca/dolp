<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function() {
	$("#west-grid").jqGrid({
		height: "auto",
		autowidth: true,
		treeGrid: true,
		treeGridModel: 'nested',
		pager: false,
		ExpandColumn: "menu",
		ExpandColClick: true,
		treeIcons: {leaf:'ui-icon-document-b'},
		datatype: "json",
		rowNum: 200,
		caption: "",
		url: "system/menu/getGridData",
		colNames: ["id","功能菜单","url","description"],
		colModel: [
			{name: "id",width:0,hidden:true,key:true},
			{name: "menu", width:150, resizable: false, sortable:false},
			{name: "url",width:0,hidden:true},
			{name: "description",width:0,hidden:true}
		],
		onSelectRow: function(rowid) {
			var treedata = $("#west-grid").getRowData(rowid);
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
		},
	    loadComplete: function(){
			$.addMessage($("#west-grid").getGridParam("userData"));
		}
	});
});
</script>
<table id="west-grid"></table>