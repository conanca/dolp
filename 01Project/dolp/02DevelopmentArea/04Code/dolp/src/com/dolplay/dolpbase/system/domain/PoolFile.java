package com.dolplay.dolpbase.system.domain;

import java.io.File;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.Ioc;

@Table("SYSTEM_POOLFILE")
public class PoolFile {
	@Id
	private Long id;
	@Column
	private Long idInPool;
	@Column
	private String name;
	@Column
	private String suffix;
	@Column
	private String poolIocName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdInPool() {
		return idInPool;
	}

	public void setIdInPool(Long idInPool) {
		this.idInPool = idInPool;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPoolIocName() {
		return poolIocName;
	}

	public void setPoolIocName(String poolIocName) {
		this.poolIocName = poolIocName;
	}

	/**
	 * 获取该文件的File对象,需提供其所在文件池的Ioc
	 * @param ioc
	 * @return
	 */
	public File getFile(Ioc ioc) {
		FilePool pool = ioc.get(NutFilePool.class, poolIocName);
		return pool.getFile(idInPool, suffix);
	}
}