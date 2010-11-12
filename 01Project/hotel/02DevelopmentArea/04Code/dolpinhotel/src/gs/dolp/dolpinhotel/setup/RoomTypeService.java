package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class RoomTypeService extends AdvJqgridIdEntityService<RoomType> {

	public RoomTypeService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<RoomType> getGridData(JqgridReqData jqRe) {
		AdvancedJqgridResData<RoomType> jq = getAdvancedJqgridRespData(null, jqRe);
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

	@Aop(value = "log")
	public Map<String, Integer> getAllRoomTypes() {
		List<RoomType> allRoomTypes = dao().query(RoomType.class, null, null);
		Map<String, Integer> roomTypeMap = new HashMap<String, Integer>();
		for (RoomType roomType : allRoomTypes) {
			roomTypeMap.put(roomType.getName(), roomType.getId());
		}
		return roomTypeMap;
	}

}
