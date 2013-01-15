package com.dolplay.dolpbase.common.view;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.util.DolpWebs;

/**
 * @author Administrator
 * 用于处理异常的JSON自定义视图
 */
public class DolpJsonView implements View {

	public static final String SYSTEM_ENVIRONMENT = "SYSTEM_ENVIRONMENT";

	private final JsonFormat format;

	private Object data;

	public void setData(Object data) {
		this.data = data;
	}

	public DolpJsonView(JsonFormat format) {
		this.format = format;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws IOException {
		Object jsonWritedStr = obj;
		// 如果异常产生，将异常消息封装进AjaxResData并返回给前台
		if (Throwable.class.isAssignableFrom(obj.getClass())) {
			String exceptionMessage;
			// 获取环境类型
			String env = DolpWebs.config().get(SYSTEM_ENVIRONMENT);
			// 获取配置文件参数SYSTEM_ENVIRONMENT,判断是否为空
			if (Strings.isEmpty(env)) {
				exceptionMessage = "配置文件中SYSTEM_ENVIRONMENT未配置或为空,无法显示异常信息!";
			} else {
				// 获取异常信息
				Throwable exception = (Throwable) obj;
				exceptionMessage = exception.getMessage();
				exceptionMessage = null == exceptionMessage ? exception.getClass().getCanonicalName()
						: exceptionMessage;
				// 若当前配置的环境为生产环境,并且异常信息不含中文,则异常信息改为简单提示信息
				if (env.equals("prd") && !Pattern.compile("[\u4e00-\u9fa5]").matcher(exceptionMessage).find()) {
					exceptionMessage = "异常发生,请联系管理员!";
				}
			}
			// 为了前台显示之用封装异常信息
			jsonWritedStr = AjaxResData.getInstanceErrorNotice(exceptionMessage);
		}
		Mvcs.write(resp, null == jsonWritedStr ? data : jsonWritedStr, format);
	}
}