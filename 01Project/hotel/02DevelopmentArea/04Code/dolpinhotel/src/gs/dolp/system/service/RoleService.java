package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.Role;

import java.util.ArrayList;
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
			Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(id).append(")").toString());
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

	@Aop(value = "log")
	public ResponseSysMsgData updateMenu(String roleId, String[] menuIds) {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		// 取得要更新可见菜单的角色
		Role role = fetch(Integer.parseInt(roleId));
		// 清空中间表中该角色原有可见菜单
		dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", role.getId()));
		// 如果新分配的可见菜单 menuIds为Null,则直接return
		if (menuIds == null || menuIds.length == 0) {
			reData.setUserdata(new SystemMessage(null, "未分配任何菜单!", null));
			return reData;
		}
		List<Menu> menus = new ArrayList<Menu>();
		// 从数据库中获取指定id的菜单
		for (String menuId : menuIds) {
			Menu menu = dao().fetch(Menu.class, Integer.parseInt(menuId));
			menus.add(menu);
		}
		// 为该角色分配这些菜单
		role.setMenus(menus);
		// 插入中间表记录
		dao().insertRelation(role, "menus");
		reData.setUserdata(new SystemMessage("分配成功!", null, null));
		return reData;
	}
}
