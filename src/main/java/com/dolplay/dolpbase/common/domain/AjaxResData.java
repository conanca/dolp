package com.dolplay.dolpbase.common.domain;

import java.util.List;

import org.nutz.json.Json;
import org.nutz.lang.Lang;

/**
 * @author Administrator
 * 包含状态码、业务数据和系统消息的后台响应数据
 * 用于$dolpGet、$dolpPost、zTree的ajaxDataFilter以及jqgrid的form edit 这几种Ajax请求方法获取返回值
 */
public class AjaxResData implements ResponseData {

	/**
	 * 状态码
	 */
	private Integer status;

	/**
	 * 业务数据
	 */
	private Object logic;

	/**
	 * 系统消息
	 */
	private List<PnotifyOption> notices;

	public AjaxResData() {
		super();
	}

	public AjaxResData(Integer status, Object logic, List<PnotifyOption> notices) {
		super();
		this.status = status;
		this.logic = logic;
		this.notices = notices;
	}

	public String toString() {
		return Json.toJson(this);
	}

	public static AjaxResData getInstanceErrorNotice(String errorMessage) {
		PnotifyOption opt = new PnotifyOption();
		opt.setType(PnotifyType.error);
		opt.setTitle("错误");
		opt.setText(errorMessage);
		return new AjaxResData(null, null, Lang.list(opt));
	}

	public void setNotice(String noticeMessage) {
		PnotifyOption opt = new PnotifyOption();
		opt.setType(PnotifyType.notice);
		opt.setTitle("通知");
		opt.setText(noticeMessage);
		notices = Lang.list(opt);
	}

	public void setInfo(String infoMessage) {
		PnotifyOption opt = new PnotifyOption();
		opt.setType(PnotifyType.info);
		opt.setTitle("信息");
		opt.setText(infoMessage);
		notices = Lang.list(opt);
	}

	public void setError(String errorMessage) {
		PnotifyOption opt = new PnotifyOption();
		opt.setType(PnotifyType.error);
		opt.setTitle("错误");
		opt.setText(errorMessage);
		notices = Lang.list(opt);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Object getLogic() {
		return logic;
	}

	public void setLogic(Object logic) {
		this.logic = logic;
	}

	public List<PnotifyOption> getNotices() {
		return notices;
	}

	public void setNotices(List<PnotifyOption> notices) {
		this.notices = notices;
	}
}