package com.dolplay.dolpbase.system.secheduler;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.system.secheduler.job.CountClientJob;
import com.dolplay.dolpbase.system.secheduler.job.RemoveInvalidAttachmentJob;

/**
 * 随服务一起运行的Scheduler
 * @author Administrator
 *
 */
public class DolpScheduler {
	private static Logger logger = LoggerFactory.getLogger(DolpScheduler.class);

	public static void run() throws Exception {
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		Date startTime = DateBuilder.evenMinuteDate(new Date());
		Date nextFireTime = startTime;

		// 设置CountClientJob及其触发器——每小时运行一次，无限重复运行
		JobDetail countClientJob = JobBuilder.newJob(CountClientJob.class).withIdentity("countClientJob", "group1")
				.build();
		SimpleTrigger inHoursTrigger = TriggerBuilder.newTrigger().withIdentity("inHoursTrigger", "group1")
				.startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build();
		if (!sched.checkExists(countClientJob.getKey()) && !sched.checkExists(inHoursTrigger.getKey())) {
			sched.scheduleJob(countClientJob, inHoursTrigger);
		} else {
			nextFireTime = sched.getTrigger(inHoursTrigger.getKey()).getNextFireTime();
		}
		logger.info(countClientJob.getKey() + " 将开始于: " + nextFireTime + " 将会重复: " + inHoursTrigger.getRepeatCount()
				+ " 次, 每 " + inHoursTrigger.getRepeatInterval() / 1000 + " 秒重复一次");

		// 设置RemoveInvalidAttachmentJob及其触发器,只运行一次
		JobDetail removeInvalidAttachmentJob = JobBuilder.newJob(RemoveInvalidAttachmentJob.class)
				.withIdentity("removeInvalidAttachmentJob", "group1").build();
		Trigger onceTrigger = TriggerBuilder.newTrigger().withIdentity("onceTrigger", "group1").startAt(startTime)
				.build();
		if (!sched.checkExists(removeInvalidAttachmentJob.getKey()) && !sched.checkExists(onceTrigger.getKey())) {
			sched.scheduleJob(removeInvalidAttachmentJob, onceTrigger);
		}
		logger.info(removeInvalidAttachmentJob.getKey() + " 将开始于: " + onceTrigger.getStartTime());

		// 启动scheduler
		sched.start();
	}

	public static void stop() throws Exception {
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.shutdown();
	}
}