package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.SysPara;
import com.dolplay.dolpbase.system.service.SysParaService;

@IocBean
@At("/system/sysPara")
public class SysParaModule {

	@Inject
	private SysParaService sysParaService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return sysParaService.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") SysPara sysPara) {
		return sysParaService.CUDSysPara(oper, ids, sysPara);
	}
}