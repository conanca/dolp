package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;

import java.util.Map;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("roomTypeModule")
@At("/dolpinhotel/setup/roomtype")
public class RoomTypeModule {

	private RoomTypeService roomTypeService;

	@At
	public JqgridAdvancedData<RoomType> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return roomTypeService.getGridData(page, rows, sidx, sord);
	}

	@At
	public void editRow(@Param("oper") String oper, @Param("id") String id, @Param("name") String name,
			@Param("price") String price, @Param("description") String description) {
		roomTypeService.CUDRoomType(oper, id, name, price, description);
	}

	@At
	public Map<String, Integer> getAllRoomTypes() {
		return roomTypeService.getAllRoomTypes();
	}

}
