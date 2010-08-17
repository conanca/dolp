<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="js/jquery.selectsubcategory.js" type="text/javascript"></script>
<link href="css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">

var customerListData = new Array();

$.fn.serializeObject = function()
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();

	jQuery("#customerList").jqGrid({
		datatype: "local",
		colNames:['序号','姓名','性别','证件类型','证件号码','籍贯地址'],
		colModel:[
			{name:'no',index:'no', width:20},
			{name:'name',index:'name', width:60},
			{name:'gender',index:'gender', width:50},
			{name:'certificateType',index:'certificateType', width:60},
			{name:'credentialNumber',index:'credentialNumber', width:150},
			{name:'address',index:'address', width:300}
		],
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		height: "100%", //自动调整高度(无滚动条)
		pager: '#customerPager',
		caption:"顾客信息列表"
	});
	//不显示查询按钮
	jQuery("#customerList").jqGrid('navGrid','#customerPager',{edit:false,add:false,del:false,search:false});
	
	jQuery("#customerList").jqGrid('navButtonAdd','#customerPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#customerDiv").dialog( "open" );
		}
	});
	jQuery("#customerList").jqGrid('navButtonAdd','#customerPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = jQuery("#customerList").jqGrid('getGridParam','selrow');
			if (id) {
				jQuery("#customerList").jqGrid('GridToForm',id,"#customerForm");
				$("#customerDiv").dialog( "open" );
			} else {
				alert("请选择要编辑的记录");
			}
		}
	});
	jQuery("#customerList").jqGrid('navButtonAdd','#customerPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var fx = jQuery("#customerList").jqGrid('getGridParam','selrow')-1;
			if (fx) {
				for(var i=0,n=0,flag=0;i<customerListData.length;i++)
				{
					if(customerListData[i]!=customerListData[fx])
					{
						customerListData[n]=customerListData[i];
						if(flag==1){
							customerListData[n].no-=1;
						}
						n++;
					}else{
						flag=1;
					}
				}
				customerListData.length-=1;
				jQuery("#customerList").jqGrid('clearGridData');
				for(var i=0;i<=customerListData.length;i++){
					jQuery("#customerList").jqGrid('addRowData',i+1,customerListData[i]);
				}
			} else {
				alert("请选择要删除的记录");
			}
		}
	});
	
	//初始化用户信息界面
	$("#customerDiv").dialog({width: 580, hide: 'slide' , autoOpen: false,close: function(event, ui) {
			$("#customerDivId").attr("value",'');	//清空隐藏域的值
			$('#customerForm')[0].reset();	//清空表单的值
		}
	});
	
	//设置按钮图标——————未起作用
	$("#customerDivCancel").button( "option", "icons", {primary:'ui-icon-cancel',secondary:'ui-icon-cancel'} );
	$("#customerDivCancel").click(function() {
		$("#customerDiv").dialog( "close" );
	});

	$("#customerDivAdd").click(function() {
		var arrLength = customerListData.length;
		var currCustomer = $('#customerForm').serializeObject();
		if(currCustomer.no==null||currCustomer.no==''){
			currCustomer.no=arrLength+1;
			customerListData[arrLength]=currCustomer;
			jQuery("#customerList").jqGrid('addRowData',arrLength+1,currCustomer);
		}else{
			customerListData[currCustomer.no-1]=currCustomer;
			jQuery("#customerList").jqGrid('setRowData',currCustomer.no,currCustomer);
		}
		$("#customerNo").attr("value",'');	//清空隐藏域的值
		$('#customerForm')[0].reset();	//清空表单的值
		//关闭用户信息界面
		$("#customerDiv").dialog( "close" );
		return false;
	});

	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes.do";
	$.getJSON(url3,function(allRoomTypes){
		$.each(allRoomTypes,function(value,text) {
			$("#roomTypeSelector").append(new Option(text,value));
		});
	});
	$("#roomTypeSelector").selectSubcategory({
		url: 'dolpinhotel/setup/room/getAllAvailableRoomForSelectOption.do',
		subcategoryid: 'roomSelector'
	});

	var options = {
		    beforeSubmit:showRequest,
		    success:	showResponse,
			url:		'#',
			type:		'post',
			clearForm:	true,
			resetForm:	true
		};
	$('#roomOccupancyForm').submit(function() {
		$(this).ajaxSubmit(options);
		return false;
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
<div id="roomOccupancyDiv" title="房间登记情况">
	<form id="roomOccupancyForm">
		<table>
			<tr>
				<td>
					入住日期：
				</td>
				<td>
					<input type="text" name="enterDate" class="datepicker"/>
				</td>
				<td>
					预离日期：
				</td>
				<td>
					<input type="text" name="expectedCheckOutDate" class="datepicker"/>
				</td>
			</tr>
			<tr>
				<td>
					房间类型：
				</td>
				<td>
					<select name="roomType" id="roomTypeSelector">
						<option value="0">请选择</option>
					</select>
				</td>
				<td>
					房间号：
				</td>
				<td>
					<select name="roomId" id="roomSelector">
						<option value="0">请选择</option>
					</select>
				</td>
			</tr>
		</table>
		
		<table id="customerList"></table>
		<div id="customerPager"></div>
		<div>
			<input type="submit" value="保存"/>
			<input type="reset" value="重置"/>
		</div>
	</form>
</div>


<div id="customerDiv" title="顾客信息">
	<form id="customerForm" action="" method="post">
		<table>
			<tr>
				<td>
					姓名：
				</td>
				<td>
					<input type="hidden" name="no" id="customerNo"/>
					<input type="text" name="name"/>
				</td>
				<td>
					性别：
				</td>
				<td>
					男:<input type="radio" name="gender" value="男"/>
					女:<input type="radio" name="gender" value="女"/>
				</td>
			</tr>
			<tr>
				<td>
					证件类型：
				</td>
				<td>
					<select name="certificateType">
						<option value="身份证">身份证</option>
						<option value="军官证">军官证</option>
						<option value="护照">护照</option>
					</select>
						</td>
				<td>
					证件号码：
				</td>
				<td>
					<input type="text" name="credentialNumber"/>
				</td>
			</tr>
			<tr>
				<td>
					籍贯地址
				</td>
				<td colspan="3">
					<input type="text" name="address"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input id="customerDivAdd" type="button" value="保存"/>
					<input type="reset" value="重置"/>
					<input id="customerDivCancel" type="button" value="取消"/>
				</td>
			</tr>
		</table>
	</form>
</div>