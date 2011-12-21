package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_CRON_TRIGGERS")
@PK({ "schedName", "triggerName", "triggerGroup" })
public class CronTriggers {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("TRIGGER_NAME")
	private String triggerName;
	/**
	 * 
	 */
	@Column("TRIGGER_GROUP")
	private String triggerGroup;
	/**
	 * 
	 */
	@Column("CRON_EXPRESSION")
	private String cronExpression;
	/**
	 * 
	 */
	@Column("TIME_ZONE_ID")
	private String timeZoneId;
}