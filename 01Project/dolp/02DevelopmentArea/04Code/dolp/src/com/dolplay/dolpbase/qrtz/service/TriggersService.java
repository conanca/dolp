package com.dolplay.dolpbase.qrtz.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.SchedulerHandler;
import com.dolplay.dolpbase.qrtz.domain.Triggers;

@IocBean(args = { "refer:dao" })
public class TriggersService extends DolpBaseService<Triggers> {

	public TriggersService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Triggers> getGridData(JqgridReqData jqReq, Boolean isSearch, Triggers triggersSearch) {
		Cnd cnd = null;
		//		if (isSearch && null != triggersSearch) {
		//			cnd = Cnd.where("1", "=", 1);
		//			Long id = triggersSearch.getId();
		//			if (null != id) {
		//				cnd.and("ID", "=", id);
		//			}
		//		}
		AdvancedJqgridResData<Triggers> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData isSchedulerStart() {
		AjaxResData resData = new AjaxResData();
		boolean isStart = false;
		try {
			isStart = SchedulerHandler.getScheduler().isStarted();
		} catch (SchedulerException e) {
			throw new RuntimeException("调度任务获取状态异常", e);
		}
		resData.setReturnData(isStart);
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData startScheduler() {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().start();
			resData.setReturnData(true);
			resData.setSystemMessage("已启动调度任务!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("启动调度任务时发生异常", e);
		}
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData shutdownScheduler() {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().shutdown();
			resData.setReturnData(true);
			resData.setSystemMessage("已关闭调度任务!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("关闭调度任务时发生异常", e);
		}
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData pauseTrigger(String triggerName, String triggerGroup) {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().pauseTrigger(new TriggerKey(triggerName, triggerGroup));
			resData.setSystemMessage("已暂停触发器!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("暂停触发器时发生异常", e);
		}
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData resumeTrigger(String triggerName, String triggerGroup) {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().resumeTrigger(new TriggerKey(triggerName, triggerGroup));
			resData.setSystemMessage("已恢复触发器!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("恢复触发器时发生异常", e);
		}
		return resData;
	}
}