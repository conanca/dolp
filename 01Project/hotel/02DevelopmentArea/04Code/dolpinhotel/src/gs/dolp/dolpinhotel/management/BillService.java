package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;

public class BillService extends AdvJqgridIdEntityService<Bill> {

	public BillService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Bill> getGridData(JqgridReqData jqRe, String number, String amount, String dateFrom,
			String dateTo) {
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
		AdvancedJqgridResData<Bill> jq = getAdvancedJqgridRespData(cnd, jqRe);
		return jq;
	}

	@Aop(value = "log")
	public void UDBill(String oper, String id, String number, String amount, String date) throws ParseException {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
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
