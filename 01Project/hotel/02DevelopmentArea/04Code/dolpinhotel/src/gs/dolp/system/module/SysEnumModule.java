package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;
import gs.dolp.system.service.SysEnumItemService;
import gs.dolp.system.service.SysEnumService;

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
	public JqgridData<SysEnum> getSysEnum(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return sysEnumService.getGridData(page, rows, sidx, sord);
	}

	@At
	@Ok("json")
	public JqgridData<SysEnumItem> getSysEnumItem(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord, @Param("sysEnumId") int SysEnumId) {
		return sysEnumItemService.getGridData(page, rows, sidx, sord, SysEnumId);
	}

	@At
	@Ok("void")
	@Fail("json")
	public void editSysEnum(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		sysEnumService.CUDSysEnum(oper, id, name, description);
	}

	@At
	@Ok("void")
	@Fail("json")
	public void editSysEnumItem(@Param("oper") String oper, @Param("id") String id, @Param("text") String text,
			@Param("value") String value, @Param("sysEnumID") int sysEnumID) {
		sysEnumItemService.CUDSysEnumItem(oper, id, text, value, sysEnumID);
	}
}
