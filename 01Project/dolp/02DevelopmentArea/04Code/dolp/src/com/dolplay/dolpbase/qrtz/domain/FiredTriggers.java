package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_FIRED_TRIGGERS")
@PK({ "schedName", "entryId" })
public class FiredTriggers {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("ENTRY_ID")
	private String entryId;
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
	@Column("INSTANCE_NAME")
	private String instanceName;
	/**
	 * 
	 */
	@Column("FIRED_TIME")
	private Long firedTime;
	/**
	 * 
	 */
	@Column("PRIORITY")
	private Integer priority;
	/**
	 * 
	 */
	@Column("STATE")
	private String state;
	/**
	 * 
	 */
	@Column("JOB_NAME")
	private String jobName;
	/**
	 * 
	 */
	@Column("JOB_GROUP")
	private String jobGroup;
	/**
	 * 
	 */
	@Column("IS_NONCONCURRENT")
	private Boolean isNonconcurrent;
	/**
	 * 
	 */
	@Column("REQUESTS_RECOVERY")
	private Boolean requestsRecovery;
}