package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;

public class RoomService extends JqgridService<Room> {

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
	public AjaxResData CUDRoom(String oper, String id, String number, String roomTypeId, String isOccupancy) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			Room room = new Room();
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().insert(room);
			respData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			Room room = new Room();
			room.setId(Integer.parseInt(id));
			room.setNumber(number);
			room.setRoomTypeId(Integer.parseInt(roomTypeId));
			room.setIsOccupancy(Integer.parseInt(isOccupancy));
			dao().update(room);
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public Map<String, String> getAllAvailableRoomForSelectOption(int roomTypeId) {
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		Condition cnd = Cnd.where("ISOCCUPANCY", "=", 0).and("ROOMTYPEID", "=", roomTypeId);
		List<Room> rooms = query(cnd, null);
		for (Room room : rooms) {
			roomOptions.put(room.getNumber(), String.valueOf(room.getId()));
		}
		return roomOptions;
	}

	@Aop(value = "log")
	public AjaxResData getAllRoomForSelectOption() {
		AjaxResData respData = new AjaxResData();
		Map<String, String> roomOptions = new LinkedHashMap<String, String>();
		List<Room> rooms = query(null, null);
		for (Room room : rooms) {
			roomOptions.put(String.valueOf(room.getId()), room.getNumber());
		}
		respData.setReturnData(roomOptions);
		return respData;
	}
}
