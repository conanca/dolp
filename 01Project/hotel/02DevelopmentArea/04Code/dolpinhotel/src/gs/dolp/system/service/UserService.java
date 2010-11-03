package gs.dolp.system.service;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.User;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class UserService extends IdEntityForjqGridService<User> {

	private static final Log log = Logs.getLog(UserService.class);

	public UserService(Dao dao) {
		super(dao);
	}

	public JqgridAdvancedData<User> getGridData(String page, String rows, String sidx, String sord) {
		JqgridAdvancedData<User> jq = getjqridDataByCnd(null, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public User userAuthenticate(String number, String password) {
		Condition cnd = Cnd.where("NUMBER", "=", number).and("PASSWORD", "=", password);
		User user = fetch(cnd);
		if (null == user) {
			throw new RuntimeException("Error username or password");
		}
		return user;
	}

	public void deleteUsers(String ids) {
		if (!Strings.isEmpty(ids)) {
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			clear(cnd);
		}
	}

	public void updateRole(String userId, String[] roleIds) {
		// 取得要更新角色的用户
		User user = fetch(Integer.parseInt(userId));
		// 清空中间表中该用户原有角色
		dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", user.getId()));
		// 如果roleIds为Null,则直接return
		if (roleIds == null) {
			return;
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
	}

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
