package gs.dolp.dolpinhotel.setup;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.JqgridService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoomTypeService extends JqgridService<RoomType> {

	public RoomTypeService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<RoomType> getGridData(JqgridReqData jqRe) {
		AdvancedJqgridResData<RoomType> jq = getAdvancedJqgridRespData(null, jqRe);
		return jq;
	}

	public AjaxResData CUDRoomType(String oper, String id, String name, String price, String description) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			final List<RoomType> roomTypes = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (RoomType roomType : roomTypes) {
						dao().clearLinks(roomType, "rooms");
					}
					clear(cnd);
				}
			});
			respData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			RoomType roomType = new RoomType();
			roomType.setName(name);
			roomType.setPrice(Double.parseDouble(price));
			roomType.setDescription(description);
			dao().insert(roomType);
			respData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			RoomType roomType = new RoomType();
			roomType.setId(Integer.parseInt(id));
			roomType.setName(name);
			roomType.setPrice(Double.parseDouble(price));
			roomType.setDescription(description);
			dao().update(roomType);
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getAllRoomTypeMap() {
		AjaxResData respData = new AjaxResData();
		List<RoomType> allRoomTypes = dao().query(RoomType.class, null, null);
		Map<String, Integer> roomTypeMap = new LinkedHashMap<String, Integer>();
		for (RoomType roomType : allRoomTypes) {
			roomTypeMap.put(roomType.getName(), roomType.getId());
		}
		respData.setReturnData(roomTypeMap);
		return respData;
	}

}
