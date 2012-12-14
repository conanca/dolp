package com.dolplay.dolpbase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.common.util.IocProvider;
import com.dolplay.dolpbase.system.secheduler.DolpSchedulerAdder;
import com.dolplay.dolpbase.system.util.MvcSetupDefaultHandler;

public class MvcSetup implements Setup {
	private static Logger logger = LoggerFactory.getLogger(MvcSetup.class);

	/**
	 * 当应用系统启动的时候要做的操作
	 */
	@Override
	public void init(NutConfig config) {
		// 初始化IocProvider
		IocProvider.init(Mvcs.getIoc());
		// 初始化dolp的数据表
		MvcSetupDefaultHandler.dolpTableInit();

		/**
		 * 此处添加自定义的操作如初始化数据表,增加调度任务等
		 * 
		 * 
		 */

		// 进行必要的检查操作
		//MvcSetupDefaultHandler.defaultCheck();
		// 增加两个dolp的调度任务
		try {
			DolpSchedulerAdder.add();
		} catch (Exception e) {
			logger.error("增加默认调度任务时发生异常", e);
		}
		// 启动调度任务
		// TODO 关闭调度
		//MvcSetupDefaultHandler.startScheduler();
		// 清空在线用户表
		DaoProvider.getDao().clear("SYSTEM_CLIENT");

		// 设置 Shiro 的 securityManager
		SecurityManager securityManager = IocProvider.getIoc().get(SecurityManager.class);
		SecurityUtils.setSecurityManager(securityManager);

	}

	/**
	 * 当应用系统停止的时候要做的操作
	 */
	@Override
	public void destroy(NutConfig config) {
		MvcSetupDefaultHandler.defaultDestroy();
	}
}