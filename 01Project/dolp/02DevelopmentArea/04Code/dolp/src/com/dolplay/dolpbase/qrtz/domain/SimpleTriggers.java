package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_SIMPLE_TRIGGERS")
@PK({ "schedName", "triggerName", "triggerGroup" })
public class SimpleTriggers {

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
	@Column("REPEAT_COUNT")
	private Long repeatCount;
	/**
	 * 
	 */
	@Column("REPEAT_INTERVAL")
	private Long repeatInterval;
	/**
	 * 
	 */
	@Column("TIMES_TRIGGERED")
	private Long timesTriggered;
}