package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class RoleService extends AdvJqgridIdEntityService<Role> {

	public RoleService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Role> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<Role> jq = getAdvancedJqgridRespData(null, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public ResponseSysMsgData CUDRole(String oper, String id, String name, String description) {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			Role role = new Role();
			role.setName(name);
			role.setDescription(description);
			dao().insert(role);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Role role = new Role();
			role.setId(Integer.parseInt(id));
			role.setName(name);
			role.setDescription(description);
			dao().update(role);
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}

	@Aop(value = "log")
	public Map<String, String> getAllRole() {
		List<Role> roles = query(null, null);
		Map<String, String> roleOptions = new LinkedHashMap<String, String>();
		for (Role r : roles) {
			roleOptions.put(r.getName(), String.valueOf(r.getId()));
		}
		return roleOptions;
	}
}
