package gs.dolp.system.module;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.service.MenuService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("menuModule")
@At("/system/menu")
public class MenuModule {

	private MenuService menuService;

	/**
	 * 角色管理页面中的权限分配菜单树显示
	 * @param roleId
	 * @return
	 */
	@At("/getPrivilegeNodesByRoleId/*")
	public AjaxResData getPrivilegeNodesByRoleId(int roleId, @Param("id") int id, @Param("lft") int lft,
			@Param("rgt") int rgt, @Param("level") int level) {
		return menuService.getPrivilegeTreeNodesByRoleId(roleId, id, lft, rgt, level);
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
	public AjaxResData getNodes(@Param("id") int id, @Param("name") String name) {
		return menuService.getTreeNodes(id);
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
	public ResponseData addParentMenu(@Param("parentId") int parentId, @Param("name") String name,
			@Param("description") String description) {
		return menuService.addMenuIsNotLeaf(parentId, name, description);
	}
}
