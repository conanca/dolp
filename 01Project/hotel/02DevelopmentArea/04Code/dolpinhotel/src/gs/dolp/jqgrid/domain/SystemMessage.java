package gs.dolp.jqgrid.domain;

import java.util.ArrayList;
import java.util.List;

import org.nutz.json.Json;
import org.nutz.lang.Strings;

public class SystemMessage implements UserData {
	private List<String> infoMessages;
	private List<String> warnMessages;
	private List<String> errorMessages;

	public SystemMessage() {
		super();
	}

	public SystemMessage(List<String> infoMessages, List<String> warnMessages, List<String> errorMessages) {
		super();
		this.infoMessages = infoMessages;
		this.warnMessages = warnMessages;
		this.errorMessages = errorMessages;
	}

	public SystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		super();
		if (!Strings.isBlank(infoMessage)) {
			infoMessages = new ArrayList<String>();
			infoMessages.add(infoMessage);
		}
		if (!Strings.isBlank(warnMessage)) {
			warnMessages = new ArrayList<String>();
			warnMessages.add(warnMessage);
		}
		if (!Strings.isBlank(errorMessage)) {
			errorMessages = new ArrayList<String>();
			errorMessages.add(errorMessage);
		}
	}

	public void addInfoMessage(String addMessage) {
		infoMessages.add(addMessage);
	}

	public void addWarnMessage(String addMessage) {
		warnMessages.add(addMessage);
	}

	public void addErrorMessage(String addMessage) {
		errorMessages.add(addMessage);
	}

	public List<String> getInfoMessages() {
		return infoMessages;
	}

	public void setInfoMessages(List<String> infoMessages) {
		this.infoMessages = infoMessages;
	}

	public List<String> getWarnMessages() {
		return warnMessages;
	}

	public void setWarnMessages(List<String> warnMessages) {
		this.warnMessages = warnMessages;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String toString() {
		return Json.toJson(this);
	}

}
