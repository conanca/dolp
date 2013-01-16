package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_JOB_DETAILS")
@PK({ "schedName", "jobName", "jobGroup" })
public class JobDetails {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
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
	@Column("JOB_CLASS_NAME")
	private String jobClassName;
	/**
	 * 
	 */
	@Column("IS_DURABLE")
	private Boolean isDurable;
	/**
	 * 
	 */
	@Column("IS_NONCONCURRENT")
	private Boolean isNonconcurrent;
	/**
	 * 
	 */
	@Column("IS_UPDATE_DATA")
	private Boolean isUpdateData;
	/**
	 * 
	 */
	@Column("REQUESTS_RECOVERY")
	private Boolean requestsRecovery;
	/**
	 * 
	 */
	@Column("JOB_DATA")
	private java.sql.Blob jobData;
}