package gs.dolp.dolpinhotel.setup;

import gs.dolp.jqgrid.JqgridData;

import java.util.HashMap;
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

public class RoomTypeService extends IdEntityService<RoomType> {
	private static final Log log = Logs.getLog(RoomTypeService.class);

	public RoomTypeService(Dao dao) {
		super(dao);
	}

	public JqgridData<RoomType> getGridData(String page, String rows, String sidx, String sord) {
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
		List<RoomType> list = query(cnd, pager);
		// 合计记录总数
		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 封装jqGrid的json格式数据类
		JqgridData<RoomType> jq = new JqgridData<RoomType>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		log.debug(jq.toString());
		return jq;
	}

	public void deleteRoomTypes(String ids) {
		if (!Strings.isEmpty(ids)) {
			Condition cnd = Cnd.wrap("ID IN (" + ids + ")");
			clear(cnd);
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
