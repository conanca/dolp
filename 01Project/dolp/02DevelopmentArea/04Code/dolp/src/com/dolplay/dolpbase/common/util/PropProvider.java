package com.dolplay.dolpbase.common.util;

import com.dolplay.dolpbase.common.domain.DolpProperties;

public class PropProvider {

	public static DolpProperties getProp() {
		return IocProvider.getIoc().get(DolpProperties.class, "prop");
	}
}