package gs.dolp.common.view;

import org.nutz.ioc.Ioc;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class MyJsonViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		if ("jsonx".equals(type)) {
			if (Strings.isBlank(value)) {
				return new MyJsonView(JsonFormat.compact());
			} else {
				JsonFormat format = Json.fromJson(JsonFormat.class, value);
				return new MyJsonView(format);
			}
		}
		return null;
	}

}
