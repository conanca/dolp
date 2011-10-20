package com.dolplay.dolpbase.common.domain;

/**
 * 
 * 包含异常消息的的后台响应数据
 * 用于$dolpGet、$dolpPost、zTree的ajaxDataFilter以及jqgrid的form edit 这几种Ajax请求方法，后台异常发生时获取异常消息
 * 
 * @author Administrator
 */
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

	/**
	 * 用于简化 设置systemMessage 的帮助函数
	 * @param infoMessage
	 * @param warnMessage
	 * @param errorMessage
	 */
	public void setSystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		setSystemMessage(new SystemMessage(infoMessage, warnMessage, errorMessage));
		AjaxResData userdata = new AjaxResData();
		userdata.setSystemMessage(infoMessage, warnMessage, errorMessage);
		setUserdata(userdata);
	}
}