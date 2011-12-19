package com.dolplay.dolpbase.common.util;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerHandler {

	public static Scheduler getScheduler() throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		return sched;
	}

	public static Date add(JobDetail job, Trigger trigger) throws SchedulerException {
		Date nextFireTime = trigger.getStartTime();

		Scheduler sched = getScheduler();
		if (!sched.checkExists(job.getKey()) && !sched.checkExists(trigger.getKey())) {
			sched.scheduleJob(job, trigger);
		} else {
			nextFireTime = sched.getTrigger(trigger.getKey()).getNextFireTime();
		}

		return nextFireTime;
	}

	public static void delete(JobDetail job) throws SchedulerException {
		Scheduler sched = getScheduler();
		if (!sched.checkExists(job.getKey())) {
			sched.deleteJob(job.getKey());
		}
	}
}
