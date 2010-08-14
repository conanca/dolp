package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.Role;
import gs.dolp.system.service.RoleService;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("roleModule")
@At("/system/role")
@Fail("json")
//@Filters( { @By(type = CheckSession.class, args = { "logonUser", "/login.jsp" }) })
public class RoleModule {

	private RoleService roleService;

	@At
	@Ok("json")
	public JqgridData<Role> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return roleService.getGridData(page, rows, sidx, sord);
	}

	@At
	@Ok("void")
	@Fail("json")
	public void save(@Param("..") Role role) {
		if (role.getId() == 0) {
			roleService.dao().insert(role);
		} else {
			roleService.dao().update(role);
		}
	}

	@At
	@Ok("void")
	@Fail("json")
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("description") String description) {
		roleService.CRURole(oper, id, name, description);
	}

	@At
	@Ok("json")
	public Map<String, String> getAllRole() {
		return roleService.getAllRole();
	}
}
