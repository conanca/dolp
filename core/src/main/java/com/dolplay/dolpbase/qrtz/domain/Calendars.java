package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_CALENDARS")
@PK({ "schedName", "calendarName" })
public class Calendars {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("CALENDAR_NAME")
	private String calendarName;
	/**
	 * 
	 */
	@Column("CALENDAR")
	private java.sql.Blob calendar;
}