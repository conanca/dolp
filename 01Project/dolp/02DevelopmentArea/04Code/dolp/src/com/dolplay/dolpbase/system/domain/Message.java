package com.dolplay.dolpbase.system.domain;

import java.sql.Timestamp;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_MESSAGE")
public class Message {
	@Id
	private Integer id;
	@One(target = User.class, field = "senderUserId")
	private User sender;
	@Column
	private Integer senderUserId;
	@ManyMany(target = User.class, relation = "SYSTEM_MESSAGE_RECEIVERUSER", from = "MESSAGEID", to = "USERID")
	private List<User> receivers;
	@ManyMany(target = PoolFile.class, relation = "SYSTEM_MESSAGE_POOLFILE", from = "MESSAGEID", to = "POOLFILEID")
	private List<PoolFile> attachments;
	@Column
	private Timestamp date;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String title;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 10000)
	private String content;
	@Column
	private Integer state;
	@Column
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Integer getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(Integer senderUserId) {
		this.senderUserId = senderUserId;
	}

	public List<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	public List<PoolFile> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<PoolFile> attachments) {
		this.attachments = attachments;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}