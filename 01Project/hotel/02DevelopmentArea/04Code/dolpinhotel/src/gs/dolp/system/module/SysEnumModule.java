package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.service.SysEnumItemService;
import gs.dolp.system.service.SysEnumService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("sysEnumModule")
@At("/system/sysEnum")
public class SysEnumModule {

	private SysEnumService sysEnumService;
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
	public ResponseData editSysEnum(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		return sysEnumService.CUDSysEnum(oper, id, name, description);
	}

	@At("/editSysEnumItem/*")
	public ResponseData editSysEnumItem(int sysEnumID, @Param("oper") String oper, @Param("id") String id,
			@Param("text") String text, @Param("value") String value) {
		return sysEnumItemService.CUDSysEnumItem(oper, id, text, value, sysEnumID);
	}

	@At("/getSysEnumItemMap/*")
	public ResponseData getSysEnumItemMap(String sysEnumName) {
		return sysEnumItemService.getSysEnumItemMap(sysEnumName);
	}
}
