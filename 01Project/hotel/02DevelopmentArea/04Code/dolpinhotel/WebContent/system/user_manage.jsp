<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();

	//初始化性别下拉列表框
	var genders = $.getSysEmnuItem("gender");
	var genders1 = $.swapJSON(genders); 
	$("#user_manage_gender").addItems(genders);

	//初始化用户信息界面
	$("#userInfo").dialog({ width: 500 , hide: 'slide' , modal: true , autoOpen: false , close: function(event, ui) {
			$("#userInfoId").attr("value",'');	//清空隐藏域的值
			$('#userInfoForm')[0].reset();	//清空表单的值
		}
	});

	jQuery.validator.addMethod("numberIsDuplicate", function(value) {
		var url = "system/user/userNumberIsDuplicate/"+value;
		var userNumberIsDuplicate = $.myGetJSON(url);
		if(userNumberIsDuplicate == true)
		{
			return false;
		} else {
			return true;
		}
	},"系统中已存在相同的用户编号！");

	// 验证
	$("#userInfoForm").validate({
		rules: {
			number: {
				required: true,
				number: true,
				minlength: 4,
				maxlength: 4,
				numberIsDuplicate :true
			},
			name: {
				required: true,
				maxlength: 10
			},
			password: {
				required: true,
				minlength: 5
			},
			password_again: {
				required: true,
				minlength: 5,
				equalTo: "#password1"
			},
			gender: "required",
			age: {
				digits: true,
				maxlength: 3
			},
			birthday: {
				date: true
			}
		}
	});

	//点击保存时提交
	var options = {
		    success:	showResponse,
			url:		'system/user/save',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#userInfoForm').submit(function() {
		if($("#userInfoForm").valid()){
			$(this).ajaxSubmit(options);
			//关闭用户信息界面
			$("#userInfo").dialog( "close" );
			//刷新表格
			$('#userInfoList').trigger("reloadGrid");
		}else{
			$.addMessageStr(null,"未通过验证",null);
		}
		return false;
	});

	//点击取消时关闭对话框
	$("#userInfocancel").click(function() {
		$("#userInfo").dialog( "close" );
	});

	//设置Grid
	$("#userInfoList").jqGrid({
		rowNum:10,
	   	rowList:[10,20,30],
	   	autowidth: true,
	   	height: "100%",
	    datatype: "json",
	    jsonReader:{
	   		repeatitems: false
        },
	    viewrecords: true,
        caption: "用户列表",
	   	url:'system/user/getGridData',
	   	editurl: "system/user/deleteRow",
	   	colNames:['id','登录号', '姓名','性别','年龄','生日','电话号码'],
	   	colModel:[
	   		{name:'id',index:'id', width:0,hidden:true},
	   		{name:'number',index:'number', width:90},
	   		{name:'name',index:'name', width:100},
	   		{name:'gender',index:'gender', width:80,resizable:false, edittype:'select', formatter:'select', editoptions:{value:genders1}},//不可调整宽度
	   		{name:'age',index:'age', width:80, editable:true},
	   		{name:'birthday',index:'birthday', width:80},
	   		{name:'phone',index:'phone', width:150}
	   	],
	   	pager: '#userInfoPager',
	   	sortname: 'number',
	    sortorder: "asc",
	    multiselect: true,
	    loadComplete: function(){
    	    var userData = $("#userInfoList").getUserData();
			$.addMessage(userData);
    	}
	});
	//不显示jqgrid自带的增删改查按钮
	$("#userInfoList").navGrid('#userInfoPager',{edit:false,add:false,del:false,search:false});
	//添加自定义按钮——添加、编辑和删除
	$("#userInfoList").navButtonAdd('#userInfoPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#userInfo").dialog( "open" );
			var url = "system/user/getNewUserNumber";
			var newUserNumber = $.myGetJSON(url);
			$("#userInfoNumber").val(newUserNumber);
		}
	});
	$("#userInfoList").navButtonAdd('#userInfoPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = $("#userInfoList").getGridParam('selrow');
			if (id) {
				$("#userInfoList").GridToForm(id,"#userInfoForm");
				$("#userInfo").dialog( "open" );
			} else {
				$.addMessageStr(null,"请选择要编辑的记录",null);
			}
		}
	});
	$("#userInfoList").navButtonAdd('#userInfoPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var gr = $("#userInfoList").getGridParam('selarrrow');
			if( gr != null && gr != ''){
				$("#userInfoList").delGridRow(gr,{reloadAfterSubmit:true,
	                afterSubmit: function(xhr, postdata) {
	                    $.addMessage($.parseJSON(xhr.responseText).userdata);
	                    return [true];
                }});
			}
			else{
				$.addMessageStr(null,"请选择要删除的记录",null);
			}
		}
	});
	//查询按钮点击事件
	$("#userInfo_search_btn").click(function () { 
		var number = $("#userInfo_search_number").val();
		var name = $("#userInfo_search_name").val();
		url = "system/user/getGridData?number="+number+"&name="+name;
		$("#userInfoList").setGridParam({url:url, page:1}).trigger("reloadGrid");
    });
});
</script>

<fieldset>
<legend>用户查询</legend>
<table>
	<tr>
		<td>
			用户编号：
		</td>
		<td>
			<input type="text" name="number" id="userInfo_search_number"/>
		</td>
		<td>
			用户姓名：
		</td>
		<td>
			<input type="text" name="name" id="userInfo_search_name"/>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="right">
			<input type="button" id="userInfo_search_btn" value="查询"/>
		</td>
	</tr>
</table>
</fieldset>

<table id="userInfoList"></table>
<div id="userInfoPager"></div>

<div id="userInfo" title="用户信息">
	<form id="userInfoForm" action="#">
	<table>
		<tr>
			<td>
				用户编号：
			</td>
			<td>
				<input type="hidden" name="id" id="userInfoId"/>
				<input type="text" name="number" id="userInfoNumber"/>
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
				<input type="password" name="password" id="password1"/>
			</td>
			<td>
				再次输入密码：
			</td>
			<td>
				<input type="password" name="password_again"/>
			</td>
		</tr>
			<tr>
			<td>
				性别：
			</td>
			<td>
				<select name="gender" id="user_manage_gender">
					<option value="">请选择</option>
				</select>
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