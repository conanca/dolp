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
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("price") String price, @Param("description") String description) {
		roomTypeService.CUDRoomType(oper, id, name, price, description);
	}

	@At
	@Ok("json")
	public Map<String, Integer> getAllRoomTypes() {
		return roomTypeService.getAllRoomTypes();
	}

}
