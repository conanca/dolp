package com.dolplay.dolpbase.system.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.Mvcs;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.common.util.PropProvider;
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

public class MvcSetupDefaultHandler {
	private static Logger logger = LoggerFactory.getLogger(MvcSetupDefaultHandler.class);

	/**
	 * 默认的应用启动时要做的操作
	 * @param config
	 */
	public static void dolpTableInit() {
		Dao dao = DaoProvider.getDao();
		// 初始化系统基本的数据表
		Boolean initDolpTables = PropProvider.getProp().getBoolean("SYSTEM_INITDOLPTABLES_ONSTART");
		if (null != initDolpTables && initDolpTables) {
			logger.info("初始化Dolp数据表");
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
			// 初始化quartz的数据表
			fm = new FileSqlManager("tables_quartz_h2.sql");
			sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}
	}

	public static void startScheduler() {
		// 启动调度任务
		Boolean dolpschedulerRun = PropProvider.getProp().getBoolean("SYSTEM_SCHEDULER_RUN");
		if (null != dolpschedulerRun && dolpschedulerRun) {
			logger.info("启动调度任务");
			try {
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();
				sched.start();
			} catch (Exception e) {
				logger.error("启动调度任务时发生异常", e);
			}
		} else {
			logger.info("不启动调度任务");
		}
	}

	/**
	 *  默认的应用停止时要做的操作
	 * @param config
	 */
	public static void defaultDestroy() {
		// 关闭调度任务
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			sched.shutdown();
		} catch (Exception e) {
			logger.error("关闭调度任务时发生异常", e);
		}
	}

	public static void defaultCheck() {
		// 检查入口方法存在于权限表中
		Boolean isCheck = PropProvider.getProp().getBoolean("SYSTEM_ISCHECK_METHOD");
		if (null != isCheck && isCheck) {
			logger.info("检查入口方法存在于权限表中");
			Dao dao = DaoProvider.getDao();
			// 获取权限表中所有的方法列表
			List<Privilege> privilegeList = dao.query(Privilege.class, null, null);
			Set<String> dbMethodPaths = new HashSet<String>();
			for (Privilege privilege : privilegeList) {
				dbMethodPaths.add(privilege.getMethodPath());
			}
			// 获取系统所有的入口方法
			Map<String, Method> map = Mvcs.getAtMap().getMethodMapping();
			logger.debug("系统共有" + map.size() + "个入口方法,权限表中存有" + dbMethodPaths.size() + "个入口方法，请确认SystemModule类中是否只有"
					+ (map.size() - dbMethodPaths.size()) + "个入口方法");
			// 如果发现有入口方法不属于SystemModule类的并且不存在于权限表中，则将错误信息记入日志
			for (String reqPath : map.keySet()) {
				Method method = map.get(reqPath);
				String methodpath = method.getDeclaringClass().getName() + "." + method.getName();
				if (!methodpath.contains("com.dolplay.dolpbase.system.module.SystemModule")
						&& !dbMethodPaths.contains(methodpath)) {
					logger.error("注意!权限表中无该方法:" + methodpath);
				}
			}
		}
	}
}