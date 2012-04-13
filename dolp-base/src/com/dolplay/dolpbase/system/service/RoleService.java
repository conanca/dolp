package com.dolplay.dolpbase.system.service;

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
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.domain.jqgrid.StandardJqgridResData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Role;

@IocBean(args = { "refer:dao" })
public class RoleService extends DolpBaseService<Role> {

	public RoleService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Role> getGridData(JqgridReqData jqReq, Boolean isSearch, Role roleSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != roleSearch) {
			Boolean isOrgaRela = roleSearch.getIsOrgaRela();
			if (null != isOrgaRela) {
				cnd = Cnd.where("ISORGARELA", "=", isOrgaRela);
				if (isOrgaRela) {
					Long organizationId = roleSearch.getOrganizationId();
					if (null != organizationId) {
						cnd = cnd.and("ORGANIZATIONID", "=", organizationId);
					}
				}
			}
			if (null != isSearch && isSearch) {
				String name = roleSearch.getName();
				if (!Strings.isEmpty(name)) {
					cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
				}
				String description = roleSearch.getDescription();
				if (!Strings.isEmpty(description)) {
					cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
				}
			}
		}
		AdvancedJqgridResData<Role> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDRole(String oper, String ids, Role role) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
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
		} else if ("add".equals(oper)) {
			dao().insert(role);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(role);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getAllRoleMap(Boolean isOrgaRela) {
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

	/**
	 * 获取指定用户指定机构的岗位列表
	 * @param jqReq
	 * @param organizationId
	 * @param userId
	 * @return
	 */
	@Aop(value = "log")
	public StandardJqgridResData getUserPostGridData(JqgridReqData jqReq, Long organizationId, Long userId) {
		Sql sql = Sqls
				.create("SELECT ID,NAME,DESCRIPTION,ID IN (SELECT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID = @userId) AS ISSET FROM SYSTEM_ROLE WHERE ORGANIZATIONID = @organizationId");
		sql.params().set("userId", userId);
		sql.params().set("organizationId", organizationId);
		StandardJqgridResData jq = getStandardJqgridResData(sql, jqReq);
		return jq;
	}
}