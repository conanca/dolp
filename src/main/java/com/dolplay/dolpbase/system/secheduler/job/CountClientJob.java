package com.dolplay.dolpbase.system.secheduler.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DolpWebs;

public class CountClientJob implements Job {
	private static Logger logger = LoggerFactory.getLogger(CountClientJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		int clientCount = DolpWebs.dao().count("SYSTEM_CLIENT");
		StringBuilder sb = new StringBuilder();
		sb.append(new Date()).append("当前在线终端数目：").append(clientCount);
		logger.info(sb.toString());
	}
}