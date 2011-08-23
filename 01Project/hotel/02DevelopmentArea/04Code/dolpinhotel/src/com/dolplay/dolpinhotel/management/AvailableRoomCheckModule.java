package com.dolplay.dolpinhotel.management;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/management/availableroomcheck")
public class AvailableRoomCheckModule {

	@Inject
	AvailableRoomCheckService availableRoomCheckService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jq) {
		return availableRoomCheckService.getGridData(jq);
	}
}
