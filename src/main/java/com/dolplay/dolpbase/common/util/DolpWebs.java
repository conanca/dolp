package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;

public class DolpWebs {

	public static Ioc ioc() {
		return Webs.ioc();
	}

	public static PropertiesProxy config() {
		return ioc().get(PropertiesProxy.class, "config");
	}

	public static DataSource dataSource() {
		return ioc().get(DataSource.class, "dataSource");
	}

	public static Dao dao() {
		return ioc().get(Dao.class, "dao");
	}
}