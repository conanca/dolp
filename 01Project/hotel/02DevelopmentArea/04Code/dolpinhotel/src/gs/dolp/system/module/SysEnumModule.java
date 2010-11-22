package gs.dolp.system.module;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseData;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;
import gs.dolp.system.service.SysEnumItemService;
import gs.dolp.system.service.SysEnumService;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("sysEnumModule")
@At("/system/sysEnum")
public class SysEnumModule {

	private SysEnumService sysEnumService;
	private SysEnumItemService sysEnumItemService;

	@At
	public AdvancedJqgridResData<SysEnum> getSysEnumGridData(@Param("..") JqgridReqData jqReq) {
		return sysEnumService.getGridData(jqReq);
	}

	// 使用REST风格的路径参数。对于所有 Nutz.Mvc 提供的内置适配器，路径参数是最优先的，所以把int sysEnumId放在第一个参数
	@At("/getSysEnumItemGridData/*")
	public AdvancedJqgridResData<SysEnumItem> getSysEnumItemGridData(int sysEnumId, @Param("..") JqgridReqData jqReq) {
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

	@At("/getSysEnumOption/*")
	public Map<String, String> getSysEnumOption(String sysEnumName) {
		return sysEnumItemService.getSysEnumItem(sysEnumName);
	}
}
