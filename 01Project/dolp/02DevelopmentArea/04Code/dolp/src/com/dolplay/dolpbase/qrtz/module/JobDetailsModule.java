package com.dolplay.dolpbase.qrtz.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.qrtz.domain.JobDetails;
import com.dolplay.dolpbase.qrtz.service.JobDetailsService;

@IocBean
@At("/qrtz/jobDetails")
public class JobDetailsModule {

	@Inject
	private JobDetailsService jobDetailsService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") JobDetails jobDetailsSearch) {
		return jobDetailsService.getGridData(jqReq, isSearch, jobDetailsSearch);
	}

	@At
	public ResponseData pauseJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup) {
		return jobDetailsService.pauseJob(jobName, jobGroup);
	}

	@At
	public ResponseData resumeJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup) {
		return jobDetailsService.resumeJob(jobName, jobGroup);
	}
}