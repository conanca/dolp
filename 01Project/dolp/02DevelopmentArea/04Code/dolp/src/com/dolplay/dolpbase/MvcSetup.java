package com.dolplay.dolpbase;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.common.util.MVCHandler;
import com.dolplay.dolpbase.system.secheduler.DolpScheduler;

public class MvcSetup implements Setup {
	private static Logger logger = LoggerFactory.getLogger(MvcSetup.class);

	/**
	 * 当应用系统启动的时候:
	 * 1.自动检查数据库，如果必要的数据表不存在，创建它们并创建默认的记录;
	 * 2.清空在线用户表;
	 * 3.启动调度程序
	 */
	@Override
	public void init(NutConfig config) {
		// 默认的应用启动时要做的操作
		MVCHandler.defaultInit(config);
		// 启动默认调度任务
		try {
			DolpScheduler.run();
		} catch (Exception e) {
			logger.error("Start SchedulerRunner exception", e);
		}
	}

	/**
	 * 当应用系统停止的时候:
	 * 1.清空在线用户表;
	 * 2.停止调度程序;
	 */
	@Override
	public void destroy(NutConfig config) {
		// 清空在线用户表
		DaoProvider.getDao().clear("SYSTEM_CLIENT");
		try {
			DolpScheduler.stop();
		} catch (Exception e) {
			logger.error("Stop SchedulerRunner exception", e);
		}
	}
}