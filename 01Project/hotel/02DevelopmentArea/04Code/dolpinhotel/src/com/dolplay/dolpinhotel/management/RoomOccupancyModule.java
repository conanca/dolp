package com.dolplay.dolpinhotel.management;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/management/roomoccupancy")
public class RoomOccupancyModule {

	@Inject
	private RoomOccupancyService roomOccupancyService;

	@At
	public ResponseData saveRoomOccupancy(@Param("enterDate") String enterDate,
			@Param("expectedCheckOutDate") String expectedCheckOutDate, @Param("roomId") int roomId,
			@Param("customers") Customer[] customers) {
		return roomOccupancyService.saveRoomOccupancy(enterDate, expectedCheckOutDate, roomId, customers);
	}

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("billId") int billId,
			@Param("number") String number, @Param("enterDateFrom") String enterDateFrom,
			@Param("enterDateTo") String enterDateTo,
			@Param("expectedCheckOutDateFrom") String expectedCheckOutDateFrom,
			@Param("expectedCheckOutDateTo") String expectedCheckOutDateTo,
			@Param("leaveDateFrom") String leaveDateFrom, @Param("leaveDateTo") String leaveDateTo,
			@Param("occupancyDays") String occupancyDays, @Param("status") String status) {
		return roomOccupancyService.getGridData(jqReq, number, enterDateFrom, enterDateTo, expectedCheckOutDateFrom,
				expectedCheckOutDateTo, leaveDateFrom, leaveDateTo, occupancyDays, status, billId);
	}

	@At
	public ResponseData checkOut(@Param("checkOutIdArr[]") int[] ids, @Param("leaveDate") String leaveDate) {
		return roomOccupancyService.checkOut(ids, leaveDate);
	}
}
