package com.dolplay.dolpbase.system.util;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.resource.Scans;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DolpWebs;

public class MvcSetupDefaultHandler {
	private static Logger logger = LoggerFactory.getLogger(MvcSetupDefaultHandler.class);

	/**
	 * 默认的应用启动时要做的操作
	 * @param config
	 */
	public static void dolpTableInit() {
		Dao dao = DolpWebs.dao();
		// 初始化系统基本的数据表
		String initDolpTables = DolpWebs.config().get("SYSTEM_INITDOLPTABLES_ONSTART");
		if (null != initDolpTables && initDolpTables.toUpperCase().equals("TRUE")) {
			logger.info("初始化Dolp数据表");
			// 批量建表
			for (Class<?> klass : Scans.me().scanPackage("com.dolplay.dolpbase.system.domain")) {
				if (klass.getAnnotation(Table.class) != null) {
					dao.create(klass, true);
				}
			}
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
		String dolpschedulerRun = DolpWebs.config().get("SYSTEM_SCHEDULER_RUN");
		if (null != dolpschedulerRun && dolpschedulerRun.toUpperCase().equals("TRUE")) {
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