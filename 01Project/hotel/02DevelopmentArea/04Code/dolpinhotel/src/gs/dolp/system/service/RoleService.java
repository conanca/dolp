package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
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
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoleService extends AdvJqgridIdEntityService<Role> {

	public RoleService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Role> getGridData(JqgridReqData jqReq, int isOrgaRela) {
		Condition cnd = null;
		if (isOrgaRela != -1) {
			cnd = Cnd.where("ISORGARELA", "=", isOrgaRela);
		}
		AdvancedJqgridResData<Role> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDRole(String oper, String id, String name, String description) {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(id).append(")").toString());
			final List<Role> roles = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Role role : roles) {
						dao().clearLinks(role, "users");
						dao().clearLinks(role, "menus");
					}
					clear(cnd);
				}
			});
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
	public Map<String, String> getAllRole(int isOrgaRela) {
		List<Role> roles = query(Cnd.where("ISORGARELA", "=", 0), null);
		Map<String, String> roleOptions = new LinkedHashMap<String, String>();
		for (Role r : roles) {
			roleOptions.put(r.getName(), String.valueOf(r.getId()));
		}
		return roleOptions;
	}

	@Aop(value = "log")
	public AjaxResData updateMenu(String roleId, String[] menuIds) {
		AjaxResData reData = new AjaxResData();
		final String roleID;
		if (Strings.isEmpty(roleId)) {
			reData.setUserdata(new SystemMessage(null, "未选择角色!", null));
			return reData;
		} else {
			roleID = roleId;
		}
		// 如果新分配的可见菜单 menuIds为Null,则清空中间表中该角色原有可见菜单并return
		if (menuIds == null || menuIds.length == 0) {
			dao().clear("SYSTEM_ROLE_MENU", Cnd.where("ROLEID", "=", roleId));
			reData.setUserdata(new SystemMessage(null, "该角色未分配任何菜单!", null));
			return reData;
		}
		// 取得要更新可见菜单的角色
		final Role role = fetch(Integer.parseInt(roleId));
		List<Menu> menus = new ArrayList<Menu>();
		// 从数据库中获取指定id的菜单
		for (String menuId : menuIds) {
			Menu menu = dao().fetch(Menu.class, Integer.parseInt(menuId));
			menus.add(menu);
		}
		// 为该角色分配这些菜单
		role.setMenus(menus);
		Trans.exec(new Atom() {
			public void run() {
				// 清空中间表中该角色原有可见菜单
				dao().clear("SYSTEM_ROLE_MENU", Cnd.where("ROLEID", "=", roleID));
				// 插入中间表记录
				dao().insertRelation(role, "menus");
			}
		});
		reData.setUserdata(new SystemMessage("分配成功!", null, null));
		return reData;
	}
}
