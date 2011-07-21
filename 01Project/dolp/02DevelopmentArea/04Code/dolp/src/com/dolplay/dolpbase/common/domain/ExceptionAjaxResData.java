package com.dolplay.dolpbase.common.domain;

public class ExceptionAjaxResData implements ResponseData {
	/**
	 * dolpGet/dolpPost 所用的系统消息字段
	 */
	private SystemMessage systemMessage;

	/**
	 * jqGrid所用的系统消息字段
	 */
	private AjaxResData userdata;

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

	public AjaxResData getUserdata() {
		return userdata;
	}

	public void setUserdata(AjaxResData userdata) {
		this.userdata = userdata;
	}
}