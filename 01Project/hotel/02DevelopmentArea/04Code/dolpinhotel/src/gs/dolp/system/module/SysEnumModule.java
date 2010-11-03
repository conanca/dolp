package gs.dolp.system.module;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;
import gs.dolp.system.service.SysEnumItemService;
import gs.dolp.system.service.SysEnumService;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("sysEnumModule")
@At("/system/sysEnum")
@Fail("json")
public class SysEnumModule {

	private SysEnumService sysEnumService;
	private SysEnumItemService sysEnumItemService;

	@At
	@Ok("json")
	public JqgridAdvancedData<SysEnum> getSysEnumGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return sysEnumService.getGridData(page, rows, sidx, sord);
	}

	// 使用REST风格的路径参数。对于所有 Nutz.Mvc 提供的内置适配器，路径参数是最优先的，所以把int sysEnumId放在第一个参数
	@At("/getSysEnumItemGridData/*")
	@Ok("json")
	public JqgridAdvancedData<SysEnumItem> getSysEnumItemGridData(int sysEnumId, @Param("page") String page,
			@Param("rows") String rows, @Param("sidx") String sidx, @Param("sord") String sord) {
		return sysEnumItemService.getGridData(page, rows, sidx, sord, sysEnumId);
	}

	@At
	@Ok("void")
	@Fail("json")
	public void editSysEnum(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		sysEnumService.CUDSysEnum(oper, id, name, description);
	}

	@At("/editSysEnumItem/*")
	@Ok("void")
	@Fail("json")
	public void editSysEnumItem(int sysEnumID, @Param("oper") String oper, @Param("id") String id,
			@Param("text") String text, @Param("value") String value) {
		sysEnumItemService.CUDSysEnumItem(oper, id, text, value, sysEnumID);
	}

	@At("/getSysEnumOption/*")
	@Ok("json")
	public Map<String, String> getSysEnumOption(String sysEnumName) {
		return sysEnumItemService.getSysEnumItem(sysEnumName);
	}
}
