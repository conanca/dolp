package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.JqgridData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

public class RoomService extends IdEntityService<Room> {
	private static final Log log = Logs.getLog(RoomService.class);

	public RoomService(Dao dao) {
		super(dao);
	}

	public JqgridData<Room> getGridData(String page, String rows, String sidx, String sord) {
		int pageNumber = 1;
		int pageSize = 10;
		String sortColumn = "ID";
		String sortOrder = "ASC";
		if (!Strings.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!Strings.isEmpty(rows)) {
			pageSize = Integer.parseInt(rows);
		}
		if (!Strings.isEmpty(sidx)) {
			sortColumn = sidx;
		}
		if (!Strings.isEmpty(sord)) {
			sortOrder = sord;
		}
		Pager pager = dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("1=1 ORDER BY " + sortColumn + " " + sortOrder);
		// 查询
		List<Room> list = query(cnd, pager);
		// 合计记录总数
		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 封装jqGrid的json格式数据类
		JqgridData<Room> jq = new JqgridData<Room>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		log.debug(jq.toString());
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
		roomOptions.put("请选择", "0");
		for (Room room : rooms) {
			roomOptions.put(room.getNumber(), String.valueOf(room.getId()));
		}
		log.debug(roomOptions);
		return roomOptions;
	}
}
