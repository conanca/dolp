package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Client;
import com.dolplay.dolpbase.system.service.ClientService;

@IocBean
@At("/system/client")
public class ClientModule {

	@Inject
	private ClientService clientService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Client clientSearch, @Param("userName") String userName) {
		return clientService.getGridData(jqReq, isSearch, clientSearch, userName);
	}

	@At
	public ResponseData kickOff(@Param("sessionIds[]") String[] sessionIds) {
		return clientService.kickOff(sessionIds);
	}
}