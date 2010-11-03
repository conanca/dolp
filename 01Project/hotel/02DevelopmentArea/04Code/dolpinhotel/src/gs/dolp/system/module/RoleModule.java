package gs.dolp.system.module;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.system.domain.Role;
import gs.dolp.system.service.RoleService;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roleModule")
@At("/system/role")
//@Filters( { @By(type = CheckSession.class, args = { "logonUser", "/login.jsp" }) })
public class RoleModule {

	private RoleService roleService;

	@At
	public JqgridAdvancedData<Role> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return roleService.getGridData(page, rows, sidx, sord);
	}

	@At
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		roleService.CUDRole(oper, id, name, description);
	}

	@At
	public Map<String, String> getAllRole() {
		return roleService.getAllRole();
	}
}
