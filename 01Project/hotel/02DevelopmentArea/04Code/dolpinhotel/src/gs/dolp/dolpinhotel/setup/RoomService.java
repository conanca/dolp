package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class RoomService extends IdEntityForjqGridService<Room> {
	private static final Log log = Logs.getLog(RoomService.class);

	public RoomService(Dao dao) {
		super(dao);
	}

	public JqgridData<Room> getJqgridData(String page, String rows, String sidx, String sord, String number,
			String isOccupancy, String roomTypeId) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != number && !"".equals(number)) {
			cnd = cnd.and("number", "LIKE", "%" + number + "%");
		}
		if (null != isOccupancy && !"-1".equals(isOccupancy)) {
			cnd = cnd.and("isOccupancy", "=", isOccupancy);
		}
		if (null != roomTypeId && !"-1".equals(roomTypeId)) {
			cnd = cnd.and("roomTypeId", "=", roomTypeId);
		}
		JqgridData<Room> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public void deleteRooms(String ids) {
		if (!Strings.isEmpty(ids)) {
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			clear(cnd);
		}
	}

	public Map<String, String> getAllAvailableRoomForSelectOption(int roomTypeId) {
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		Condition cnd = Cnd.where("ISOCCUPANCY", "=", 0).and("ROOMTYPEID", "=", roomTypeId);
		List<Room> rooms = this.query(cnd, null);
		for (Room room : rooms) {
			roomOptions.put(room.getNumber(), String.valueOf(room.getId()));
		}
		log.debug(roomOptions);
		return roomOptions;
	}

	public Map<String, String> getAllRoomForSelectOption() {
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		List<Room> rooms = this.query(null, null);
		for (Room room : rooms) {
			roomOptions.put(String.valueOf(room.getId()), room.getNumber());
		}
		log.debug(roomOptions);
		return roomOptions;
	}
}
