package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;
import gs.dolp.common.jqgrid.domain.SystemMessage;

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
import org.nutz.service.Service;

public class AvailableRoomCheckService extends Service {

	public AvailableRoomCheckService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public StandardJqgridResData AvailableRoomCount() throws SQLException {
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(1);
		jq.setRows(getRows());
		jq.setUserdata(new SystemMessage("查询成功!", null, null));
		return jq;
	}

	@SuppressWarnings("unchecked")
	@Aop(value = "log")
	public List<StandardJqgridResDataRow> getRows() throws SQLException {
		Sql sql = Sqls
				.create("SELECT (SELECT NAME FROM DOLPINHOTEL_ROOMTYPE WHERE ID = ROOMTYPEID) AS ROOMTYPENAME,COUNT(ROOMTYPEID) AS AVAILABLEROOMCOUNT FROM DOLPINHOTEL_ROOM WHERE ISOCCUPANCY = 0 GROUP BY ROOMTYPEID");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
				int i = 1;
				while (rs.next()) {
					List cell = new ArrayList();
					cell.add(rs.getString("ROOMTYPENAME"));
					cell.add(rs.getInt("AVAILABLEROOMCOUNT"));
					StandardJqgridResDataRow row = new StandardJqgridResDataRow();
					row.setId(i);
					row.setCell(cell);
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
