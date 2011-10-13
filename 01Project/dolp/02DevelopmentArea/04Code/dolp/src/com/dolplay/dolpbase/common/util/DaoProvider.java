package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;

import com.jolbox.bonecp.BoneCPDataSource;

public class DaoProvider {

	private static BoneCPDataSource dataSource;
	private static Dao dao;

	public static void init(Ioc ioc) {
		dataSource = ioc.get(BoneCPDataSource.class, "dataSource");
		dao = new NutDao(dataSource);
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Dao getDao() {
		return dao;
	}
}