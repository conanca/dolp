package com.dolplay.dolpbase.system.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.export.JRHtmlExporter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
import com.dolplay.dolpbase.common.report.ReportHandler;
import com.dolplay.dolpbase.common.report.WebappDataSource;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.ExcelHandler;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" })
public class UserService extends DolpBaseService<User> {

	public static final String SYSTEM_DEFAULTPASSWORD = "SYSTEM_DEFAULTPASSWORD";

	public UserService(Dao dao) {
		super(dao);
	}

	public User fetchByNumber(String number) {
		return this.fetch(Cnd.where("NUMBER", "=", number));
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<User> getGridData(JqgridReqData jqReq, Boolean isSearch, User searchUser) {
		Cnd cnd = null;
		if (null != isSearch && isSearch && null != searchUser) {
			cnd = Cnd.where("1", "=", 1);
			String number = searchUser.getNumber();
			if (!Strings.isEmpty(number)) {
				cnd.and("NUMBER", "LIKE", StringUtils.quote(number, '%'));
			}
			String name = searchUser.getName();
			if (!Strings.isEmpty(name)) {
				cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
			}
			String gender = searchUser.getGender();
			if (!Strings.isEmpty(gender)) {
				cnd.and("GENDER", "=", gender);
			}
			Integer age = searchUser.getAge();
			if (null != age) {
				cnd.and("AGE", "=", age);
			}
			String birthday = searchUser.getBirthday();
			if (!Strings.isEmpty(birthday)) {
				cnd.and("BIRTHDAY", "LIKE", StringUtils.quote(birthday, '%'));
			}
			String phone = searchUser.getPhone();
			if (!Strings.isEmpty(phone)) {
				cnd.and("PHONE", "=", phone);
			}
		}
		AdvancedJqgridResData<User> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public User userAuthenticate(String number, String password) {
		// 对输入的登录密码进行MD5加密
		password = DigestUtils.md5Hex(password);
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
		sql.setCallback(Sqls.callback.str());
		dao().execute(sql);
		String newUserNumber = Strings.alignRight(String.valueOf(Long.parseLong(sql.getString()) + 1), 4, '0');
		respData.setLogic(newUserNumber);
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
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			if (isUserNumberDuplicate(user.getNumber())) {
				respData.setError("添加失败：系统中已存在相同的用户编号!");
				return respData;
			}
			String defaultPass = getSysParaValue(SYSTEM_DEFAULTPASSWORD);
			// 对默认密码进行加盐加密
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toBase64();
			String hashedPasswordBase64 = new Sha256Hash(defaultPass, salt, 1024).toBase64();
			user.setSalt(salt);
			user.setPassword(hashedPasswordBase64);
			respData.setInfo("添加成功!");
			dao().insert(user);
		} else if ("edit".equals(oper)) {
			// 忽略“密码”“盐”字段
			dao().updateIgnoreNull(user);
			respData.setInfo("修改成功!");
		} else {
			respData.setError("未操作数据");
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData updateRole(final Long userId, String[] roleIds) {
		AjaxResData respData = new AjaxResData();
		if (userId <= 0) {
			respData.setNotice("未正确选择用户");
			return respData;
		}
		// 如果新分配的角色 roleIds为Null,则直接清空中间表中该用户原有角色然后return
		if (roleIds == null || roleIds.length == 0) {
			dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", userId));
			respData.setNotice("该用户未分配任何角色!");
			return respData;
		}
		List<Role> roles = new ArrayList<Role>();
		// 取得要更新角色的用户
		final User user = fetch(userId);
		// 从数据库中获取指定id的角色
		for (String roleId : roleIds) {
			Role role = dao().fetch(Role.class, Long.parseLong(roleId));
			roles.add(role);
		}
		// 为该用户分配这些角色
		user.setRoles(roles);
		Trans.exec(new Atom() {
			public void run() {
				// 清空中间表中该用户原有角色
				dao().clear("SYSTEM_USER_ROLE", Cnd.where("USERID", "=", userId));
				// 插入中间表记录
				dao().insertRelation(user, "roles");
			}
		});
		respData.setInfo("分配成功!");
		return respData;
	}

	public List<String> getRoleNameList(User user) {
		user = dao().fetchLinks(user, "roles");
		List<Role> roles = user.getRoles();
		List<String> roleNameList = new ArrayList<String>();
		for (Role role : roles) {
			roleNameList.add(role.getName());
		}
		return roleNameList;
	}

	@Aop(value = "log")
	public AjaxResData getCurrentRoleIdArr(Long userId) {
		AjaxResData respData = new AjaxResData();
		Sql sql = Sqls
				.create("SELECT ID FROM SYSTEM_ROLE WHERE ISORGARELA = @isOrgaRela AND ID IN (SELECT DISTINCT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID = @userId)");
		sql.params().set("isOrgaRela", false);
		sql.params().set("userId", userId);
		sql.setCallback(Sqls.callback.longs());
		dao().execute(sql);
		long[] currentRoleIDs = (long[]) sql.getResult();
		respData.setLogic(currentRoleIDs);
		return respData;
	}

	public long[] getCurrentPermissionIdList(Long userId) {
		String sqlStr = "SELECT ID from SYSTEM_PERMISSION WHERE ID IN (SELECT DISTINCT PERMISSIONID FROM SYSTEM_ROLE_PERMISSION WHERE ROLEID IN(SELECT ROLEID FROM SYSTEM_USER_ROLE WHERE USERID=$userId))";
		Sql sql = Sqls.create(sqlStr);
		sql.vars().set("userId", userId);
		sql.setCallback(Sqls.callback.longs());
		dao().execute(sql);
		long[] permissionIdArr = (long[]) sql.getResult();
		return permissionIdArr;
	}

	@Aop(value = "log")
	public AjaxResData updatePost(final Long userId, String orgId, final String[] postIds) {
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
			respData.setNotice("该用户未分配任何岗位!");
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
		respData.setInfo("分配成功!");
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData changePasswordForCurrentUser(User user, String oldPassword, String newPassword) {
		AjaxResData respData = new AjaxResData();
		// 对输入的原密码进行MD5加密
		String salt = user.getSalt();
		String hashedPasswordBase64 = new Sha256Hash(oldPassword, salt, 1024).toBase64();
		// 比较输入的原密码和数据库中的原密码
		int countAuthenticatedUser = count(Cnd.where("ID", "=", user.getId())
				.and("PASSWORD", "=", hashedPasswordBase64));
		if (countAuthenticatedUser == 0) {
			respData.setError("原密码错误!");
		} else {
			// 对输入的新密码进行MD5加密
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			salt = rng.nextBytes().toBase64();
			hashedPasswordBase64 = new Sha256Hash(newPassword, salt, 1024).toBase64();
			user.setSalt(salt);
			user.setPassword(hashedPasswordBase64);
			dao().update(user);
			respData.setInfo("密码修改成功!");
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData changePasswordForAUser(Long userId, String newPassword) {
		AjaxResData respData = new AjaxResData();
		if (userId > 0) {
			User user = new User();
			user.setId(userId);
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toBase64();
			String hashedPasswordBase64 = new Sha256Hash(newPassword, salt, 1024).toBase64();
			user.setSalt(salt);
			user.setPassword(hashedPasswordBase64);
			dao().updateIgnoreNull(user);
			respData.setInfo("密码修改成功!");
		} else {
			respData.setNotice("未正确选择用户");
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData importUsers(File f) {
		AjaxResData respData = new AjaxResData();
		if (f == null) {
			respData.setNotice("请选择正确的文件!");
			return respData;
		}

		HSSFWorkbook wb;
		List<Object> userList;
		try {
			wb = new HSSFWorkbook(new FileInputStream(f));
			HSSFSheet sheet = wb.getSheetAt(0);
			userList = ExcelHandler.readSheet(sheet, User.class);
		} catch (Exception e) {
			throw new RuntimeException("读取EXCEL文档出错!", e);
		}
		for (Object obj : userList) {
			User user = (User) obj;
			dao().insert(user);
		}
		f.delete();

		respData.setInfo("新增" + userList.size() + "位用户");
		return respData;
	}

	@Aop(value = "log")
	public JRHtmlExporter exportUsers(ServletContext context) {
		// 获取报表文件实际路径
		String reportFilePath = "/reports/users.jasper";
		reportFilePath = context.getRealPath(reportFilePath);
		// 设置参数
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "Users Report");
		// 设置数据源——此处直接写入数据
		List<User> list = this.query(null, null);
		int count = list.size();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", list.get(i).getName());
			map.put("number", list.get(i).getNumber());
			map.put("age", list.get(i).getAge());
			data.add(map);
		}
		WebappDataSource dataSource = new WebappDataSource();
		dataSource.setData(data);
		// 设置JRHtmlExporter对象
		JRHtmlExporter exporter = null;
		try {
			exporter = ReportHandler.export(reportFilePath, parameters, dataSource);
		} catch (Exception e) {
			throw new RuntimeException("报表导出失败!", e);
		}
		return exporter;
	}

	@Aop(value = "log")
	public Object[] exportUsers2() {
		// 设置参数
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "Users Report");
		// 设置数据源——此处直接写入数据
		List<User> list = this.query(null, null);
		int count = list.size();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", list.get(i).getName());
			map.put("number", list.get(i).getNumber());
			map.put("age", list.get(i).getAge());
			data.add(map);
		}
		WebappDataSource dataSource = new WebappDataSource();
		dataSource.setData(data);
		// 将参数和数据源放到一个长度为2的数组中
		Object[] reportContent = new Object[2];
		reportContent[0] = parameters;
		reportContent[1] = dataSource;
		return reportContent;
	}
}