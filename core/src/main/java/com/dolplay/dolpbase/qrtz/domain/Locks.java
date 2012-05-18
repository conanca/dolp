package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("QRTZ_LOCKS")
@PK({ "schedName", "lockName" })
public class Locks {

	/**
	 * 
	 */
	@Column("SCHED_NAME")
	private String schedName;
	/**
	 * 
	 */
	@Column("LOCK_NAME")
	private String lockName;
}