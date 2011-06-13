package gs.dolp.common.schedule;

import gs.dolp.common.util.DaoHandler;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountOnlineUserJob implements Job {

	private static Logger log = LoggerFactory.getLogger(CountOnlineUserJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		log.info(jobKey + " executing at " + new Date());
		int onlineUserCount = DaoHandler.getDao().count("SYSTEM_ONLINE_USER");
		log.info("当前在线终端数目：" + onlineUserCount);
	}

}
