package com.dolplay.dolpbase.qrtz.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.SchedulerHandler;
import com.dolplay.dolpbase.qrtz.domain.JobDetails;

@IocBean(args = { "refer:dao" })
public class JobDetailsService extends DolpBaseService<JobDetails> {

	public JobDetailsService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<JobDetails> getGridData(final JqgridReqData jqReq, Boolean isSearch,
			JobDetails jobDetailsSearch) {
		//Cnd cnd = null;
		//		if (isSearch && null != jobDetailsSearch) {
		//			cnd = Cnd.where("1", "=", 1);
		//			Long id = jobDetailsSearch.getId();
		//			if (null != id) {
		//				cnd.and("ID", "=", id);
		//			}
		//		}

		final Object[] objs = new Object[1];
		FieldFilter.create(JobDetails.class, null, "jobData", true).run(new Atom() {
			public void run() {
				objs[0] = getAdvancedJqgridRespData(null, jqReq);
			}
		});
		return (AdvancedJqgridResData<JobDetails>) objs[0];
	}

	@Aop(value = "log")
	public AjaxResData getJobDetail(String schedName, String jobName, String jobGroup) {
		AjaxResData resData = new AjaxResData();
		Condition cnd = Cnd.where("schedName", "=", schedName).and("jobName", "=", jobName)
				.and("jobGroup", "=", jobGroup);
		JobDetails jobDetail = fetch(cnd);
		if (null == jobDetail) {
			resData.setSystemMessage(null, "获取作业失败", null);
			return resData;
		}
		resData.setReturnData(jobDetail);
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData pauseJob(String jobName, String jobGroup) {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().pauseJob(new JobKey(jobName, jobGroup));
			resData.setSystemMessage("已暂停作业!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("暂停作业时发生异常", e);
		}
		return resData;
	}

	@Aop(value = "log")
	public AjaxResData resumeJob(String jobName, String jobGroup) {
		AjaxResData resData = new AjaxResData();
		try {
			SchedulerHandler.getScheduler().resumeJob(new JobKey(jobName, jobGroup));
			resData.setSystemMessage("已恢复作业!", null, null);
		} catch (SchedulerException e) {
			throw new RuntimeException("恢复作业时发生异常", e);
		}
		return resData;
	}
}