package gs.dolp.common.view;

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
public class MyJsonViewMaker implements ViewMaker {

	public static final String VIEW_JSON = "dolpjson";

	@Override
	public View make(Ioc ioc, String type, String value) {
		if (VIEW_JSON.equals(type))
			if (Strings.isBlank(value))
				return new MyJsonView(JsonFormat.compact());
			else
				return new MyJsonView(Json.fromJson(JsonFormat.class, value));
		return null;
	}

}