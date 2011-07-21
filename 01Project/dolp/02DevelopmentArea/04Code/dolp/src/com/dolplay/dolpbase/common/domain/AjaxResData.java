package com.dolplay.dolpbase.common.domain;

import org.nutz.json.Json;

/**
 * @author Administrator
 * 包含业务数据和系统消息的后台响应数据
 */
public class AjaxResData implements ResponseData {

	/**
	 * 业务数据
	 */
	private Object returnData;

	/**
	 * 系统消息
	 */
	private SystemMessage systemMessage;

	public AjaxResData() {
		super();
	}

	public AjaxResData(Object returnData, SystemMessage systemMessage) {
		super();
		this.returnData = returnData;
		this.systemMessage = systemMessage;
	}

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

	/**
	 * 用于简化 设置systemMessage 的帮助函数
	 * @param infoMessage
	 * @param warnMessage
	 * @param errorMessage
	 */
	public void setSystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		setSystemMessage(new SystemMessage(infoMessage, warnMessage, errorMessage));
	}

	public String toString() {
		return Json.toJson(this);
	}

}
