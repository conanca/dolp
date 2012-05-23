package com.dolplay.dolpbase.common.domain;

public class PnotifyOption {
	private PnotifyType type;
	private String title;
	private String text;
	private String notice_icon;
	private String info_icon;
	private String error_icon;

	public PnotifyType getType() {
		return type;
	}
	public void setType(PnotifyType type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getNotice_icon() {
		return notice_icon;
	}
	public void setNotice_icon(String notice_icon) {
		this.notice_icon = notice_icon;
	}
	public String getInfo_icon() {
		return info_icon;
	}
	public void setInfo_icon(String info_icon) {
		this.info_icon = info_icon;
	}
	public String getError_icon() {
		return error_icon;
	}
	public void setError_icon(String error_icon) {
		this.error_icon = error_icon;
	}
}