package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_BLOB_TRIGGERS")
public class BlobTriggers {

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
	@Column("BLOB_DATA")
	private java.sql.Blob blobData;
}