package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.mvc.NutConfig;

import com.dolplay.dolpbase.system.util.NutConfigStorage;
import com.jolbox.bonecp.BoneCPDataSource;

public class DaoProvider {

	private static DataSource getDataSource(NutConfig nutConfig) {
		return IocObjectProvider.getIocObject(nutConfig, BoneCPDataSource.class, "dataSource");
	}

	private static Dao getDao(NutConfig nutConfig) {
		return new NutDao(getDataSource(nutConfig));
	}

	public static DataSource getDataSource() {
		return getDataSource(NutConfigStorage.getNutConfig());
	}

	public static Dao getDao() {
		return getDao(NutConfigStorage.getNutConfig());
	}

}