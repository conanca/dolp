package gs.dolp.common.util;

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
		// TODO Auto-generated method stub
		System.out.println("调用到了～～～～～～～～～～～～shutdown");
	}

}
