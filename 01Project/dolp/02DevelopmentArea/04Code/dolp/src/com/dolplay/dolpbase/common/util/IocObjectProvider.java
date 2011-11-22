package com.dolplay.dolpbase.common.util;

import org.nutz.filepool.FilePool;
import org.nutz.ioc.Ioc;

import com.dolplay.dolpbase.common.domain.DolpProperties;

public class IocObjectProvider {

	private static DolpProperties prop;

	private static FilePool attachmentPool;

	public static void init(Ioc ioc) {
		prop = ioc.get(DolpProperties.class, "prop");
		attachmentPool = ioc.get(FilePool.class, "attachmentPool");
	}

	public static DolpProperties getProp() {
		return prop;
	}

	public static FilePool getAttachmentPool() {
		return attachmentPool;
	}
}