package com.dolplay.dolpbase.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DolpConnectionProvider implements ConnectionProvider {
	private static Logger logger = LoggerFactory.getLogger(DolpConnectionProvider.class);

	@Override
	public Connection getConnection() throws SQLException {
		logger.debug("Get connection");
		return DaoHandler.getDataSource().getConnection();
	}

	@Override
	public void shutdown() throws SQLException {
		logger.debug("Close connection");
		this.getConnection().close();
	}
}