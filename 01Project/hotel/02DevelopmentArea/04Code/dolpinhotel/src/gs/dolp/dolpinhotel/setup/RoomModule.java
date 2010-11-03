package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomModule")
@At("/dolpinhotel/setup/room")
public class RoomModule {

	private RoomService roomService;

	@At
	public JqgridAdvancedData<Room> getJqgridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord, @Param("number") String number,
			@Param("isOccupancy") String isOccupancy, @Param("roomTypeId") String roomTypeId) {
		return roomService.getJqgridData(page, rows, sidx, sord, number, isOccupancy, roomTypeId);
	}

	@At
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("number") String number,
			@Param("roomTypeId") String roomTypeId, @Param("isOccupancy") String isOccupancy) {
		roomService.CUDRoom(oper, id, number, roomTypeId, isOccupancy);
	}

	@At
	public Map<String, String> getAllAvailableRoomForSelectOption(@Param("myid") int roomTypeId) {
		return roomService.getAllAvailableRoomForSelectOption(roomTypeId);
	}

	@At
	public Map<String, String> getAllRoomForSelectOption() {
		return roomService.getAllRoomForSelectOption();
	}

}
