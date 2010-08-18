package gs.dolp.dolpinhotel.management;

import gs.dolp.dolpinhotel.setup.Room;
import gs.dolp.jqgrid.JqgridData;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoomOccupancyService extends IdEntityService<RoomOccupancy> {
	private static final Log log = Logs.getLog(RoomOccupancyService.class);

	public RoomOccupancyService(Dao dao) {
		super(dao);
	}

	public void saveRoomOccupancy(String enterDate, String expectedCheckOutDate, final int roomId,
			final Customer[] customers) throws ParseException {

		if (null != enterDate && !"".equals(enterDate) && roomId != 0) {
			final RoomOccupancy roomOccupancy = new RoomOccupancy();
			roomOccupancy.setRoomId(roomId);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp enterDateTime = new Timestamp(dateFormat.parse(enterDate).getTime());
			roomOccupancy.setEnterDate(enterDateTime);
			if (null != expectedCheckOutDate && !"".equals(expectedCheckOutDate)) {
				Timestamp expectedCheckOutDateTime = new Timestamp(dateFormat.parse(expectedCheckOutDate).getTime());
				roomOccupancy.setExpectedCheckOutDate(expectedCheckOutDateTime);
			}
			//获取入住的房间，并修改其状态
			final Room room = dao().fetch(Room.class, (long) roomId);
			room.setIsOccupancy(1);

			//插入房间入住表和顾客信息表这两个操作放到一个事务中
			Trans.exec(new Atom() {
				public void run() {
					//插入房间入住表
					dao().insert(roomOccupancy);
					//更新数据库中所入住的房间状态
					dao().update(room);
					//插入顾客信息表
					int roomOccupancyId = roomOccupancy.getId();
					for (Customer customer : customers) {
						customer.setRoomOccupancyId(roomOccupancyId);
						dao().insert(customer);
					}
				}
			});
		}
	}

	public JqgridData<RoomOccupancy> getGridData(String page, String rows, String sidx, String sord) {
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
		List<RoomOccupancy> list = query(cnd, pager);
		// 合计记录总数
		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 封装jqGrid的json格式数据类
		JqgridData<RoomOccupancy> jq = new JqgridData<RoomOccupancy>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		log.debug(jq.toString());
		return jq;
	}
}
