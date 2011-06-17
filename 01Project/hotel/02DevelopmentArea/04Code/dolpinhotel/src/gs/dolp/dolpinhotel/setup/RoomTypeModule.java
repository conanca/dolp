package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomTypeModule")
@At("/dolpinhotel/setup/roomtype")
public class RoomTypeModule {

	private RoomTypeService roomTypeService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return roomTypeService.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") RoomType roomType) {
		return roomTypeService.CUDRoomType(oper, ids, roomType);
	}

	@At
	public ResponseData getAllRoomTypeMap() {
		return roomTypeService.getAllRoomTypeMap();
	}

}
