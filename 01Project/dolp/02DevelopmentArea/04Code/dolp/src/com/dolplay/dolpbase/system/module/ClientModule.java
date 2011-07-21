package com.dolplay.dolpbase.system.module;


import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.service.ClientService;

@InjectName("clientModule")
@At("/system/client")
public class ClientModule {

	private ClientService clientService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return clientService.getGridData(jqReq);
	}

	@At
	public ResponseData kickOff(@Param("sessionIds[]") String[] sessionIds) {
		return clientService.kickOff(sessionIds);
	}
}
