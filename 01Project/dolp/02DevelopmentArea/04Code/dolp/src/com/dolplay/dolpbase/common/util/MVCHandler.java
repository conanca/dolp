package com.dolplay.dolpbase.common.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.NutConfig;

import com.dolplay.dolpbase.system.domain.Client;
import com.dolplay.dolpbase.system.domain.Menu;
import com.dolplay.dolpbase.system.domain.Message;
import com.dolplay.dolpbase.system.domain.Organization;
import com.dolplay.dolpbase.system.domain.PoolFile;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.domain.SysEnum;
import com.dolplay.dolpbase.system.domain.SysEnumItem;
import com.dolplay.dolpbase.system.domain.SysPara;
import com.dolplay.dolpbase.system.domain.User;

public class MVCHandler {

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 默认的应用启动时要做的操作
	 * @param config
	 */
	public static void defaultInit(NutConfig config) {
		// 初始化dao,datasource及properties
		DaoProvider.init(config.getIoc());
		Dao dao = DaoProvider.getDao();
		IocObjectProvider.init(config.getIoc());
		// 初始化系统基本的数据表
		if (!dao.exists("SYSTEM_USER")) {
			// 建实体类的表
			dao.create(Client.class, true);
			dao.create(Menu.class, true);
			dao.create(Message.class, true);
			dao.create(Organization.class, true);
			dao.create(Privilege.class, true);
			dao.create(Role.class, true);
			dao.create(SysEnum.class, true);
			dao.create(SysEnumItem.class, true);
			dao.create(SysPara.class, true);
			dao.create(User.class, true);
			dao.create(PoolFile.class, true);

			// 添加默认记录
			FileSqlManager fm = new FileSqlManager("init_system_h2.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}
		// 初始化quartz的数据表
		if (!dao.exists("QRTZ_JOB_DETAILS")) {
			FileSqlManager fm = new FileSqlManager("tables_quartz_h2.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}

		// 清空在线用户表
		dao.clear("SYSTEM_CLIENT");

		// 获取权限表中所有的方法列表
		List<Privilege> privilegeList = dao.query(Privilege.class, null, null);
		Set<String> dbMethodPaths = new HashSet<String>();
		for (Privilege privilege : privilegeList) {
			dbMethodPaths.add(privilege.getMethodPath());
		}
		// 获取系统所有的入口方法
		Map<String, Method> map = config.getAtMap().getMethodMapping();
		// 如果有一个入口方法不属于SystemModule类的并且不存在于权限表中，则抛出异常
		for (String reqPath : map.keySet()) {
			Method method = map.get(reqPath);
			String methodpath = method.getDeclaringClass().getName() + "." + method.getName();
			if (!methodpath.contains("com.dolplay.dolpbase.system.module.SystemModule")
					&& !dbMethodPaths.contains(methodpath)) {
				RuntimeException e = new RuntimeException("权限表中无该方法:" + methodpath);
				throw e;
			}
		}
	}
}