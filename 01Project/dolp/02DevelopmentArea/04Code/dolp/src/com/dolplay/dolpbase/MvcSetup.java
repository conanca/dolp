package com.dolplay.dolpbase;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.dolplay.dolpbase.system.util.MvcSetupDefaultHandler;

public class MvcSetup implements Setup {

	/**
	 * 当应用系统启动的时候要做的操作
	 */
	@Override
	public void init(NutConfig config) {
		MvcSetupDefaultHandler.defaultInit(config);

		//此处添加自定义的操作如初始化数据表,增加调度任务等

		MvcSetupDefaultHandler.defaultCheck(config);
		MvcSetupDefaultHandler.startScheduler();
	}

	/**
	 * 当应用系统停止的时候要做的操作
	 */
	@Override
	public void destroy(NutConfig config) {
		MvcSetupDefaultHandler.defaultDestroy(config);
	}
}