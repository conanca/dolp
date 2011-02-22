package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.Role;
import gs.dolp.system.service.RoleService;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roleModule")
@At("/system/role")
public class RoleModule {

	private RoleService roleService;

	@At
	public AdvancedJqgridResData<Role> getGridData(@Param("..") JqgridReqData jqReq,
			@Param("isOrgaRela") int isOrgaRela, @Param("organizationId") int organizationId) {
		return roleService.getGridData(jqReq, isOrgaRela, organizationId);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description, @Param("isOrgaRela") String isOrgaRela,
			@Param("organizationId") String organizationId) {
		return roleService.CUDRole(oper, id, name, description, isOrgaRela, organizationId);
	}

	@At
	public Map<String, String> getAllRole(@Param("isOrgaRela") int isOrgaRela) {
		return roleService.getAllRole(isOrgaRela);
	}

	@At
	public ResponseData assignMenu(@Param("roleId") String roleId, @Param("checkedMenus[]") String[] checkedMenus,
			@Param("checkedPrivileges[]") String[] checkedPrivileges,
			@Param("unCheckedMenus[]") String[] unCheckedMenus,
			@Param("unCheckedPrivileges[]") String[] unCheckedPrivileges) {
		System.out.println("123");
		return roleService.updateRolePrivileges(roleId, checkedMenus, checkedPrivileges, unCheckedMenus, unCheckedPrivileges);
	}
}
