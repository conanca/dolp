package com.dolplay.dolpbase.qrtz.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.qrtz.domain.CronTriggers;

@IocBean(args = { "refer:dao" })
public class CronTriggersService extends DolpBaseService<CronTriggers> {

	public CronTriggersService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<CronTriggers> getGridData(JqgridReqData jqReq, Boolean isSearch,
			CronTriggers cronTriggersSearch) {
		Cnd cnd = null;
		//		if (isSearch && null != cronTriggersSearch) {
		//			cnd = Cnd.where("1", "=", 1);
		//			Long id = cronTriggersSearch.getId();
		//			if (null != id) {
		//				cnd.and("ID", "=", id);
		//			}
		//		}
		AdvancedJqgridResData<CronTriggers> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData getCronTrigger(String schedName, String triggerName, String triggerGroup) {
		AjaxResData resData = new AjaxResData();
		Condition cnd = Cnd.where("schedName", "=", schedName).and("triggerName", "=", triggerName)
				.and("triggerGroup", "=", triggerGroup);
		CronTriggers cronTrigger = fetch(cnd);
		if (null == cronTrigger) {
			resData.setError("获取触发器详情失败");
			return resData;
		}
		resData.setLogic(cronTrigger);
		return resData;
	}
}