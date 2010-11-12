package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomOccupancyModule")
@At("/dolpinhotel/management/roomoccupancy")
public class RoomOccupancyModule {
	private RoomOccupancyService roomOccupancyService;

	@At
	public void saveRoomOccupancy(@Param("enterDate") String enterDate,
			@Param("expectedCheckOutDate") String expectedCheckOutDate, @Param("roomId") int roomId,
			@Param("customers") Customer[] customers) throws ParseException {
		roomOccupancyService.saveRoomOccupancy(enterDate, expectedCheckOutDate, roomId, customers);
	}

	@At
	public AdvancedJqgridResData<RoomOccupancy> getGridData(@Param("..") JqgridReqData jqReq,
			@Param("billId") int billId, @Param("number") String number, @Param("enterDateFrom") String enterDateFrom,
			@Param("enterDateTo") String enterDateTo,
			@Param("expectedCheckOutDateFrom") String expectedCheckOutDateFrom,
			@Param("expectedCheckOutDateTo") String expectedCheckOutDateTo,
			@Param("leaveDateFrom") String leaveDateFrom, @Param("leaveDateTo") String leaveDateTo,
			@Param("occupancyDays") String occupancyDays, @Param("status") String status) {
		return roomOccupancyService.getGridData(jqReq, number, enterDateFrom, enterDateTo, expectedCheckOutDateFrom,
				expectedCheckOutDateTo, leaveDateFrom, leaveDateTo, occupancyDays, status, billId);
	}

	@At
	public void checkOut(@Param("checkOutIdArr[]") int[] ids, @Param("leaveDate") String leaveDate,
			HttpServletRequest req) throws ParseException {
		roomOccupancyService.checkOut(ids, leaveDate);
	}
}
