package com.dolplay.dolpbase.common.util;

import com.dolplay.dolpbase.common.domain.DolpProperties;
import com.dolplay.dolpbase.system.util.NutConfigStorage;

public class PropProvider {

	public static DolpProperties getProp() {
		return IocObjectProvider.getIocObject(NutConfigStorage.getNutConfig(), DolpProperties.class, "prop");
	}
}