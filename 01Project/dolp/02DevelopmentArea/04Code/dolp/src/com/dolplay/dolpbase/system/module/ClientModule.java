package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.service.ClientService;

@IocBean
@At("/system/client")
public class ClientModule {

	@Inject
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