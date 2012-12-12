package com.dolplay.dolpbase.system.module;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Menu;
import com.dolplay.dolpbase.system.service.MenuService;

@IocBean
@At("/system/menu")
public class MenuModule {

	@Inject
	private MenuService menuService;

	/**
	 * 菜单管理页面，右侧菜单grid的显示
	 * @param parentId
	 * @param jqReq
	 * @return
	 */
	@At("/getGridData/*")
	@RequiresPermissions("menu:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("parentId") Long parentId,
			@Param("_search") Boolean isSearch, @Param("..") Menu menuSearch) {
		return menuService.getGridData(jqReq, parentId, isSearch, menuSearch);
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
	@RequiresPermissions("menu:create, delete, update:*")
	public ResponseData editRow(Long parentId, @Param("oper") String oper, @Param("ids") String ids,
			@Param("..") Menu menu) {
		return menuService.CUDMenu(oper, ids, menu, parentId);
	}

	/**
	 * 菜单管理页面，右侧菜单grid，添加父菜单功能
	 * @param parentId
	 * @param name
	 * @param description
	 * @param parentLevel
	 * @return
	 */
	@At
	@RequiresPermissions("menu:create:*")
	public ResponseData addParentMenu(@Param("parentId") Long parentId, @Param("name") String name,
			@Param("description") String description) {
		return menuService.addMenuIsNotLeaf(parentId, name, description);
	}

	/**
	 * 菜单管理和权限管理页面，左侧菜单树的显示（不显示叶子节点）
	 * 注:该入口方法实现了权限管理中，页面可见权限部分
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @return
	 */
	@At
	@RequiresPermissions("menu:read:*")
	public ResponseData getNodes(@Param("id") Long id, @Param("name") String name) {
		return menuService.getTreeNodes(id);
	}

	/**
	 * 角色管理页面中的权限分配菜单树显示
	 * @param roleId
	 * @return
	 */
	@At("/getPrivilegeNodesByRoleId/*")
	@RequiresPermissions("menu:read:*")
	public ResponseData getPrivilegeNodesByRoleId(Long roleId, @Param("id") Long id, @Param("lft") Long lft,
			@Param("rgt") Long rgt) {
		return menuService.getPrivilegeTreeNodesByRoleId(roleId, id, lft, rgt);
	}

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
	@RequiresPermissions("menu:read:*")
	public ResponseData dispMenu(@Param("nodeid") Long nodeId, @Param("n_left") Long nLeft,
			@Param("n_right") Long nRight, @Param("n_level") Integer nLevel, HttpServletRequest request,
			ServletResponse response) {
		return menuService.getGridData(nodeId, nLeft, nRight, nLevel, request, response);
	}
}