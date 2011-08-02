package com.dolplay.dolpinhotel.management;

import java.text.ParseException;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/management/bill")
public class BillModule {

	@Inject
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
