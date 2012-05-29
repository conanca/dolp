package com.dolplay.dolpbase.common.util;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.OperableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerHandler {
	private static Logger logger = LoggerFactory.getLogger(SchedulerHandler.class);

	public static Scheduler getScheduler() {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = null;
		try {
			sched = sf.getScheduler();
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
		return sched;
	}

	public static Date add(JobDetail job, Trigger trigger) {
		Date nextFireTime = null;

		Scheduler sched;
		try {
			sched = getScheduler();
			if (!sched.checkExists(job.getKey()) && !sched.checkExists(trigger.getKey())) {
				sched.scheduleJob(job, trigger);
				nextFireTime = TriggerUtils.computeFireTimes((OperableTrigger) trigger, null, 1).get(0);
			} else {
				nextFireTime = sched.getTrigger(trigger.getKey()).getNextFireTime();
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}

		return nextFireTime;
	}

	public static void delete(JobDetail job) {
		Scheduler sched = getScheduler();
		try {
			if (!sched.checkExists(job.getKey())) {
				sched.deleteJob(job.getKey());
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
	}

	public static void pauseTrigger(TriggerKey key) {
		Scheduler sched = getScheduler();
		try {
			if (!sched.checkExists(key)) {
				sched.pauseTrigger(key);
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
	}

	public static void resumeTrigger(TriggerKey key) {
		Scheduler sched = getScheduler();
		try {
			if (!sched.checkExists(key)) {
				sched.resumeTrigger(key);
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
	}

	public static void pauseJob(JobKey key) {
		Scheduler sched = getScheduler();
		try {
			if (!sched.checkExists(key)) {
				sched.pauseJob(key);
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
	}

	public static void resumeJob(JobKey key) {
		Scheduler sched = getScheduler();
		try {
			if (!sched.checkExists(key)) {
				sched.resumeJob(key);
			}
		} catch (SchedulerException e) {
			logger.error("调度任务异常", e);
		}
	}
}
