package com.dolplay.dolpinhotel.management;

import java.sql.SQLException;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.domain.jqgrid.StandardJqgridResData;
import com.dolplay.dolpbase.common.service.DolpBaseService;

@IocBean(args = { "refer:dao" }, fields = { "prop" })
public class AvailableRoomCheckService extends DolpBaseService<Object> {

	public AvailableRoomCheckService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public StandardJqgridResData getGridData(JqgridReqData jqReq) throws SQLException {
		Sql sql = Sqls.create("SELECT (SELECT NAME FROM DOLPINHOTEL_ROOMTYPE WHERE ID = ROOMTYPEID) AS ROOMTYPENAME,"
				+ "COUNT(ROOMTYPEID) AS AVAILABLEROOMCOUNT FROM DOLPINHOTEL_ROOM "
				+ "WHERE ISOCCUPANCY = false GROUP BY ROOMTYPEID");
		StandardJqgridResData jqGrid = getStandardJqgridResData(sql, jqReq);
		return jqGrid;
	}
}
