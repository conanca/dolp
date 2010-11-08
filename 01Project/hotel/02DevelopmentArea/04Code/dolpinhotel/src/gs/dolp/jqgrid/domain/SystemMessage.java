package gs.dolp.jqgrid.domain;

import org.nutz.json.Json;

public class SystemMessage implements UserData {
	private String infoMessage;
	private String warnMessage;
	private String errorMessage;

	public SystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		super();
		this.infoMessage = infoMessage;
		this.warnMessage = warnMessage;
		this.errorMessage = errorMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getWarnMessage() {
		return warnMessage;
	}

	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String toString() {
		return Json.toJson(this);
	}

}
