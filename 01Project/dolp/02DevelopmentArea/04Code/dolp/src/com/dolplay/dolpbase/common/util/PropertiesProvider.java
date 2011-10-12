package com.dolplay.dolpbase.common.util;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

public class PropertiesProvider {

	private static DolpProperties prop;

	static {
		if (prop == null) {
			Ioc ioc = new NutIoc(new JsonLoader("properties.js"));
			prop = ioc.get(DolpProperties.class, "prop");
		}
	}

	public static DolpProperties getProp() {
		return prop;
	}
}