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
	public AdvancedJqgridResData<Role> getGridData(@Param("..") JqgridReqData jqReq, @Param("isOrgaRela") int isOrgaRela) {
		return roleService.getGridData(jqReq, isOrgaRela);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		return roleService.CUDRole(oper, id, name, description);
	}

	@At
	public Map<String, String> getAllRole(@Param("isOrgaRela") int isOrgaRela) {
		return roleService.getAllRole(isOrgaRela);
	}

	@At
	public ResponseData assignMenu(@Param("roleId") String roleId, @Param("selectedMenuIds[]") String[] selectedMenuIds) {
		return roleService.updateMenu(roleId, selectedMenuIds);
	}
}
