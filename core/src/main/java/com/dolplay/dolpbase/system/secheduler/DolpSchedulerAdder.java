package com.dolplay.dolpbase.system.secheduler;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.SchedulerHandler;
import com.dolplay.dolpbase.system.secheduler.job.CountClientJob;
import com.dolplay.dolpbase.system.secheduler.job.RemoveInvalidAttachmentJob;

/**
 * Dolp的默认调度任务添加器
 * @author Administrator
 *
 */
public class DolpSchedulerAdder {
	private static Logger logger = LoggerFactory.getLogger(DolpSchedulerAdder.class);

	public static void add() throws Exception {

		Date startTime = DateBuilder.evenMinuteDate(new Date());
		Date nextFireTime = startTime;

		// 设置CountClientJob及其触发器——每小时运行一次，无限重复运行
		JobDetail countClientJob = JobBuilder.newJob(CountClientJob.class)
				.withIdentity("CountClientJob", "DolpScheduler").build();
		SimpleTrigger inHoursTrigger = TriggerBuilder.newTrigger().withIdentity("EveryHourTrigger", "DolpScheduler")
				.startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build();
		nextFireTime = SchedulerHandler.add(countClientJob, inHoursTrigger);
		logger.info(countClientJob.getKey() + " 将开始于: " + nextFireTime + " 将会重复: " + inHoursTrigger.getRepeatCount()
				+ " 次, 每 " + inHoursTrigger.getRepeatInterval() / 1000 + " 秒重复一次");

		// 设置RemoveInvalidAttachmentJob及其触发器,只运行一次
		JobDetail removeInvalidAttachmentJob = JobBuilder.newJob(RemoveInvalidAttachmentJob.class)
				.withIdentity("RemoveInvalidAttachmentJob", "DolpScheduler").build();
		Trigger onceTrigger = TriggerBuilder.newTrigger().withIdentity("OnceTrigger", "DolpScheduler")
				.startAt(startTime).build();
		nextFireTime = SchedulerHandler.add(removeInvalidAttachmentJob, onceTrigger);
		logger.info(removeInvalidAttachmentJob.getKey() + " 将开始于: " + nextFireTime);
	}
}