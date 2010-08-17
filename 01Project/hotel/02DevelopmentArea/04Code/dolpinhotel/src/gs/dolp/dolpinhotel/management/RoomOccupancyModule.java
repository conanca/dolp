package gs.dolp.dolpinhotel.management;

import java.text.ParseException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
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
}
