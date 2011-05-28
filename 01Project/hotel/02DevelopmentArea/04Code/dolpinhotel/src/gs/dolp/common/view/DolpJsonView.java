package gs.dolp.common.view;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ExceptionAjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.util.ExceptionHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * 用于处理异常的JSON自定义视图
 */
public class DolpJsonView implements View {
	final static Logger logger = LoggerFactory.getLogger("ROOT");

	private JsonFormat format;

	private Object data;

	public void setData(Object data) {
		this.data = data;
	}

	public DolpJsonView(JsonFormat format) {
		this.format = format;
	}

	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws IOException {
		Object jsonWritedStr = obj;
		// 如果异常产生，将异常消息封装进AjaxResData并返回给前台
		if (Throwable.class.isAssignableFrom(obj.getClass())) {
			Throwable exception = (Throwable) obj;
			ExceptionAjaxResData excpAjaxResData = new ExceptionAjaxResData();
			excpAjaxResData.setSystemMessage(new SystemMessage(null, null, exception.getMessage()));
			AjaxResData userdata = new AjaxResData();
			userdata.setSystemMessage(null, null, exception.getMessage());
			excpAjaxResData.setUserdata(userdata);
			jsonWritedStr = excpAjaxResData;
			// 输出日志
			logger.error(ExceptionHandler.packageException(exception));
		}
		Mvcs.write(resp, null == jsonWritedStr ? data : jsonWritedStr, format);
	}
}
