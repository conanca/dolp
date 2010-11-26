package gs.dolp.common.jqgrid.domain;

import org.nutz.json.Json;

public class ResponseSysMsgData implements ResponseData {

	private Object returnData;

	private ResUserData userdata;

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

	public ResUserData getUserdata() {
		return userdata;
	}

	public void setUserdata(ResUserData userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}

}
