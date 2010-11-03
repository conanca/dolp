package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;

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

	public JqgridAdvancedData<Room> getJqgridData(String page, String rows, String sidx, String sord, String number,
			String isOccupancy, String roomTypeId) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			cnd = cnd.and("NUMBER", "LIKE", "%" + number + "%");
		}
		if (!Strings.isBlank(isOccupancy) && !"-1".equals(isOccupancy)) {
			cnd = cnd.and("ISOCCUPANCY", "=", isOccupancy);
		}
		if (!Strings.isBlank(roomTypeId) && !"-1".equals(roomTypeId)) {
			cnd = cnd.and("ROOMTYPEID", "=", roomTypeId);
		}
		JqgridAdvancedData<Room> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

	public void CUDRoom(String oper, String id, String number, String roomTypeId, String isOccupancy) {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
		}
		if ("add".equals(oper)) {
			Room room = new Room();
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().insert(room);
		}
		if ("edit".equals(oper)) {
			Room room = new Room();
			room.setId(Integer.parseInt(id));
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().update(room);
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
