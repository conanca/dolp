package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_SCHEDULER_STATE")
@PK({ "schedName", "instanceName" })
public class SchedulerState {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("INSTANCE_NAME")
	private String instanceName;
	/**
	 * 
	 */
	@Column("LAST_CHECKIN_TIME")
	private Long lastCheckinTime;
	/**
	 * 
	 */
	@Column("CHECKIN_INTERVAL")
	private Long checkinInterval;
}