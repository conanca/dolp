package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.MenuService;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("menuModule")
@At("/system/menu")
public class MenuModule {

	private MenuService menuService;

	/**
	 * west布局的菜单显示
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @param session
	 * @return
	 */
	@At
	public StandardJqgridResData dispMenu(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel, HttpSession session) {
		User logonUser = (User) session.getAttribute("logonUser");
		return menuService.getGridData(nodeId, nLeft, nRight, nLevel, logonUser);
	}

	@At
	public StandardJqgridResData dispParentMenu(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel) {
		return menuService.getGridDataOnlyParent(nodeId, nLeft, nRight, nLevel);
	}

	/**
	 * 角色可见分配菜单页面中的菜单显示
	 * @param roleId
	 * @return
	 */
	@At("/getMenuByRoleId/*")
	public StandardJqgridResData getMenuByRoleId(int roleId) {
		return menuService.getMenuByRoleId(roleId);
	}

	@At("/getGridData/*")
	public AdvancedJqgridResData<Menu> getGridData(int parentId, @Param("..") JqgridReqData jqReq) {
		return menuService.getGridData(jqReq, parentId);
	}

	@At("/editRow/*")
	public ResponseData editRow(int parentId, @Param("oper") String oper, @Param("id") String id,
			@Param("name") String name, @Param("url") String url, @Param("description") String description) {
		return menuService.CUDMenu(oper, id, name, url, description, parentId);
	}
}
