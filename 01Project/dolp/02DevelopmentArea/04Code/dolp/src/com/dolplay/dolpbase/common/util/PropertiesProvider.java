package com.dolplay.dolpbase.common.util;

import org.nutz.ioc.Ioc;

import com.dolplay.dolpbase.common.domain.DolpProperties;

public class PropertiesProvider {

	private static DolpProperties prop;

	public static void init(Ioc ioc) {
		prop = ioc.get(DolpProperties.class, "prop");
	}

	public static DolpProperties getProp() {
		return prop;
	}
}