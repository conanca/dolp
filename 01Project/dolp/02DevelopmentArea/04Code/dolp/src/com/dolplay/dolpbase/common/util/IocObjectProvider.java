package com.dolplay.dolpbase.common.util;

import javax.servlet.ServletContext;

import org.nutz.filepool.FilePool;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;

import com.dolplay.dolpbase.common.domain.DolpProperties;

public class IocObjectProvider {

	private static DolpProperties prop;

	private static FilePool attachmentPool;

	private static Ioc ioc;

	public static void init(Ioc ioc) {
		IocObjectProvider.ioc = ioc;
		prop = ioc.get(DolpProperties.class, "prop");
		attachmentPool = ioc.get(FilePool.class, "attachmentPool");
	}

	public static DolpProperties getProp() {
		return prop;
	}

	public static FilePool getAttachmentPool() {
		return attachmentPool;
	}

	public static <T> T getIocObject(ActionContext actionContext, Class<T> type, String name) {
		return Mvcs.getIoc(actionContext.getServletContext()).get(type, name);
	}

	public static <T> T getIocObject(ServletContext context, Class<T> type, String name) {
		return Mvcs.getIoc(context).get(type, name);
	}

	public static <T> T getIocObject(Ioc ioc, Class<T> type, String name) {
		return ioc.get(type, name);
	}

	public static <T> T getIocObject(Class<T> type, String name) {
		return ioc.get(type, name);
	}
}