package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_PAUSED_TRIGGER_GRPS")
@PK({ "schedName", "triggerGroup" })
public class PausedTriggerGrps {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("TRIGGER_GROUP")
	private String triggerGroup;
}