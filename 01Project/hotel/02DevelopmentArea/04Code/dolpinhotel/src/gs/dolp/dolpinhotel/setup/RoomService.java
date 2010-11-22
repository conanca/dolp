package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;

public class RoomService extends AdvJqgridIdEntityService<Room> {

	public RoomService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Room> getJqgridData(JqgridReqData jqRe, String number, String isOccupancy,
			String roomTypeId) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			cnd = cnd.and("NUMBER", "LIKE", "%" + Strings.trim(number) + "%");
		}
		if (!Strings.isBlank(isOccupancy) && !"-1".equals(isOccupancy)) {
			cnd = cnd.and("ISOCCUPANCY", "=", isOccupancy);
		}
		if (!Strings.isBlank(roomTypeId) && !"-1".equals(roomTypeId)) {
			cnd = cnd.and("ROOMTYPEID", "=", roomTypeId);
		}
		AdvancedJqgridResData<Room> jq = getAdvancedJqgridRespData(cnd, jqRe);
		return jq;
	}

	@Aop(value = "log")
	public ResponseSysMsgData CUDRoom(String oper, String id, String number, String roomTypeId, String isOccupancy) {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			Room room = new Room();
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().insert(room);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Room room = new Room();
			room.setId(Integer.parseInt(id));
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().update(room);
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}

	@Aop(value = "log")
	public Map<String, String> getAllAvailableRoomForSelectOption(int roomTypeId) {
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		Condition cnd = Cnd.where("ISOCCUPANCY", "=", 0).and("ROOMTYPEID", "=", roomTypeId);
		List<Room> rooms = this.query(cnd, null);
		for (Room room : rooms) {
			roomOptions.put(room.getNumber(), String.valueOf(room.getId()));
		}
		return roomOptions;
	}

	@Aop(value = "log")
	public Map<String, String> getAllRoomForSelectOption() {
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		List<Room> rooms = this.query(null, null);
		for (Room room : rooms) {
			roomOptions.put(String.valueOf(room.getId()), room.getNumber());
		}
		return roomOptions;
	}
}
