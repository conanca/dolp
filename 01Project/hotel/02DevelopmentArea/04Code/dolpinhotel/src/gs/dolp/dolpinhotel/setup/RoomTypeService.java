package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class RoomTypeService extends IdEntityForjqGridService<RoomType> {
	private static final Log log = Logs.getLog(RoomTypeService.class);

	public RoomTypeService(Dao dao) {
		super(dao);
	}

	public JqgridData<RoomType> getGridData(String page, String rows, String sidx, String sord) {
		JqgridData<RoomType> jq = getjqridDataByCnd(null, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public void CUDRoomType(String oper, String id, String name, String price, String description) {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
		}
		if ("add".equals(oper)) {
			RoomType roomType = new RoomType();
			roomType.setName(name);
			roomType.setPrice(Double.parseDouble(price));
			roomType.setDescription(description);
			dao().insert(roomType);
		}
		if ("edit".equals(oper)) {
			RoomType roomType = new RoomType();
			roomType.setId(Integer.parseInt(id));
			roomType.setName(name);
			roomType.setPrice(Double.parseDouble(price));
			roomType.setDescription(description);
			dao().update(roomType);
		}
	}

	public Map<Integer, String> getAllRoomTypes() {
		List<RoomType> allRoomTypes = dao().query(RoomType.class, null, null);
		Map<Integer, String> roomTypeMap = new HashMap<Integer, String>();
		for (RoomType roomType : allRoomTypes) {
			roomTypeMap.put(roomType.getId(), roomType.getName());
		}
		log.debug(roomTypeMap);
		return roomTypeMap;
	}

}
