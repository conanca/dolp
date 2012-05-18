package com.dolplay.dolpinhotel.management;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/management/customer")
public class CustomerModule {

	@Inject
	private CustomerService customerService;

	@At("/getGridDataByRoomOccId/*")
	public ResponseData getGridDataByRoomOccId(int roomOccId, @Param("..") JqgridReqData jqReq) {
		return customerService.getGridDataByRoomOccId(roomOccId, jqReq);
	}
}
