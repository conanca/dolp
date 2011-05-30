package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.service.JqgridService;

import java.sql.SQLException;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;

public class AvailableRoomCheckService extends JqgridService<Object> {

	public AvailableRoomCheckService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public StandardJqgridResData getGridData(JqgridReqData jqReq) throws SQLException {
		Sql sql = Sqls.create("SELECT (SELECT NAME FROM DOLPINHOTEL_ROOMTYPE WHERE ID = ROOMTYPEID) AS ROOMTYPENAME,"
				+ "COUNT(ROOMTYPEID) AS AVAILABLEROOMCOUNT FROM DOLPINHOTEL_ROOM "
				+ "WHERE ISOCCUPANCY = 0 GROUP BY ROOMTYPEID");
		StandardJqgridResData jqGrid = getStandardJqgridResData(sql, null, jqReq);
		return jqGrid;
	}
}
