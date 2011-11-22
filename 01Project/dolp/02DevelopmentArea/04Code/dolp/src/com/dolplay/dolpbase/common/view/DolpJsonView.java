package com.dolplay.dolpbase.common.view;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.dolplay.dolpbase.common.domain.DolpProperties;
import com.dolplay.dolpbase.common.domain.ExceptionAjaxResData;
import com.dolplay.dolpbase.common.util.IocObjectProvider;

/**
 * @author Administrator
 * 用于处理异常的JSON自定义视图
 */
public class DolpJsonView implements View {

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
			String exceptionMessage;
			// 获取环境类型
			DolpProperties prop = IocObjectProvider.getProp();
			String env = (String) prop.get("Environment");
			// 获取配置文件参数Environment,判断是否为空
			if (Strings.isEmpty(env)) {
				exceptionMessage = "配置文件中Environment未配置或为空";
			} else {
				// 获取异常信息
				Throwable exception = (Throwable) obj;
				exceptionMessage = exception.getMessage();
				// 异常信息为空时的处理
				if (null == exceptionMessage) {
					if (exception instanceof NullPointerException) {
						exceptionMessage = "java.lang.NullPointerException: null";
					} else {
						exceptionMessage = "未知异常";
					}
				}
				// 判断异常信息中是否含中文
				String regEx = "[\u4e00-\u9fa5]";
				Pattern pat = Pattern.compile(regEx);
				Matcher matcher = pat.matcher(exceptionMessage);
				// 若当前配置的环境为生产环境,并且异常信息不含中文,则异常信息改为简单提示信息
				if (env.equals("prd") && !matcher.find()) {
					exceptionMessage = "异常发生,请联系管理员";
				}
			}
			// 为了前台显示之用封装异常信息
			ExceptionAjaxResData excpAjaxResData = new ExceptionAjaxResData();
			excpAjaxResData.setSystemMessage(null, null, exceptionMessage);
			jsonWritedStr = excpAjaxResData;
		}
		Mvcs.write(resp, null == jsonWritedStr ? data : jsonWritedStr, format);
	}
}