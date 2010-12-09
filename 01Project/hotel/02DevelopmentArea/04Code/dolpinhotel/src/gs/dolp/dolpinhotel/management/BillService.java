package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
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
	public ResponseSysMsgData UDBill(String oper, String id, String number, String amount, String date)
			throws ParseException {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(id).append(")").toString());
			clear(cnd);
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
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
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}

}
