package com.dolplay.dolpbase.system.service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.ExcelHandler;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" }, fields = { "prop" })
public class UserService extends DolpBaseService<User> {

	public UserService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<User> getGridData(JqgridReqData jqReq, String number, String name) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			cnd = cnd.and("NUMBER", "LIKE", "%" + Strings.trim(number) + "%");
		}
		if (!Strings.isBlank(name)) {
			cnd = cnd.and("NAME", "LIKE", "%" + Strings.trim(name) + "%");
		}
		AdvancedJqgridResData<User> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public User userAuthenticate(String number, String password) {
		Condition cnd = Cnd.where("NUMBER", "=", number).and("PASSWORD", "=", password);
		User user = fetch(cnd);
		if (null == user) {
			throw new RuntimeException("用户名或密码错误!");
		}
		return user;
	}

	@Aop(value = "log")
	public AjaxResData getNewUserNumber() {
		AjaxResData respData = new AjaxResData();
		Sql sql = Sqls.create("SELECT MAX(NUMBER) AS MAXNUMBER FROM SYSTEM_USER");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				String maxNumber = null;
				while (rs.next()) {
					maxNumber = rs.getString("MAXNUMBER");
				}
				return maxNumber;
			}
		});
		dao().execute(sql);
		String newUserNumber = Strings.alignRight(String.valueOf(Integer.parseInt((String) sql.getResult()) + 1), 4,
				'0');
		respData.setReturnData(newUserNumber);
		return respData;
	}

	@Aop(value = "log")
	public boolean isUserNumberDuplicate(String userNumber) {
		int count = count(Cnd.where("NUMBER", "=", userNumber));
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Aop(value = "log")
	public AjaxResData CUDUser(String oper, String ids, User user) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			if (!Strings.isEmpty(ids)) {
				final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
				final List<User> users = query(cnd, null);
				Trans.exec(new Atom() {
					public void run() {
						for (User user : users) {
							dao().clearLinks(user, "roles");
						}
						clear(cnd);
					}
				});
			}
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			if (isUserNumberDuplicate(user.getNumber())) {
				respData.setSystemMessage(null, null, "添加失败：系统中已存在相同的用户编号!");
				return respData;
			}
			user.setPassword(getSysParaValue("DefaultPassword"));
			respData.setSystemMessage("添加成功!", null, null);
			dao().insert(user);
		} else if ("edit".equals(oper)) {
			// 忽略“密码”字段
			dao().updateIgnoreNull(user);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未操作数据", null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData updateRole(String userId, String[] roleIds) {
		AjaxResData respData = new AjaxResData();
		final String userID;
		if (Strings.isEmpty(userId)) {
			respData.setSystemMessage(null, "未选择用户!", null);
			return respData;
		} else {
			userID = userId;
		}
		// 如果新分配的角色 roleIds为Null,则直接清空中间表中该用户原有角色然后return
		if (roleIds == null || roleIds.length == 0) {
			dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", userID));
			respData.setSystemMessage(null, "该用户未分配任何角色!", null);
			return respData;
		}
		List<Role> roles = new ArrayList<Role>();
		// 取得要更新角色的用户
		final User user = fetch(Integer.parseInt(userId));
		// 从数据库中获取指定id的角色
		for (String roleId : roleIds) {
			Role role = dao().fetch(Role.class, Integer.parseInt(roleId));
			roles.add(role);
		}
		// 为该用户分配这些角色
		user.setRoles(roles);
		Trans.exec(new Atom() {
			public void run() {
				// 清空中间表中该用户原有角色
				dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", userID));
				// 插入中间表记录
				dao().insertRelation(user, "roles");
			}
		});
		respData.setSystemMessage("分配成功!", null, null);
		return respData;
	}

	@SuppressWarnings("unchecked")
	@Aop(value = "log")
	public AjaxResData getCurrentRoleIdArr(String userId) {
		AjaxResData respData = new AjaxResData();
		Sql sql = Sqls
				.create("SELECT ID FROM SYSTEM_ROLE WHERE ISORGARELA = $isOrgaRela AND ID IN (SELECT DISTINCT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID = $userId)");
		sql.vars().set("isOrgaRela", false);
		sql.vars().set("userId", userId);
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Integer> currentRoleIDs = new ArrayList<Integer>();
				while (rs.next()) {
					currentRoleIDs.add(rs.getInt("ID"));
				}
				return currentRoleIDs;
			}
		});
		dao().execute(sql);
		List<Integer> currentRoleIDs = (List<Integer>) sql.getResult();
		respData.setReturnData(currentRoleIDs);
		return respData;
	}

	@Aop(value = "log")
	public List<Privilege> getCurrentPrivileges(int userId) {
		StringBuilder sb = new StringBuilder(
				"ID IN (SELECT DISTINCT PRIVILEGEID FROM SYSTEM_ROLE_PRIVILEGE WHERE ROLEID IN(SELECT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID='");
		sb.append(userId).append("'))");
		Condition cnd = Cnd.wrap(sb.toString());
		List<Privilege> privileges = dao().query(Privilege.class, cnd, null);
		return privileges;
	}

	@Aop(value = "log")
	public AjaxResData updatePost(final String userId, String orgId, final String[] postIds) {
		AjaxResData respData = new AjaxResData();
		StringBuilder sb = new StringBuilder();
		sb.append("ROLEID IN (SELECT ID FROM SYSTEM_ROLE WHERE ORGANIZATIONID =");
		sb.append(orgId);
		sb.append(") AND USERID = ");
		sb.append(userId);
		final Condition cnd = Cnd.wrap(sb.toString());
		// 如果未选中任何岗位，则清除此用户该机构下的所有岗位关系
		if (postIds == null || postIds.length == 0) {
			dao().clear("SYSTEM_USER_ROLE", cnd);
			respData.setSystemMessage(null, "该机构下未分配任何岗位!", null);
			return respData;
		}
		// 清除旧岗位关系，插入新的关系
		Trans.exec(new Atom() {
			public void run() {
				dao().clear("SYSTEM_USER_ROLE", cnd);
				for (String postId : postIds) {
					dao().insert("SYSTEM_USER_ROLE", Chain.make("ROLEID", postId).add("USERID", userId));
				}
			}
		});
		respData.setSystemMessage("分配成功!", null, null);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData changePasswordForCurrentUser(User user, String oldPassword, String newPassword) {
		AjaxResData respData = new AjaxResData();
		int countAuthenticatedUser = count(Cnd.where("ID", "=", user.getId()).and("PASSWORD", "=", oldPassword));
		if (countAuthenticatedUser == 0) {
			respData.setSystemMessage(null, "原密码错误!", null);
		} else {
			user.setPassword(newPassword);
			dao().update(user);
			respData.setSystemMessage("密码修改成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData changePasswordForAUser(int userId, String newPassword) {
		AjaxResData respData = new AjaxResData();
		if (userId > 0) {
			User user = new User();
			user.setId(userId);
			user.setPassword(newPassword);
			dao().updateIgnoreNull(user);
			respData.setSystemMessage("密码修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未正确选择用户", null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData importUsers(File f) {
		AjaxResData respData = new AjaxResData();

		HSSFWorkbook wb;
		List<Object> userList;
		try {
			wb = new HSSFWorkbook(new FileInputStream(f));
			HSSFSheet sheet = wb.getSheetAt(0);
			userList = ExcelHandler.readSheet(sheet, User.class);
		} catch (Exception e) {
			throw new RuntimeException("读取EXCEL文档出错!");
		}
		for (Object obj : userList) {
			User user = (User) obj;
			dao().insert(user);
		}
		f.delete();

		respData.setSystemMessage("新增" + userList.size() + "位用户", null, null);
		return respData;
	}
}