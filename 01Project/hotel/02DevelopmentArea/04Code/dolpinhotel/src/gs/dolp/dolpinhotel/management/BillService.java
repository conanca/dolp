package gs.dolp.dolpinhotel.management;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.highchart.domain.ChartReturnData;
import gs.dolp.common.highchart.domain.SeriesItem;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class BillService extends JqgridService<Bill> {

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
	public AjaxResData UDBill(String oper, String id, String number, String amount, String date) throws ParseException {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			final List<Bill> bills = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Bill bill : bills) {
						dao().clearLinks(bill, "roomOccupancy");
					}
					clear(cnd);
				}
			});
			reData.setSystemMessage("删除成功!", null, null);
		}
		if ("edit".equals(oper)) {
			Bill bill = new Bill();
			bill.setId(Integer.parseInt(id));
			bill.setNumber(number);
			bill.setAmount(Double.parseDouble(amount));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp dateTime = new Timestamp(dateFormat.parse(date).getTime());
			bill.setDate(dateTime);
			dao().update(bill);
			reData.setSystemMessage("修改成功!", null, null);
		}
		return reData;
	}

	@Aop(value = "log")
	public AjaxResData statisticBill(String startDate, String endDate) {
		AjaxResData reData = new AjaxResData();

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
		reData.setReturnData(chartData);
		reData.setSystemMessage("统计完成!", null, null);
		return reData;
	}
}
