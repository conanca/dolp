package com.dolplay.dolpbase.common.domain;


public class UploadTempFile {

	private String name;
	private String clientLocalPath;
	private Long id;
	private String contentType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientLocalPath() {
		return clientLocalPath;
	}

	public void setClientLocalPath(String clientLocalPath) {
		this.clientLocalPath = clientLocalPath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}