package gs.dolp.system.module;

import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.service.MenuService;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("menuModule")
@At("/system/menu")
public class MenuModule {

	private MenuService menuService;

	@At
	public StandardJqgridResData getGridData(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel, HttpServletRequest req) {
		return menuService.getGridData(nodeId, nLeft, nRight, nLevel);
	}

	@At
	public StandardJqgridResData getGridData1(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel, HttpServletRequest req) {
		return menuService.getGridData1(nodeId, nLeft, nRight, nLevel);
	}

	@At
	public void save(@Param("..") Menu menu) {
		if (menu.getId() == 0) {
			menuService.dao().insert(menu);
		} else {
			menuService.dao().update(menu);
		}
	}
}
