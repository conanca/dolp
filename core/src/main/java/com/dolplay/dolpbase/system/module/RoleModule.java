package com.dolplay.dolpbase.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.service.RoleService;

@IocBean
@At("/system/role")
public class RoleModule {

	@Inject
	private RoleService roleService;

	@At
	@RequiresPermissions("role:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Role roleSearch) {
		return roleService.getGridData(jqReq, isSearch, roleSearch);
	}

	@At
	@RequiresPermissions("role:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Role role) {
		return roleService.CUDRole(oper, ids, role);
	}

	@At
	@RequiresPermissions("role:assign_permission:*")
	public ResponseData assignPrivilege(@Param("roleId") String roleId, @Param("checkedMenus[]") String[] checkedMenus,
			@Param("checkedPrivileges[]") String[] checkedPrivileges,
			@Param("unCheckedMenus[]") String[] unCheckedMenus,
			@Param("unCheckedPrivileges[]") String[] unCheckedPrivileges) {
		return roleService.updateRolePrivileges(roleId, checkedMenus, checkedPrivileges, unCheckedMenus,
				unCheckedPrivileges);
	}

	@At("/getAllRoleMap/*")
	@RequiresPermissions("role:read:*")
	public ResponseData getAllRoleMap(Boolean isOrgaRela) {
		return roleService.getAllRoleMap(isOrgaRela);
	}

	/**
	 * 获取指定用户的岗位列表
	 * @param jqReq
	 * @param organizationId
	 * @param userId
	 * @return
	 */
	@At
	@RequiresPermissions("user:read_post:*")
	public ResponseData getUserPostGridData(@Param("..") JqgridReqData jqReq,
			@Param("organizationId") Long organizationId, @Param("userId") Long userId) {
		return roleService.getUserPostGridData(jqReq, organizationId, userId);
	}
}