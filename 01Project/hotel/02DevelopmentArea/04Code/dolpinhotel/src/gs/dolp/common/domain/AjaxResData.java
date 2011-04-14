package gs.dolp.common.domain;

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

	public String toString() {
		return Json.toJson(this);
	}

}
