package com.dolplay.dolpbase.qrtz.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("QRTZ_SIMPROP_TRIGGERS")
@PK({ "schedName", "triggerName", "triggerGroup" })
public class SimpropTriggers {

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
	@Column("STR_PROP_1")
	private String strProp1;
	/**
	 * 
	 */
	@Column("STR_PROP_2")
	private String strProp2;
	/**
	 * 
	 */
	@Column("STR_PROP_3")
	private String strProp3;
	/**
	 * 
	 */
	@Column("INT_PROP_1")
	private Integer intProp1;
	/**
	 * 
	 */
	@Column("INT_PROP_2")
	private Integer intProp2;
	/**
	 * 
	 */
	@Column("LONG_PROP_1")
	private Long longProp1;
	/**
	 * 
	 */
	@Column("LONG_PROP_2")
	private Long longProp2;
	/**
	 * 
	 */
	@Column("DEC_PROP_1")
	private Long decProp1;
	/**
	 * 
	 */
	@Column("DEC_PROP_2")
	private Long decProp2;
	/**
	 * 
	 */
	@Column("BOOL_PROP_1")
	private Boolean boolProp1;
	/**
	 * 
	 */
	@Column("BOOL_PROP_2")
	private Boolean boolProp2;
}