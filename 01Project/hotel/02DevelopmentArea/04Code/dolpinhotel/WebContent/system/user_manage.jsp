<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();
	
	jQuery("#userInfoList").jqGrid({
	   	url:'system/user/getGridData',
		datatype: "json",
	   	colNames:['id','登录号', '姓名','性别','年龄','生日','电话号码'],
	   	colModel:[
	   		{name:'id',index:'id', width:0},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'gender',index:'gender', width:80,resizable:false},//不可调整宽度
	   		{name:'age',index:'age', width:80},
	   		{name:'birthday',index:'birthday', width:80},
	   		{name:'phone',index:'phone', width:150}
	   	],
	   	rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%", //自动调整高度(无滚动条)
	    jsonReader:{
	   		repeatitems: false
        },
	   	pager: '#userInfoPager',
	   	sortname: 'number',
	    sortorder: "asc",
	    viewrecords: true,
	    editurl: "system/user/deleteRow",	//del:true
	    multiselect: true, //checkbox
	    caption: "用户列表"
	});
	//不显示jqgrid自带的增删改查按钮
	jQuery("#userInfoList").jqGrid('navGrid','#userInfoPager',{edit:false,add:false,del:false,search:false});
	jQuery("#userInfoList").jqGrid('hideCol',['id']);//隐藏id列
	jQuery("#userInfoList").jqGrid('navButtonAdd','#userInfoPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#userInfo").dialog( "open" );
		}
	});
	jQuery("#userInfoList").jqGrid('navButtonAdd','#userInfoPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = jQuery("#userInfoList").jqGrid('getGridParam','selrow');
			if (id) {
				jQuery("#userInfoList").jqGrid('GridToForm',id,"#userInfoForm");
				$("#userInfo").dialog( "open" );
			} else {
				alert("请选择要编辑的记录");
			}
		}
	});
	jQuery("#userInfoList").jqGrid('navButtonAdd','#userInfoPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var gr = jQuery("#userInfoList").jqGrid('getGridParam','selarrrow');
			if( gr != null && gr != ''){
				jQuery("#userInfoList").jqGrid('delGridRow',gr,{reloadAfterSubmit:true});
			}
			else{
				alert("请选择要删除的记录");
			}
		}
	});
	
	//初始化用户信息界面
	$("#userInfo").dialog({width: 500, hide: 'slide' , autoOpen: false,close: function(event, ui) {
			$("#userInfoId").attr("value",'');	//清空隐藏域的值
			$('#userInfoForm')[0].reset();	//清空表单的值
		}
	});
	
	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'system/user/save',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#userInfoForm').submit(function() {
		$(this).ajaxSubmit(options);
		//关闭用户信息界面
		$("#userInfo").dialog( "close" );
		//刷新表格
		$('#userInfoList').trigger("reloadGrid");
		return false;
	});
	//设置按钮图标——————未起作用
	$("#userInfocancel").button( "option", "icons", {primary:'ui-icon-cancel',secondary:'ui-icon-cancel'} );
	$("#userInfocancel").click(function() {
		$("#userInfo").dialog( "close" );
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
<table id="userInfoList"></table>
<div id="userInfoPager"></div>

<div id="userInfo" title="用户信息">
	<form id="userInfoForm">
	<table>
		<tr>
			<td>
				用户编号：
			</td>
			<td>
				<input type="hidden" name="id" id="userInfoId"/>
				<input type="text" name="number"/>
			</td>
			<td>
				用户姓名：
			</td>
			<td>
				<input type="text" name="name"/>
			</td>
		</tr>
		<tr>
			<td>
				登录密码：
			</td>
			<td>
				<input type="password" name="password"/>
			</td>
			<td>
				再次输入密码：
			</td>
			<td>
				<input type="password"/>
			</td>
		</tr>
			<tr>
			<td>
				性别：
			</td>
			<td>
				男:<input type="radio" name="gender" value="男"/>
				女:<input type="radio" name="gender" value="女"/>
			</td>
			<td>
				年龄：
			</td>
			<td>
				<input type="text" name="age"/>
			</td>
		</tr>
		<tr>
			<td>
				出生日期：
			</td>
			<td>
				<input type="text" name="birthday" class="datepicker"/>
			</td>
			<td>
				电话号码：
			</td>
			<td>
				<input type="text" name="phone"/>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="保存"/>
				<input type="reset" value="重置"/>
				<input id="userInfocancel" type="button" value="取消"/>
			</td>
		</tr>
	</table>
	</form>
</div>
