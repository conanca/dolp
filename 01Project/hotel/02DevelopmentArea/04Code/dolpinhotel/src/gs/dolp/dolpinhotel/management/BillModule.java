package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;

import java.text.ParseException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("billModule")
@At("/dolpinhotel/management/bill")
public class BillModule {
	private BillService billService;

	@At
	public JqgridAdvancedData<Bill> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord, @Param("number") String number,
			@Param("amount") String amount, @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo) {
		return billService.getGridData(page, rows, sidx, sord, number, amount, dateFrom, dateTo);
	}

	@At
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("number") String number,
			@Param("amount") String amount, @Param("date") String date) throws ParseException {
		billService.UDBill(oper, id, number, amount, date);
	}
}
