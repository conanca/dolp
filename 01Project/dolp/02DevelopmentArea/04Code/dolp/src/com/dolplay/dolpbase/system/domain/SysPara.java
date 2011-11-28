package com.dolplay.dolpbase.system.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_SYSPARA")
public class SysPara {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 100)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String value;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}