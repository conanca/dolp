<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/jquery.selectsubcategory.js" type="text/javascript"></script>
<script src="js/jquery.json-2.2.min.js" type="text/javascript"></script>
<script type="text/javascript">

var customerListData = new Array();

$(function(){
	$(".datepicker").datepicker();
	$("input:button,input:submit,input:reset").button();

	//初始化性别下拉列表框
	var genders = $.getSysEmnuItem("gender");
	var genders1 = $.swapJSON(genders); 
	$("#customer_manage_gender").addItems(genders);
	//初始化证件类型下拉列表框
	var certificateTypes = $.getSysEmnuItem("certificateType");
	$("#certificateType").addItems(certificateTypes);
	var certificateTypes1 = $.swapJSON(certificateTypes);

	$("#customerList").jqGrid({
		rowNum:10,
		rowList:[10,20,30],
		autowidth: true,
		height: "100%", //自动调整高度(无滚动条)
		datatype: "local",
		caption:"顾客信息列表",
		colNames:['序号','姓名','性别','证件类型','证件号码','籍贯地址'],
		colModel:[
			{name:'no',index:'no', width:80},
			{name:'name',index:'name', width:80},
			{name:'gender',index:'gender', width:80, edittype:'select', formatter:'select', editoptions:{value:genders1}},
			{name:'certificateType',index:'certificateType', width:120, editable:true, edittype:'select', formatter:'select', editoptions:{value:certificateTypes1}},
			{name:'credentialNumber',index:'credentialNumber', width:150},
			{name:'address',index:'address', width:300}
		],
		pager: '#customerPager'
	});
	//不显示查询按钮
	$("#customerList").navGrid('#customerPager',{edit:false,add:false,del:false,search:false});
	
	$("#customerList").navButtonAdd('#customerPager',{caption:"添加",buttonicon:"ui-icon-plus",
		onClickButton:function(){
			$("#customerDiv").dialog( "open" );
		}
	});
	$("#customerList").navButtonAdd('#customerPager',{caption:"编辑",buttonicon:"ui-icon-pencil",
		onClickButton:function(){
			var id = $("#customerList").getGridParam('selrow');
			if (id) {
				$("#customerList").GridToForm(id,"#customerForm");
				$("#customerDiv").dialog( "open" );
			} else {
				$.addMessageStr(null,"请选择要编辑的记录",null);
			}
		}
	});
	$("#customerList").navButtonAdd('#customerPager',{caption:"删除",buttonicon:"ui-icon-trash",position:"last",
		onClickButton:function(){
			var fx = $("#customerList").getGridParam('selrow')-1;
			if (fx >= 0) {
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
				$("#customerList").clearGridData();
				for(var i=0;i<=customerListData.length;i++){
					$("#customerList").addRowData(i+1,customerListData[i]);
				}
			} else {
				$.addMessageStr(null,"请选择要删除的记录",null);
			}
		}
	});

	//初始化用户信息界面
	$("#customerDiv").dialog({width: 500, hide: 'slide' , modal: true , autoOpen: false,close: function(event, ui) {
			$("#customerDivId").attr("value",'');	//清空隐藏域的值
			$('#customerForm')[0].reset();	//清空表单的值
		}
	});
	
	$("#customerDivCancel").click(function() {
		$("#customerDiv").dialog( "close" );
	});

	// 验证
	$("#customerForm").validate({
		rules: {
			name: {
				required: true,
				maxlength: 10
			},
			credentialNumber: {
				required: true,
				maxlength: 20
			},
			enterDate: {
				required: true,
				date: true
			},
			expectedCheckOutDate: {
				date: true
			}
		}
	});

	$("#customerDivAdd").click(function() {
		if(!$("#customerForm").valid()){
			$.addMessageStr(null,"未通过验证",null);
			return false;
		}
		var arrLength = customerListData.length;
		var currCustomer = $('#customerForm').serializeObject();
		if(currCustomer.no==null||currCustomer.no==''){
			currCustomer.no=arrLength+1;
			customerListData[arrLength]=currCustomer;
			$("#customerList").addRowData(arrLength+1,currCustomer);
		}else{
			customerListData[currCustomer.no-1]=currCustomer;
			$("#customerList").setRowData(currCustomer.no,currCustomer);
		}
		$("#customerNo").attr("value",'');	//清空隐藏域的值
		$('#customerForm')[0].reset();	//清空表单的值
		//关闭用户信息界面
		$("#customerDiv").dialog( "close" );
		return false;
	});

	// 通过插件jquery.selectsubcategory.js实现下拉框二级联动
	var url3 = "dolpinhotel/setup/roomtype/getAllRoomTypes";
	$("#roomTypeSelector").addItems($.getItem(url3));
	$("#roomTypeSelector").selectSubcategory({
		url: 'dolpinhotel/setup/room/getAllAvailableRoomForSelectOption',
		subcategoryid: 'roomSelector'
	});

	// 验证
	$("#roomOccupancyForm").validate({
		rules: {
			roomType: {
				required: true
			},
			roomId: {
				required: true
			},
			enterDate: {
				required: true,
				date: true
			},
			expectedCheckOutDate: {
				date: true
			}
		}
	});
	
	$("#roomcheckin").click(function() {
		if(!$("#roomOccupancyForm").valid()){
			$.addMessageStr(null,"未通过验证",null);
			return false;
		}
		var url='dolpinhotel/management/roomoccupancy/saveRoomOccupancy';
		var enterDate=$('input:text[name=enterDate]').val();
		var expectedCheckOutDate=$('input:text[name=expectedCheckOutDate]').val();
		var roomId=$('select[name=roomId]').val();
		// 通过jquery.json-2.2.min.js插件实现将customers数组对象转换成json格式
		var customers =$.toJSON(customerListData);
		$.post(url, { customers: customers, enterDate: enterDate, expectedCheckOutDate: expectedCheckOutDate, roomId: roomId },
			function(data) {
			  $.addMessageStr("登记成功",null,null);
			  $('#roomOccupancyForm')[0].reset();	//清空表单的值
			  $("#customerList").clearGridData();	//清空grid的值
			  $('#roomSelector').find('option').remove().end();	//移除房间号选择框的所有option
			  $("#roomSelector").append(new Option("请先选择房间类型",""));
			  customerListData = new Array();	//初始化customerListData
			}
		);
	});
});
</script>
<div id="roomOccupancyDiv" title="房间登记情况">
	<form id="roomOccupancyForm" action="#">
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
						<option value="">请选择</option>
					</select>
				</td>
				<td>
					房间号：
				</td>
				<td>
					<select name="roomId" id="roomSelector">
						<option value="">请先选择房间类型</option>
					</select>
				</td>
			</tr>
		</table>
		
		<table id="customerList"></table>
		<div id="customerPager"></div>
		<div>
			<input type="button" value="登记" id="roomcheckin"/>
			<input type="reset" value="重置"/>
		</div>
	</form>
</div>

<div id="customerDiv" title="顾客信息">
	<form id="customerForm" action="#" method="post">
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
					<select name="gender" id="customer_manage_gender">
						<option value="">请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					证件类型：
				</td>
				<td>
					<select name="certificateType" id="certificateType">
						<option value="">请选择</option>
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
					<input type="text" name="address" size="60"/>
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