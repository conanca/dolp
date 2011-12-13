package com.dolplay.dolpbase.system.util;

import org.nutz.mvc.NutConfig;

public class NutConfigStorage {
	private static NutConfig nutConfig;

	public static void loadNutConfig(NutConfig nutConfig) {
		if (null == NutConfigStorage.nutConfig) {
			NutConfigStorage.nutConfig = nutConfig;
		}
	}

	public static NutConfig getNutConfig() {
		return nutConfig;
	}
}