package com.dolplay.dolpbase.system.domain;

import java.io.File;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.Ioc;
import org.nutz.json.JsonField;

@Table("SYSTEM_POOLFILE")
public class PoolFile {
	@Id
	@JsonField(ignore = true)
	private Long id;
	@Column
	private Long idInPool;
	@Column
	private String name;
	@Column
	@JsonField(ignore = true)
	private String suffix;
	@Column
	@JsonField(ignore = true)
	private String poolIocName;
	private String clientLocalPath;
	private String contentType;

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

	public String getClientLocalPath() {
		return clientLocalPath;
	}

	public void setClientLocalPath(String clientLocalPath) {
		this.clientLocalPath = clientLocalPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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