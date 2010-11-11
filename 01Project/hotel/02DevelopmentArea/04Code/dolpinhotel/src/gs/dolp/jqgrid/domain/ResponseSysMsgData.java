package gs.dolp.jqgrid.domain;

import org.nutz.json.Json;

public class ResponseSysMsgData implements ResponseData {

	private UserData userdata;

	public UserData getUserdata() {
		return userdata;
	}

	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}

}
