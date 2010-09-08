<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){

	jQuery("#menuList").jqGrid({
	   	url:'system/menu/getGridData',
	   	datatype: "json",
	   	colNames:['id','name','url','description','level'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'name',index:'name', width:60},
	   		{name:'url',index:'url', width:200},
	   		{name:'description',index:'description', width:150},	//设置弹出窗口中字段类型
	   		{name:'level',index:'level', width:0}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	   	pager: '#menuPager',
	   	sortname: 'id',
	    sortorder: "asc",
	    viewrecords: true,
	    ExpandColumn: "name",
	    treeGrid: true,
	    treeGridModel: 'adjacency',
	    treeIcons: {leaf:'ui-icon-document-b'},
	    ExpandColClick: true,
	    caption:"菜单列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#menuList").jqGrid('navGrid','#menuPager',{edit:false,add:false,del:false,search:false});
	jQuery("#menuList").jqGrid('hideCol',['id','level']);//隐藏id列
	jQuery("#menuList").jqGrid('navButtonAdd','#menuPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#menuInfo").dialog( "open" );
			var id = jQuery("#menuList").jqGrid('getGridParam','selrow');
			var level = 0;
			if(id!=null){
				var ret = jQuery("#menuList").jqGrid('getRowData',id);
				level = ret.level;
			}
			$("#menuInfoParentId").val(id);
			$("#menuInfoLevel").val(ret.level+1);
		}
	});
	jQuery("#menuList").jqGrid('navButtonAdd','#menuPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = jQuery("#menuList").jqGrid('getGridParam','selrow');
			if (id) {
				jQuery("#menuList").jqGrid('GridToForm',id,"#menuInfoForm");
				$("#menuInfo").dialog( "open" );
			} else {
				alert("请选择要编辑的记录");
			}
		}
	});
	jQuery("#menuList").jqGrid('navButtonAdd','#menuPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var gr = jQuery("#menuList").jqGrid('getGridParam','selarrrow');
			if( gr != null ){
				jQuery("#menuList").jqGrid('delGridRow',gr,{reloadAfterSubmit:true});
			}
			else{
				alert("请选择要删除的记录");
			}
		}
	});
	
	//初始化菜单信息界面
	$("#menuInfo").dialog({width: 580, hide: 'slide' , autoOpen: false,close: function(event, ui) {
			$("#menuInfoId").attr("value",'');	//清空隐藏域的值
			$('#menuInfoForm')[0].reset();	//清空表单的值
		}
	});
	
	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'system/menu/save',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#menuInfoForm').submit(function() {
		$(this).ajaxSubmit(options);
		//关闭菜单信息界面
		$("#menuInfo").dialog( "close" );
		//刷新表格
		$('#menuList').trigger("reloadGrid");
		return false;
	});
	//设置按钮图标——————未起作用
	$("#menuInfocancel").button( "option", "icons", {primary:'ui-icon-cancel',secondary:'ui-icon-cancel'} );
	$("#menuInfocancel").click(function() {
		$("#menuInfo").dialog( "close" );
	});
});
//提交前
function showRequest(formData, jqForm, options) {
	
}
//提交后获得Respons后
function showResponse(responseText, statusText, xhr, $form)  {
	alert('保存成功');
}
</script>
<table id="menuList"></table>
<div id="menuPager"></div>
<div id="menuInfo">
	<form id="menuInfoForm">
		<table>
			<tr>
				<td>
					菜单名称：
				</td>
				<td>
					<input type="hidden" name="id" id="menuInfoId"/>
					<input type="hidden" name="parentId" id="menuInfoParentId"/>
					<input type="hidden" name="level" id="menuInfoLevel"/>
					<input type="text" name="name"/>
				</td>
			</tr>
			<tr>
				<td>
					页面地址：
				</td>
				<td>
					<input type="text" name="url"/>
				</td>
			</tr>
			<tr>
				<td>
					菜单描述：
				</td>
				<td>
					<textarea name="description"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="保存"/>
					<input type="reset" value="重置"/>
					<input id="menuInfocancel" type="button" value="取消"/>
				</td>
			</tr>
		</table>
	</form>
</div>