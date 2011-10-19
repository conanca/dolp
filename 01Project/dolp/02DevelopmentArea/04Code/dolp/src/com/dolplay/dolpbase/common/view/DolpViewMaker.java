package com.dolplay.dolpbase.common.view;

import org.nutz.ioc.Ioc;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

/**
 * @author Administrator
 * 用于处理异常的JSON自定义视图
 */
public class DolpViewMaker implements ViewMaker {

	public static final String VIEW_JSON = "dolpjson";
	public static final String VIEW_JASPER = "jasper";
	public static final String VIEW_JASPER2 = "jasper2";

	@Override
	public View make(Ioc ioc, String type, String value) {
		if (VIEW_JSON.equals(type))
			if (Strings.isBlank(value))
				return new DolpJsonView(JsonFormat.compact());
			else
				return new DolpJsonView(Json.fromJson(JsonFormat.class, value));
		if (VIEW_JASPER.equals(type))
			return new HtmlReportView();
		if (VIEW_JASPER2.equals(type))
			return new HtmlReport2View(value);
		return null;
	}
}