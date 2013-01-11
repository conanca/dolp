package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.alibaba.druid.pool.DruidDataSource;

public class DaoProvider {

	private static Dao dao;

	public static DataSource getDataSource() {
		return IocProvider.getIoc().get(DruidDataSource.class, "dataSource");
	}

	public static Dao getDao() {
		if (dao == null) {
			dao = new NutDao(getDataSource());
		}
		return dao;
	}
}