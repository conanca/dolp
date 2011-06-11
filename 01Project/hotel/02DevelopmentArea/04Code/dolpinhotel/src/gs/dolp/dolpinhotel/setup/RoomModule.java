package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomModule")
@At("/dolpinhotel/setup/room")
public class RoomModule {

	private RoomService roomService;

	@At
	public ResponseData getJqgridData(@Param("..") JqgridReqData jqReq, @Param("number") String number,
			@Param("isOccupancy") String isOccupancy, @Param("roomTypeId") String roomTypeId) {
		return roomService.getJqgridData(jqReq, number, isOccupancy, roomTypeId);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("number") String number,
			@Param("roomTypeId") String roomTypeId, @Param("isOccupancy") String isOccupancy) {
		return roomService.CUDRoom(oper, id, number, roomTypeId, isOccupancy);
	}

	@At
	public Map<String, String> getAllAvailableRoomForSelectOption(@Param("myid") int roomTypeId) {
		return roomService.getAllAvailableRoomForSelectOption(roomTypeId);
	}

	@At
	public ResponseData getAllRoomForSelectOption() {
		return roomService.getAllRoomForSelectOption();
	}

}
