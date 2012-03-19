package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.jolbox.bonecp.BoneCPDataSource;

public class DaoProvider {

	private static Dao dao;

	public static DataSource getDataSource() {
		return IocProvider.getIoc().get(BoneCPDataSource.class, "dataSource");
	}

	public static Dao getDao() {
		if (dao == null) {
			dao = new NutDao(getDataSource());
		}
		return dao;
	}
}