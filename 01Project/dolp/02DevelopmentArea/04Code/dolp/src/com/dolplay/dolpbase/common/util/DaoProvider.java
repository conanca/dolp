package com.dolplay.dolpbase.common.util;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import com.jolbox.bonecp.BoneCPDataSource;

public class DaoProvider {

	private static BoneCPDataSource dataSource;

	static {
		if (dataSource == null) {
			Ioc ioc = new NutIoc(new JsonLoader("dao.js"));
			dataSource = ioc.get(BoneCPDataSource.class, "dataSource");
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Dao getDao() {
		Dao dao = new NutDao(dataSource);
		return dao;
	}
}