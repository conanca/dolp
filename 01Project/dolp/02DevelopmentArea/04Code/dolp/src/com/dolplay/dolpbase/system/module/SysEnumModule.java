package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.SysEnum;
import com.dolplay.dolpbase.system.domain.SysEnumItem;
import com.dolplay.dolpbase.system.service.SysEnumItemService;
import com.dolplay.dolpbase.system.service.SysEnumService;

@IocBean
@At("/system/sysEnum")
public class SysEnumModule {

	@Inject
	private SysEnumService sysEnumService;
	@Inject
	private SysEnumItemService sysEnumItemService;

	@At
	public ResponseData getSysEnumGridData(@Param("..") JqgridReqData jqReq) {
		return sysEnumService.getGridData(jqReq);
	}

	@At("/getSysEnumItemGridData/*")
	public ResponseData getSysEnumItemGridData(int sysEnumId, @Param("..") JqgridReqData jqReq) {
		return sysEnumItemService.getGridData(jqReq, sysEnumId);
	}

	@At
	public ResponseData editSysEnum(@Param("oper") String oper, @Param("ids") String ids, @Param("..") SysEnum sysEnum) {
		return sysEnumService.CUDSysEnum(oper, ids, sysEnum);
	}

	@At
	public ResponseData editSysEnumItem(@Param("oper") String oper, @Param("ids") String ids,
			@Param("..") SysEnumItem sysEnumItem) {
		return sysEnumItemService.CUDSysEnumItem(oper, ids, sysEnumItem);
	}
}