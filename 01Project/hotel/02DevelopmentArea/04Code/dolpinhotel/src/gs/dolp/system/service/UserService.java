package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class UserService extends AdvJqgridIdEntityService<User> {

	public UserService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<User> getGridData(JqgridReqData jqReq, String organizationId, String number,
			String name) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(organizationId)) {
			cnd = cnd.and("ORGANIZATIONID", "=", Strings.trim(organizationId));
		}
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
	public String getNewUserNumber() {
		String newUserNumber = "";
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
		newUserNumber = Strings.alignRight(String.valueOf(Integer.parseInt((String) sql.getResult()) + 1), 4, '0');
		return newUserNumber;
	}

	@Aop(value = "log")
	public boolean userNumberIsDuplicate(String userNumber) {
		int count = count(Cnd.where("NUMBER", "=", userNumber));
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Aop(value = "log")
	public void save(User user) {
		if (user.getId() == 0) {
			dao().insert(user);
		} else {
			dao().update(user);
		}
	}

	@Aop(value = "log")
	public void deleteUsers(String ids) {
		if (!Strings.isEmpty(ids)) {
			final Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(ids).append(")").toString());
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
	}

	@Aop(value = "log")
	public AjaxResData updateRole(String userId, String[] roleIds) {
		AjaxResData reData = new AjaxResData();
		final String userID;
		if (Strings.isEmpty(userId)) {
			reData.setUserdata(new SystemMessage(null, "未选择用户!", null));
			return reData;
		} else {
			userID = userId;
		}
		// 如果新分配的角色 roleIds为Null,则直接清空中间表中该用户原有角色然后return
		if (roleIds == null || roleIds.length == 0) {
			dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", userID));
			reData.setUserdata(new SystemMessage(null, "该用户未分配任何角色!", null));
			return reData;
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
		reData.setUserdata(new SystemMessage("分配成功!", null, null));
		return reData;
	}

	@Aop(value = "log")
	public int[] getCurrentRoleIDs(String userId) {
		User user = dao().fetchLinks(dao().fetch(User.class, Long.parseLong(userId)), "roles");
		List<Role> roles = user.getRoles();
		int[] currentRoleIDs = new int[roles.size()];
		int i = 0;
		for (Role r : roles) {
			currentRoleIDs[i] = r.getId();
			i++;
		}
		return currentRoleIDs;
	}
}
