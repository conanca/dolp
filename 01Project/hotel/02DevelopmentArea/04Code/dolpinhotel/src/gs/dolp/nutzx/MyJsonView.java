package gs.dolp.nutzx;

import gs.dolp.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.jqgrid.domain.SystemMessage;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

public class MyJsonView implements View {

	private JsonFormat format;

	private Object data;

	public void setData(Object data) {
		this.data = data;
	}

	public MyJsonView(JsonFormat format) {
		this.format = format;
	}

	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws IOException {
		Object jsonWritedStr = obj;
		if (Throwable.class.isAssignableFrom(obj.getClass())) {
			Throwable exception = (Throwable) obj;
			ResponseSysMsgData reData = new ResponseSysMsgData();
			reData.setUserdata(new SystemMessage(null, null, exception.getMessage()));
			jsonWritedStr = reData;
		}
		Mvcs.write(resp, null == jsonWritedStr ? data : jsonWritedStr, format);
	}
}
