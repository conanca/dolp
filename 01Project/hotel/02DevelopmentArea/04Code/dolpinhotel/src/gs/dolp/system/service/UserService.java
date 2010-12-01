package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
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

public class UserService extends AdvJqgridIdEntityService<User> {

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
			cnd = cnd.and("NUMBER", "LIKE", "%" + Strings.trim(name) + "%");
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
		int count = this.count(Cnd.where("NUMBER", "=", userNumber));
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
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			clear(cnd);
		}
	}

	@Aop(value = "log")
	public ResponseSysMsgData updateRole(String userId, String[] roleIds) {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		// 取得要更新角色的用户
		User user = fetch(Integer.parseInt(userId));
		// 清空中间表中该用户原有角色
		dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", user.getId()));
		// 如果新分配的角色 roleIds为Null,则直接return
		if (roleIds == null || roleIds.length == 0) {
			reData.setUserdata(new SystemMessage(null, "未分配任何角色!", null));
			return reData;
		}
		List<Role> roles = new ArrayList<Role>();
		// 从数据库中获取指定id的角色
		for (String roleId : roleIds) {
			RoleService roleService = new RoleService(this.dao());
			Role role = roleService.fetch(Integer.parseInt(roleId));
			roles.add(role);
		}
		// 为该用户分配这些角色
		user.setRoles(roles);
		// 插入中间表记录
		dao().insertRelation(user, "roles");
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
