package com.dolplay.dolpbase.common.util;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.Mvcs;

public class IocProvider {
	private static Ioc ioc;

	public static void init(Ioc ioc) {
		IocProvider.ioc = ioc;
	}

	public static Ioc getIoc() {
		Ioc ioc = Mvcs.getIoc();
		if (ioc == null) {
			ioc = IocProvider.ioc;
		}
		return ioc;
	}
}