package com.dolplay.dolpbase.qrtz.module;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.qrtz.domain.CronTriggers;
import com.dolplay.dolpbase.qrtz.service.CronTriggersService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/qrtz/cronTriggers")
public class CronTriggersModule {
	
	@Inject
	private CronTriggersService cronTriggersService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") CronTriggers cronTriggersSearch) {
		return cronTriggersService.getGridData(jqReq, isSearch, cronTriggersSearch);
	}
}