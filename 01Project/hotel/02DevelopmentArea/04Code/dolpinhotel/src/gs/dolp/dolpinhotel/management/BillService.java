package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class BillService extends IdEntityForjqGridService<Bill> {

	private static final Log log = Logs.getLog(BillService.class);

	public BillService(Dao dao) {
		super(dao);
	}

	public JqgridData<Bill> getGridData(String page, String rows, String sidx, String sord, String number,
			String amount, String dateFrom, String dateTo) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			cnd = cnd.and("NUMBER", "LIKE", "%" + number + "%");
		}
		if (!Strings.isBlank(amount)) {
			cnd = cnd.and("AMOUNT", "=", amount);
		}
		if (!Strings.isBlank(dateFrom)) {
			cnd = cnd.and("DATE", ">=", dateFrom);
		}
		if (!Strings.isBlank(dateTo)) {
			cnd = cnd.and("DATE", "<=", dateTo);
		}
		JqgridData<Bill> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public void UDBill(String oper, String id, String number, String amount, String date) throws ParseException {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
			List<Bill> bills = this.query(cnd, null);

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
		}
	}

}
