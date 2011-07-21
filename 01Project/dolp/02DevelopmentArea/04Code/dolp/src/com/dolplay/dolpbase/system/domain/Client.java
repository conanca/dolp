package com.dolplay.dolpbase.system.domain;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_CLIENT")
public class Client {
	@Id
	private Integer id;
	@Column
	@ColDefine(type = ColType.CHAR, width = 32)
	private String sessionId;
	@Column
	private Integer userId;
	@One(target = User.class, field = "userId")
	private User user;
	@Column
	private Timestamp logonTime;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String ipAddr;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String browser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(Timestamp logonTime) {
		this.logonTime = logonTime;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
}