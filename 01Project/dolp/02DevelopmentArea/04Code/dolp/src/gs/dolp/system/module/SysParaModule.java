package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.domain.SysPara;
import gs.dolp.system.service.SysParaService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("sysParaModule")
@At("/system/sysPara")
public class SysParaModule {
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
