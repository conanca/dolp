package com.dolplay.dolpbase;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.MVCHandler;
import com.dolplay.dolpbase.system.secheduler.DolpScheduler;

public class MvcSetup implements Setup {
	private static Logger logger = LoggerFactory.getLogger(MvcSetup.class);

	/**
	 * 当应用系统启动的时候:
	 * 1.默认的应用启动时要做的操作
	 * 2.启动调度程序
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
		try {
			DolpScheduler.stop();
		} catch (Exception e) {
			logger.error("Stop SchedulerRunner exception", e);
		}
	}
}