package com.dolplay.dolpbase.qrtz.module;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.qrtz.domain.SimpleTriggers;
import com.dolplay.dolpbase.qrtz.service.SimpleTriggersService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/qrtz/simpleTriggers")
public class SimpleTriggersModule {
	
	@Inject
	private SimpleTriggersService simpleTriggersService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") SimpleTriggers simpleTriggersSearch) {
		return simpleTriggersService.getGridData(jqReq, isSearch, simpleTriggersSearch);
	}
}