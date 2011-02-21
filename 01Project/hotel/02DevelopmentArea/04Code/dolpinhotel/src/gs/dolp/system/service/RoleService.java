package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Role;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
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
	public AdvancedJqgridResData<Role> getGridData(JqgridReqData jqReq, int isOrgaRela, int organizationId) {
		Condition cnd = Cnd.where("ISORGARELA", "=", isOrgaRela);
		if (isOrgaRela == 1) {
			cnd = ((Cnd) cnd).and("ORGANIZATIONID", "=", organizationId);
		}
		AdvancedJqgridResData<Role> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDRole(String oper, String id, String name, String description, String isOrgaRela,
			String organizationId) {
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
			if (!Strings.isEmpty(isOrgaRela)) {
				role.setIsOrgaRela(Integer.parseInt(isOrgaRela));
				if (isOrgaRela.equals("1")) {
					role.setOrganizationId(Integer.parseInt(organizationId));
				}
			}
			dao().insert(role);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Role role = fetch(Integer.parseInt(id));
			role.setId(Integer.parseInt(id));
			role.setName(name);
			role.setDescription(description);
			if (!Strings.isEmpty(isOrgaRela)) {
				role.setIsOrgaRela(Integer.parseInt(isOrgaRela));
				if (isOrgaRela.equals("1")) {
					role.setOrganizationId(Integer.parseInt(organizationId));
				}
			}
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
	public AjaxResData updateMenu(final String roleId, final String[] checkedMenus, final String[] checkedPrivileges,
			final String[] unCheckedMenus, final String[] unCheckedPrivileges) {
		AjaxResData reData = new AjaxResData();

		Trans.exec(new Atom() {
			public void run() {
				if (unCheckedMenus != null && unCheckedMenus.length > 0) {
					dao().clear("SYSTEM_ROLE_MENU",
							Cnd.where("ROLEID", "=", roleId).and("MENUID", "in", unCheckedMenus));
				}
				if (unCheckedPrivileges != null && unCheckedMenus.length > 0) {
					dao().clear("SYSTEM_ROLE_PRIVILEGE",
							Cnd.where("ROLEID", "=", roleId).and("PRIVILEGEID", "in", unCheckedPrivileges));
				}

				if (checkedMenus != null) {
					for (String checkedMenu : checkedMenus) {
						int checkedMenuCount = dao().count("SYSTEM_ROLE_MENU",
								Cnd.where("ROLEID", "=", roleId).and("MENUID", "=", checkedMenu));
						if (checkedMenuCount == 0) {
							dao().insert("SYSTEM_ROLE_MENU", Chain.make("ROLEID", roleId).add("MENUID", checkedMenu));
						}
					}
				}
				if (checkedPrivileges != null) {
					for (String checkedPrivilege : checkedPrivileges) {
						int checkedPrivilegeCount = dao().count("SYSTEM_ROLE_PRIVILEGE",
								Cnd.where("ROLEID", "=", roleId).and("PRIVILEGEID", "=", checkedPrivilege));
						if (checkedPrivilegeCount == 0) {
							dao().insert("SYSTEM_ROLE_PRIVILEGE",
									Chain.make("ROLEID", roleId).add("PRIVILEGEID", checkedPrivilege));
						}
					}
				}
			}
		});

		reData.setUserdata(new SystemMessage("分配成功!", null, null));
		return reData;
	}
}
