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
		MvcSetupDefaultHandler.defaultCheck(config);
	}

	/**
	 * 当应用系统停止的时候要做的操作
	 */
	@Override
	public void destroy(NutConfig config) {
		MvcSetupDefaultHandler.defaultDestroy(config);
	}
}