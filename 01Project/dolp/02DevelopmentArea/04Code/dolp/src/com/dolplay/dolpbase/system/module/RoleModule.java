package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.service.RoleService;

@InjectName("roleModule")
@At("/system/role")
public class RoleModule {

	private RoleService roleService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("isOrgaRela") boolean isOrgaRela,
			@Param("organizationId") int organizationId) {
		return roleService.getGridData(jqReq, isOrgaRela, organizationId);
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
	public ResponseData getAllRoleMap(boolean isOrgaRela) {
		return roleService.getAllRoleMap(isOrgaRela);
	}

	/**
	 * 获取指定用户的岗位列表
	 * @param jqReq
	 * @param organizationId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@At
	public ResponseData getUserPostGridData(@Param("..") JqgridReqData jqReq,
			@Param("organizationId") int organizationId, @Param("userId") int userId) throws Exception {
		return roleService.getUserPostGridData(jqReq, organizationId, userId);
	}
}