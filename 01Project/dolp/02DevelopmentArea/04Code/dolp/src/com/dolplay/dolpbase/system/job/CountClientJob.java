package com.dolplay.dolpbase.system.job;


import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoHandler;

public class CountClientJob implements Job {

	private static Logger log = LoggerFactory.getLogger(CountClientJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		log.debug(jobKey + " executing at " + new Date());
		int clientCount = DaoHandler.getDao().count("SYSTEM_CLIENT");
		log.info(new Date() + " 当前在线终端数目：" + clientCount);
	}

}
