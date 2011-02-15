package gs.dolp.common.view;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

/**
 * @author Administrator
 * 用于处理异常的JSON自定义视图
 */
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
		// 如果异常产生，将异常消息封装进AjaxResData并返回给前台
		if (Throwable.class.isAssignableFrom(obj.getClass())) {
			Throwable exception = (Throwable) obj;
			AjaxResData reData = new AjaxResData();
			reData.setUserdata(new SystemMessage(null, null, exception.getMessage()));
			jsonWritedStr = reData;
		}
		Mvcs.write(resp, null == jsonWritedStr ? data : jsonWritedStr, format);
	}
}
