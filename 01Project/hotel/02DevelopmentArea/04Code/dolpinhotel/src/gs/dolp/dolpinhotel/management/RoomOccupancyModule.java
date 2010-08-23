package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.JqgridData;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("roomOccupancyModule")
@At("/dolpinhotel/management/roomoccupancy")
@Fail("json")
public class RoomOccupancyModule {
	private RoomOccupancyService roomOccupancyService;

	@At
	public void saveRoomOccupancy(@Param("enterDate") String enterDate,
			@Param("expectedCheckOutDate") String expectedCheckOutDate, @Param("roomId") int roomId,
			@Param("customers") Customer[] customers) throws ParseException {
		roomOccupancyService.saveRoomOccupancy(enterDate, expectedCheckOutDate, roomId, customers);
	}

	@At
	@Ok("json")
	public JqgridData<RoomOccupancy> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord, @Param("number") String number,
			@Param("enterDateFrom") String enterDateFrom, @Param("enterDateTo") String enterDateTo,
			@Param("expectedCheckOutDateFrom") String expectedCheckOutDateFrom,
			@Param("expectedCheckOutDateTo") String expectedCheckOutDateTo,
			@Param("leaveDateFrom") String leaveDateFrom, @Param("leaveDateTo") String leaveDateTo,
			@Param("occupancyDays") String occupancyDays, @Param("status") String status, @Param("billId") int billId) {
		return roomOccupancyService.getGridData(page, rows, sidx, sord, number, enterDateFrom, enterDateTo,
				expectedCheckOutDateFrom, expectedCheckOutDateTo, leaveDateFrom, leaveDateTo, occupancyDays, status,
				billId);
	}

	@At
	public void checkOut(@Param("checkOutIdArr[]") int[] ids, @Param("leaveDate") String leaveDate,
			HttpServletRequest req) throws ParseException {
		roomOccupancyService.checkOut(ids, leaveDate);
	}
}
