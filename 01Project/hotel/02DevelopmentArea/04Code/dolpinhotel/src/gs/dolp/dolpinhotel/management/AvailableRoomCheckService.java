package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.domain.JqgridStandardData;
import gs.dolp.jqgrid.domain.JqgridStandardDataRow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.Service;

public class AvailableRoomCheckService extends Service {

	private static final Log log = Logs.getLog(AvailableRoomCheckService.class);

	public AvailableRoomCheckService(Dao dao) {
		super(dao);
	}

	public JqgridStandardData AvailableRoomCount() throws SQLException {
		JqgridStandardData jq = new JqgridStandardData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(1);
		jq.setRows(getRows());
		log.debug(jq.toString());
		return jq;
	}

	@SuppressWarnings("unchecked")
	public List<JqgridStandardDataRow> getRows() throws SQLException {
		Sql sql = Sqls
				.create("SELECT (SELECT NAME FROM DOLPINHOTEL_ROOMTYPE WHERE ID = ROOMTYPEID) AS ROOMTYPENAME,COUNT(ROOMTYPEID) AS AVAILABLEROOMCOUNT FROM DOLPINHOTEL_ROOM WHERE ISOCCUPANCY = 0 GROUP BY ROOMTYPEID");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<JqgridStandardDataRow> rows = new ArrayList<JqgridStandardDataRow>();
				int i = 1;
				while (rs.next()) {
					List cell = new ArrayList();
					cell.add(rs.getString("ROOMTYPENAME"));
					cell.add(rs.getInt("AVAILABLEROOMCOUNT"));
					JqgridStandardDataRow row = new JqgridStandardDataRow();
					row.setId(i);
					row.setCell(cell);
					rows.add(row);
					i++;
				}
				return rows;
			}
		});
		dao().execute(sql);
		return (List<JqgridStandardDataRow>) sql.getResult();
	}
}
