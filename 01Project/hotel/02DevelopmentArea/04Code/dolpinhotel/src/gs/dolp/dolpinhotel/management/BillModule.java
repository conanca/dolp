package gs.dolp.dolpinhotel.management;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;

import java.text.ParseException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("billModule")
@At("/dolpinhotel/management/bill")
public class BillModule {
	private BillService billService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jq, @Param("number") String number,
			@Param("amount") String amount, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo) {
		return billService.getGridData(jq, number, amount, dateFrom, dateTo);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Bill bill)
			throws ParseException {
		return billService.UDBill(oper, ids, bill);
	}

	@At
	public ResponseData statisticBill(@Param("startDate") String startDate, @Param("endDate") String endDate) {
		return billService.statisticBill(startDate, endDate);
	}

}
