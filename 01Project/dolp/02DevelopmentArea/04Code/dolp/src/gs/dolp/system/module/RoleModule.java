package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.domain.Role;
import gs.dolp.system.service.RoleService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roleModule")
@At("/system/role")
public class RoleModule {

	private RoleService roleService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("isOrgaRela") int isOrgaRela,
			@Param("organizationId") int organizationId) {
		return roleService.getGridData(jqReq, isOrgaRela, organizationId);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Role role) {
		return roleService.CUDRole(oper, ids, role);
	}

	@At("/getAllRoleMap/*")
	public ResponseData getAllRoleMap(int isOrgaRela) {
		return roleService.getAllRoleMap(isOrgaRela);
	}

	@At
	public ResponseData assignPrivilege(@Param("roleId") String roleId, @Param("checkedMenus[]") String[] checkedMenus,
			@Param("checkedPrivileges[]") String[] checkedPrivileges,
			@Param("unCheckedMenus[]") String[] unCheckedMenus,
			@Param("unCheckedPrivileges[]") String[] unCheckedPrivileges) {
		return roleService.updateRolePrivileges(roleId, checkedMenus, checkedPrivileges, unCheckedMenus,
				unCheckedPrivileges);
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
