package com.dolplay.dolpbase.system.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_SYSENUMITEM")
public class SysEnumItem {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String text;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String value;
	@Column
	private Integer sysEnumId;
	@One(target = SysEnum.class, field = "sysEnumId")
	private SysEnum sysEnum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSysEnumId() {
		return sysEnumId;
	}

	public void setSysEnumId(Integer sysEnumId) {
		this.sysEnumId = sysEnumId;
	}

	public SysEnum getSysEnum() {
		return sysEnum;
	}

	public void setSysEnum(SysEnum sysEnum) {
		this.sysEnum = sysEnum;
	}
}