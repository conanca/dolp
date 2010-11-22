package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseData;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomTypeModule")
@At("/dolpinhotel/setup/roomtype")
public class RoomTypeModule {

	private RoomTypeService roomTypeService;

	@At
	public AdvancedJqgridResData<RoomType> getGridData(@Param("..") JqgridReqData jqReq) {
		return roomTypeService.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("price") String price, @Param("description") String description) {
		return roomTypeService.CUDRoomType(oper, id, name, price, description);
	}

	@At
	public Map<String, Integer> getAllRoomTypes() {
		return roomTypeService.getAllRoomTypes();
	}

}
