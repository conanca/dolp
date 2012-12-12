package com.dolplay.dolpbase.system.util;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
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
import com.dolplay.dolpbase.system.domain.Permission;
import com.dolplay.dolpbase.system.domain.PoolFile;
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
			dao.create(Permission.class, true);
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

}