package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseData;

import java.text.ParseException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("billModule")
@At("/dolpinhotel/management/bill")
public class BillModule {
	private BillService billService;

	@At
	public AdvancedJqgridResData<Bill> getGridData(@Param("..") JqgridReqData jq, @Param("number") String number,
			@Param("amount") String amount, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo) {
		return billService.getGridData(jq, number, amount, dateFrom, dateTo);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("number") String number,
			@Param("amount") String amount, @Param("date") String date) throws ParseException {
		return billService.UDBill(oper, id, number, amount, date);
	}
}
