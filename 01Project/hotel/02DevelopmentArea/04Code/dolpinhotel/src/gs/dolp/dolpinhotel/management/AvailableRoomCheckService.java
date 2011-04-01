package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;
import gs.dolp.common.jqgrid.service.JqgridService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;

public class AvailableRoomCheckService extends JqgridService<Object> {

	public AvailableRoomCheckService(Dao dao) {
		super(dao);
	}

	@SuppressWarnings("unchecked")
	@Aop(value = "log")
	public List<StandardJqgridResDataRow> getRows() throws SQLException {
		Sql sql = Sqls.create("SELECT (SELECT NAME FROM DOLPINHOTEL_ROOMTYPE WHERE ID = ROOMTYPEID) AS ROOMTYPENAME,"
				+ "COUNT(ROOMTYPEID) AS AVAILABLEROOMCOUNT FROM DOLPINHOTEL_ROOM "
				+ "WHERE ISOCCUPANCY = 0 GROUP BY ROOMTYPEID");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
				int i = 1;
				while (rs.next()) {
					StandardJqgridResDataRow row = new StandardJqgridResDataRow();
					row.setId(i);
					row.addCellItem(rs.getString("ROOMTYPENAME"));
					row.addCellItem(String.valueOf(rs.getInt("AVAILABLEROOMCOUNT")));
					rows.add(row);
					i++;
				}
				return rows;
			}
		});
		dao().execute(sql);
		return (List<StandardJqgridResDataRow>) sql.getResult();
	}
}
