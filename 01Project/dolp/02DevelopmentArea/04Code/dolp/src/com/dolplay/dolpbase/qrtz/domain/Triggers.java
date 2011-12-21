package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_TRIGGERS")
@PK({ "schedName", "triggerName", "triggerGroup" })
public class Triggers {

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
	@Column("DESCRIPTION")
	private String description;
	/**
	 * 
	 */
	@Column("NEXT_FIRE_TIME")
	private Long nextFireTime;
	/**
	 * 
	 */
	@Column("PREV_FIRE_TIME")
	private Long prevFireTime;
	/**
	 * 
	 */
	@Column("PRIORITY")
	private Integer priority;
	/**
	 * 
	 */
	@Column("TRIGGER_STATE")
	private String triggerState;
	/**
	 * 
	 */
	@Column("TRIGGER_TYPE")
	private String triggerType;
	/**
	 * 
	 */
	@Column("START_TIME")
	private Long startTime;
	/**
	 * 
	 */
	@Column("END_TIME")
	private Long endTime;
	/**
	 * 
	 */
	@Column("CALENDAR_NAME")
	private String calendarName;
	/**
	 * 
	 */
	@Column("MISFIRE_INSTR")
	private Integer misfireInstr;
	/**
	 * 
	 */
	@Column("JOB_DATA")
	private java.sql.Blob jobData;
}