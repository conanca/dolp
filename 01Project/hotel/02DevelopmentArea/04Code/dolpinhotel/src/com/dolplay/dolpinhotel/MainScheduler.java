package com.dolplay.dolpinhotel;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.system.job.CountClientJob;

/**
 * 随服务一起运行的Scheduler
 * @author Administrator
 *
 */
public class MainScheduler {
	private static Logger logger = LoggerFactory.getLogger(MainScheduler.class);

	public void run() throws Exception {
		logger.info("------- scheduler初始化 ----------------------");
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		logger.info("------- scheduler初始化完成 ------------------");

		// 设置Job：CountClientJob
		JobDetail job = JobBuilder.newJob(CountClientJob.class).withIdentity("job1", "group1").build();
		// 设置开始时间(找个整的分钟)
		Date startTime = DateBuilder.evenMinuteDate(new Date());
		// 设置触发器：每1小时运行一次，无限次运行
		SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_1", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build();
		// job和trigger不存在于Scheduler，才加入调度
		if (!sched.checkExists(job.getKey()) && !sched.checkExists(trigger.getKey())) {
			sched.scheduleJob(job, trigger);
		}
		logger.info(job.getKey() + " 将会重复: " + trigger.getRepeatCount() + " 次, 每 " + trigger.getRepeatInterval() / 1000
				+ " 秒重复一次");

		logger.info("------- 正在启动 Scheduler -------------------");
		sched.start();
		logger.info("------- 已启动 Scheduler ---------------------");
	}

	public void stop() throws Exception {
		logger.info("------- scheduler初始化 ----------------------");
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		logger.info("------- scheduler初始化完成 ------------------");
		sched.shutdown();
	}
}