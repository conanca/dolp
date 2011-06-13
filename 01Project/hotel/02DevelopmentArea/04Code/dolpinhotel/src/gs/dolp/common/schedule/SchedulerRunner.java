package gs.dolp.common.schedule;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;

import java.util.Date;

import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerRunner {

	private static Logger log = LoggerFactory.getLogger(SchedulerRunner.class);

	public void run() throws Exception {
		log.info("------- scheduler初始化 ----------------------");
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		log.info("------- scheduler初始化完成 ------------------");

		// 设置Job：CountOnlineUserJob
		JobDetail job = newJob(CountOnlineUserJob.class).withIdentity("job1", "group1").build();
		// 设置开始时间为：5秒后
		Date startTime = futureDate(5, IntervalUnit.SECOND);
		// 设置触发器：每30秒运行一次，无限运行
		SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_1", "group1").startAt(startTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();
		// job和trigger不存在于Scheduler，才加入调度
		if (!sched.checkExists(job.getKey()) && !sched.checkExists(trigger.getKey())) {
			sched.scheduleJob(job, trigger);
		}
		log.info(job.getKey() + "会重复: " + trigger.getRepeatCount() + " 次, 每 " + trigger.getRepeatInterval() / 1000
				+ " 秒重复一次");

		log.info("------- 正在启动 Scheduler -------------------");
		sched.start();
		log.info("------- 已启动 Scheduler ---------------------");
	}

	public void stop() throws Exception {
		log.info("------- scheduler初始化 ----------------------");
		// 获取scheduler的引用
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		log.info("------- scheduler初始化完成 ------------------");
		sched.shutdown();
	}
}
