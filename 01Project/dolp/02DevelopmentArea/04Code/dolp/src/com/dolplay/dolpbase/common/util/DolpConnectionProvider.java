package com.dolplay.dolpbase.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;

public class DolpConnectionProvider implements ConnectionProvider {

	@Override
	public Connection getConnection() throws SQLException {
		return DaoHandler.getDataSource().getConnection();
	}

	@Override
	public void shutdown() throws SQLException {
		// 不需要让它来关闭连接
	}

}
