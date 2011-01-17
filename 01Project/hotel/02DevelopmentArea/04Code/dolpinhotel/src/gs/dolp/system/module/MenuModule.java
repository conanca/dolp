package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.MenuEntity;
import gs.dolp.system.service.MenuService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("menuModule")
@At("/system/menu")
public class MenuModule {

	private MenuService menuService;

	/**
	 * 角色可见分配菜单页面中的菜单显示
	 * @param roleId
	 * @return
	 */
	@At("/getMenuByRoleId/*")
	public AdvancedJqgridResData<MenuEntity> getMenuByRoleId(int roleId) {
		return menuService.getMenuByRoleId(roleId);
	}

	/**
	 * 菜单管理页面，左侧菜单树的显示（不显示叶子节点）
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @return
	 */
	@At
	public AdvancedJqgridResData<MenuEntity> dispParentMenu(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel) {
		return menuService.getGridDataOnlyParent(nodeId, nLeft, nRight, nLevel);
	}

	/**
	 * 菜单管理页面，右侧菜单grid的显示
	 * @param parentId
	 * @param jqReq
	 * @return
	 */
	@At("/getGridData/*")
	public AdvancedJqgridResData<Menu> getGridData(int parentId, @Param("..") JqgridReqData jqReq) {
		return menuService.getGridData(jqReq, parentId);
	}

	/**
	 * 菜单管理页面，右侧菜单grid的编辑
	 * @param parentId
	 * @param oper
	 * @param id
	 * @param name
	 * @param url
	 * @param description
	 * @return
	 */
	@At("/editRow/*")
	public ResponseData editRow(int parentId, @Param("oper") String oper, @Param("id") String id,
			@Param("name") String name, @Param("url") String url, @Param("description") String description) {
		return menuService.CUDMenu(oper, id, name, url, description, parentId);
	}

	/**
	 * 菜单管理页面，右侧菜单grid，添加父菜单功能
	 * @param parentId
	 * @param name
	 * @param description
	 * @param parentLevel
	 * @return
	 */
	@At("/addParentMenu/*")
	public ResponseData addParentMenu(int parentId, @Param("name") String name,
			@Param("description") String description, @Param("parentLevel") int parentLevel) {
		return menuService.addMenuIsNotLeaf(parentId, name, description, parentLevel);
	}
}
