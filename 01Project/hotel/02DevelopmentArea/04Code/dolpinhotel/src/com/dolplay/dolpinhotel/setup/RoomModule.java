package com.dolplay.dolpinhotel.setup;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/setup/room")
public class RoomModule {

	@Inject
	private RoomService roomService;

	@At
	public ResponseData getJqgridData(@Param("..") JqgridReqData jqReq, @Param("number") String number,
			@Param("isOccupancy") String isOccupancy, @Param("roomTypeId") String roomTypeId) {
		return roomService.getJqgridData(jqReq, number, isOccupancy, roomTypeId);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Room room) {
		return roomService.CUDRoom(oper, ids, room);
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
