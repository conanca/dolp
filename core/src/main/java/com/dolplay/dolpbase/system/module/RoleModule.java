package com.dolplay.dolpbase.system.module;

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
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Role roleSearch) {
		return roleService.getGridData(jqReq, isSearch, roleSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Role role) {
		return roleService.CUDRole(oper, ids, role);
	}

	@At
	public ResponseData assignPrivilege(@Param("roleId") String roleId, @Param("checkedMenus[]") String[] checkedMenus,
			@Param("checkedPrivileges[]") String[] checkedPrivileges,
			@Param("unCheckedMenus[]") String[] unCheckedMenus,
			@Param("unCheckedPrivileges[]") String[] unCheckedPrivileges) {
		return roleService.updateRolePrivileges(roleId, checkedMenus, checkedPrivileges, unCheckedMenus,
				unCheckedPrivileges);
	}

	@At("/getAllRoleMap/*")
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
	public ResponseData getUserPostGridData(@Param("..") JqgridReqData jqReq,
			@Param("organizationId") Long organizationId, @Param("userId") Long userId) {
		return roleService.getUserPostGridData(jqReq, organizationId, userId);
	}
}