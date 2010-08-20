package gs.dolp.dolpinhotel.management;

import gs.dolp.dolpinhotel.setup.Room;
import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Expression;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoomOccupancyService extends IdEntityForjqGridService<RoomOccupancy> {
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

	public JqgridData<RoomOccupancy> getGridData(String page, String rows, String sidx, String sord, String number,
			String enterDateFrom, String enterDateTo, String expectedCheckOutDateFrom, String expectedCheckOutDateTo,
			String leaveDateFrom, String leaveDateTo, String occupancyDays, String status) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != number && !"".equals(number)) {
			List<Room> rooms = dao().query(Room.class, Cnd.wrap("NUMBER LIKE '%" + number + "%'"), null);
			int[] roomIds = new int[rooms.size()];
			for (int i = 0; i < rooms.size(); i++) {
				roomIds[i] = rooms.get(i).getId();
			}
			Expression e = Cnd.where("ROOMID", "IN", roomIds);
			cnd = cnd.and(e);
		}
		if (null != enterDateFrom && !"".equals(enterDateFrom)) {
			cnd = cnd.and("ENTER_DATE", ">=", enterDateFrom);
		}
		if (null != enterDateTo && !"".equals(enterDateTo)) {
			cnd = cnd.and("ENTER_DATE", "<=", enterDateTo);
		}
		if (null != expectedCheckOutDateFrom && !"".equals(expectedCheckOutDateFrom)) {
			cnd = cnd.and("EXPECTED_CHECK_OUT_DATE", ">=", expectedCheckOutDateFrom);
		}
		if (null != expectedCheckOutDateTo && !"".equals(expectedCheckOutDateTo)) {
			cnd = cnd.and("EXPECTED_CHECK_OUT_DATE", "<=", expectedCheckOutDateTo);
		}
		if (null != leaveDateFrom && !"".equals(leaveDateFrom)) {
			cnd = cnd.and("LEAVE_DATE", ">=", leaveDateFrom);
		}
		if (null != leaveDateTo && !"".equals(leaveDateTo)) {
			cnd = cnd.and("LEAVE_DATE", "<=", leaveDateTo);
		}
		if (null != occupancyDays && !"".equals(occupancyDays)) {
			cnd = cnd.and("OCCUPANCY_DAYS", "=", occupancyDays);
		}
		if (null != status && !"-1".equals(status)) {
			cnd = cnd.and("STATUS", "=", status);
		}
		JqgridData<RoomOccupancy> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}
}
