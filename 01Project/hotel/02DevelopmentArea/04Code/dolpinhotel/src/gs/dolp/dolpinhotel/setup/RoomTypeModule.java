package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.JqgridData;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("roomTypeModule")
@At("/dolpinhotel/setup/roomtype")
@Fail("json")
public class RoomTypeModule {

	private RoomTypeService roomTypeService;

	@At
	@Ok("json")
	public JqgridData<RoomType> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return roomTypeService.getGridData(page, rows, sidx, sord);
	}

	@At
	@Fail("json")
	public void save(@Param("..") RoomType roomType) {
		if (roomType.getId() == 0) {
			roomTypeService.dao().insert(roomType);
		} else {
			roomTypeService.dao().update(roomType);
		}
	}

	@At
	@Fail("json")
	public void deleteRow(@Param("id") String ids) {
		roomTypeService.deleteRoomTypes(ids);
	}

	@At
	@Ok("json")
	public Map<Integer, String> getAllRoomTypes() {
		return roomTypeService.getAllRoomTypes();
	}

}
