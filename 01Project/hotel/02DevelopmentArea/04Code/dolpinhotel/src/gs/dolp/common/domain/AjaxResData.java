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
	 * 系统消息,之所以名叫userdata,是为了便于自定义的视图—— DolpJsonView 统一处理 ajax提交或请求 和 jqGrid的请求 的异常
	 */
	private SystemMessage userdata;

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public SystemMessage getUserdata() {
		return userdata;
	}

	public void setUserdata(SystemMessage userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}

}
