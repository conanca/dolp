package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.Role;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoleService extends JqgridService<Role> {

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
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			final List<Role> roles = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Role role : roles) {
						dao().clearLinks(role, "users");
						dao().clearLinks(role, "menus");
						dao().clearLinks(role, "privileges");
					}
					clear(cnd);
				}
			});
			respData.setSystemMessage("删除成功!", null, null);
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
			respData.setSystemMessage("添加成功!", null, null);
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
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getAllRoleMap(int isOrgaRela) {
		AjaxResData respData = new AjaxResData();
		List<Role> roles = query(Cnd.where("ISORGARELA", "=", isOrgaRela), null);
		Map<String, String> roleOptions = new LinkedHashMap<String, String>();
		for (Role r : roles) {
			roleOptions.put(r.getName(), String.valueOf(r.getId()));
		}
		respData.setReturnData(roleOptions);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData updateRolePrivileges(final String roleId, final String[] checkedMenus,
			final String[] checkedPrivileges, final String[] unCheckedMenus, final String[] unCheckedPrivileges) {
		AjaxResData respData = new AjaxResData();

		Trans.exec(new Atom() {
			public void run() {
				// 删除该角色未选中的菜单项和操作权限
				if (unCheckedMenus != null && unCheckedMenus.length > 0) {
					dao().clear("SYSTEM_ROLE_MENU",
							Cnd.where("ROLEID", "=", roleId).and("MENUID", "in", unCheckedMenus));
				}
				if (unCheckedPrivileges != null && unCheckedMenus.length > 0) {
					dao().clear("SYSTEM_ROLE_PRIVILEGE",
							Cnd.where("ROLEID", "=", roleId).and("PRIVILEGEID", "in", unCheckedPrivileges));
				}

				// 添加该角色选中的菜单项和操作权限，如果已存在则不添加
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

		respData.setSystemMessage("分配成功!", null, null);
		return respData;
	}

	@Aop(value = "log")
	public StandardJqgridResData getUserPostGridData(JqgridReqData jqReq, int organizationId, int userId)
			throws SQLException {
		Sql sql = Sqls.create("SELECT ID,NAME,DESCRIPTION,ID IN "
				+ "(SELECT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID = $userId) AS ISSET "
				+ "FROM SYSTEM_ROLE WHERE ORGANIZATIONID = $organizationId");
		sql.vars().set("userId", userId);
		sql.vars().set("organizationId", organizationId);
		StandardJqgridResData jq = getStandardJqgridResData(sql, null, jqReq);
		return jq;
	}
}
