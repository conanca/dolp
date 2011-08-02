package com.dolplay.dolpinhotel.setup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;

@IocBean(args = { "refer:dao" })
public class RoomService extends DolpBaseService<Room> {

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
	public AjaxResData CUDRoom(String oper, String ids, Room room) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(room);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(room);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
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
