package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridStandardData;
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
	public JqgridStandardData getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return menuService.getGridData();
	}

	@At
	@Fail("json")
	public void save(@Param("..") Menu menu) {
		if (menu.getId() == 0) {
			menuService.dao().insert(menu);
		} else {
			menuService.dao().update(menu);
		}
	}

	@At
	@Fail("json")
	public void deleteRow(@Param("id") String ids) {
		menuService.deleteMenus(ids);
	}
}
