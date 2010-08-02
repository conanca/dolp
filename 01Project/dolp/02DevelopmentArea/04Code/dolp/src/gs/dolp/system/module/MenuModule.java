package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.service.MenuService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("menuModule")
@At("/system/menu")
@Fail("json")
public class MenuModule {

	private MenuService menuService;

	@At
	@Ok("json")
	public JqgridData<Menu> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return menuService.getGridData(page, rows, sidx, sord, 0, 0);
	}
}
