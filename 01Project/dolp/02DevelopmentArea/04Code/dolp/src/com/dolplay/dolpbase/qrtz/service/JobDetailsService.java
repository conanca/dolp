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
import com.dolplay.dolpbase.qrtz.domain.JobDetails;

@IocBean(args = { "refer:dao" })
public class JobDetailsService extends DolpBaseService<JobDetails> {

	public JobDetailsService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<JobDetails> getGridData(JqgridReqData jqReq, Boolean isSearch,
			JobDetails jobDetailsSearch) {
		Cnd cnd = null;
		//		if (isSearch && null != jobDetailsSearch) {
		//			cnd = Cnd.where("1", "=", 1);
		//			Long id = jobDetailsSearch.getId();
		//			if (null != id) {
		//				cnd.and("ID", "=", id);
		//			}
		//		}
		AdvancedJqgridResData<JobDetails> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
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
}