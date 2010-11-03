package gs.dolp.system.service;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;
import gs.dolp.system.domain.Role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class RoleService extends IdEntityForjqGridService<Role> {

	private static final Log log = Logs.getLog(RoleService.class);

	public RoleService(Dao dao) {
		super(dao);
	}

	public JqgridAdvancedData<Role> getGridData(String page, String rows, String sidx, String sord) {
		JqgridAdvancedData<Role> jq = getjqridDataByCnd(null, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public void CUDRole(String oper, String id, String name, String description) {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
		}
		if ("add".equals(oper)) {
			Role role = new Role();
			role.setName(name);
			role.setDescription(description);
			dao().insert(role);
		}
		if ("edit".equals(oper)) {
			Role role = new Role();
			role.setId(Integer.parseInt(id));
			role.setName(name);
			role.setDescription(description);
			dao().update(role);
		}
	}

	public Map<String, String> getAllRole() {
		List<Role> roles = query(null, null);
		Map<String, String> roleOptions = new LinkedHashMap<String, String>();
		for (Role r : roles) {
			roleOptions.put(r.getName(), String.valueOf(r.getId()));
		}
		return roleOptions;
	}
}
