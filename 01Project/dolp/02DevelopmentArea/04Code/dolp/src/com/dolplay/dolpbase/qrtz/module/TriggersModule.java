package com.dolplay.dolpbase.qrtz.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.qrtz.domain.Triggers;
import com.dolplay.dolpbase.qrtz.service.CronTriggersService;
import com.dolplay.dolpbase.qrtz.service.JobDetailsService;
import com.dolplay.dolpbase.qrtz.service.SimpleTriggersService;
import com.dolplay.dolpbase.qrtz.service.TriggersService;

@IocBean
@At("/qrtz/triggers")
public class TriggersModule {

	@Inject
	private TriggersService triggersService;
	@Inject
	private SimpleTriggersService simpleTriggersService;
	@Inject
	private CronTriggersService cronTriggersService;
	@Inject
	private JobDetailsService jobDetailsService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Triggers triggersSearch) {
		return triggersService.getGridData(jqReq, isSearch, triggersSearch);
	}

	@At
	public ResponseData isSchedulerStart() {
		return triggersService.isSchedulerStart();
	}

	@At
	public ResponseData startScheduler() {
		return triggersService.startScheduler();
	}

	@At
	public ResponseData shutdownScheduler() {
		return triggersService.shutdownScheduler();
	}

	@At
	public ResponseData getSimpleTrigger(@Param("schedName") String schedName,
			@Param("triggerName") String triggerName, @Param("triggerGroup") String triggerGroup) {
		return simpleTriggersService.getSimpleTrigger(schedName, triggerName, triggerGroup);
	}

	@At
	public ResponseData getCronTrigger(@Param("schedName") String schedName, @Param("triggerName") String triggerName,
			@Param("triggerGroup") String triggerGroup) {
		return cronTriggersService.getCronTrigger(schedName, triggerName, triggerGroup);
	}

	@At
	public ResponseData getJobDetail(@Param("schedName") String schedName, @Param("triggerName") String triggerName,
			@Param("triggerGroup") String triggerGroup) {
		return jobDetailsService.getJobDetail(schedName, triggerName, triggerGroup);
	}

	@At
	public ResponseData pauseTrigger(@Param("triggerName") String triggerName,
			@Param("triggerGroup") String triggerGroup) {
		return triggersService.pauseTrigger(triggerName, triggerGroup);
	}

	@At
	public ResponseData resumeTrigger(@Param("triggerName") String triggerName,
			@Param("triggerGroup") String triggerGroup) {
		return triggersService.resumeTrigger(triggerName, triggerGroup);
	}
}