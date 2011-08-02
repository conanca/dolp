package com.dolplay.dolpinhotel.management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.highcharts.ChartReturnData;
import com.dolplay.dolpbase.common.domain.highcharts.SeriesItem;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;

@IocBean(args = { "refer:dao" })
public class BillService extends DolpBaseService<Bill> {

	public BillService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Bill> getGridData(JqgridReqData jqRe, String number, String amount, String dateFrom,
			String dateTo) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			cnd = cnd.and("NUMBER", "LIKE", "%" + Strings.trim(number) + "%");
		}
		if (!Strings.isBlank(amount)) {
			cnd = cnd.and("AMOUNT", "=", Strings.trim(amount));
		}
		if (!Strings.isBlank(dateFrom)) {
			cnd = cnd.and("DATE", ">=", dateFrom);
		}
		if (!Strings.isBlank(dateTo)) {
			cnd = cnd.and("DATE", "<=", dateTo);
		}
		AdvancedJqgridResData<Bill> jq = getAdvancedJqgridRespData(cnd, jqRe);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData UDBill(String oper, String ids, Bill bill) throws ParseException {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			final List<Bill> bills = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Bill bill : bills) {
						dao().clearLinks(bill, "roomOccupancy");
					}
					clear(cnd);
				}
			});
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(bill);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData statisticBill(String startDate, String endDate) {
		AjaxResData respData = new AjaxResData();

		Sql sql = Sqls
				.create("SELECT MONTH(DATE) AS MONTH,SUM(AMOUNT) AS MONTHAMOUNT FROM DOLPINHOTEL_BILL GROUP BY MONTH(DATE)");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<String> categories = new ArrayList<String>();
				List<Float> data1 = new ArrayList<Float>();
				List<Float> data2 = new ArrayList<Float>();
				while (rs.next()) {
					String month = rs.getString("MONTH") + "月";
					categories.add(month);
					data1.add(rs.getFloat("MONTHAMOUNT"));
					data2.add(rs.getFloat("MONTHAMOUNT") / 2);
				}
				List<SeriesItem> series = new ArrayList<SeriesItem>();
				series.add(new SeriesItem("营业额", data1));
				series.add(new SeriesItem("盈利", data2));
				return new ChartReturnData(categories, series);
			}
		});
		dao().execute(sql);
		ChartReturnData chartData = (ChartReturnData) sql.getResult();
		respData.setReturnData(chartData);
		respData.setSystemMessage("统计完成!", null, null);
		return respData;
	}
}
