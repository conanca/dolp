package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
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
	public AdvancedJqgridResData<SysPara> getGridData(@Param("..") JqgridReqData jqReq) {
		return sysParaService.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("value") String value, @Param("description") String description) {
		return sysParaService.CUDSysPara(oper, id, name, value, description);
	}
}
