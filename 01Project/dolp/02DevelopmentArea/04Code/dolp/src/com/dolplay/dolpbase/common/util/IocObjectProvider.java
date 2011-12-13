package com.dolplay.dolpbase.common.util;

import javax.servlet.ServletContext;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;

public class IocObjectProvider {

	public static <T> T getIocObject(NutConfig nutConfig, Class<T> type, String name) {
		return nutConfig.getIoc().get(type, name);
	}

	public static <T> T getIocObject(ActionContext actionContext, Class<T> type, String name) {
		return Mvcs.getIoc(actionContext.getServletContext()).get(type, name);
	}

	public static <T> T getIocObject(ServletContext servletContext, Class<T> type, String name) {
		return Mvcs.getIoc(servletContext).get(type, name);
	}
}