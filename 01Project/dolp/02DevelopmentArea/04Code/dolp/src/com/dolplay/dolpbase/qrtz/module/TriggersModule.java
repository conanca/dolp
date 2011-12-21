package com.dolplay.dolpbase.qrtz.module;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.qrtz.domain.Triggers;
import com.dolplay.dolpbase.qrtz.service.TriggersService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/qrtz/triggers")
public class TriggersModule {
	
	@Inject
	private TriggersService triggersService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Triggers triggersSearch) {
		return triggersService.getGridData(jqReq, isSearch, triggersSearch);
	}
}