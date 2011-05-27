package gs.dolp.common.domain;

import java.util.ArrayList;
import java.util.List;

import org.nutz.json.Json;
import org.nutz.lang.Strings;

/**
 * @author Administrator
 * 系统信息对象
 */
public class SystemMessage {
	/**
	 * 提示信息集合
	 */
	private List<String> infoMessages;

	/**
	 * 警告信息集合
	 */
	private List<String> warnMessages;

	/**
	 * 错误信息集合
	 */
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
		if (!Strings.isEmpty(infoMessage)) {
			infoMessages = new ArrayList<String>();
			infoMessages.add(infoMessage);
		}
		if (!Strings.isEmpty(warnMessage)) {
			warnMessages = new ArrayList<String>();
			warnMessages.add(warnMessage);
		}
		if (!Strings.isEmpty(errorMessage)) {
			errorMessages = new ArrayList<String>();
			errorMessages.add(errorMessage);
		}
	}

	/**
	 * 添加提示信息
	 * @param addMessage
	 */
	public void addInfoMessage(String addMessage) {
		infoMessages.add(addMessage);
	}

	/**
	 * 添加警告信息
	 * @param addMessage
	 */
	public void addWarnMessage(String addMessage) {
		warnMessages.add(addMessage);
	}

	/**
	 * 添加错误信息
	 * @param addMessage
	 */
	public void addErrorMessage(String addMessage) {
		errorMessages.add(addMessage);
	}

	public void addSystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		addInfoMessage(infoMessage);
		addWarnMessage(warnMessage);
		addErrorMessage(errorMessage);
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