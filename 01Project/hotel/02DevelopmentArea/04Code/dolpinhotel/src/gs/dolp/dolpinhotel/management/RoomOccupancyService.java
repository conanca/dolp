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
			StringBuffer roomIds = new StringBuffer("(");
			for (Room room : rooms) {
				roomIds.append(room.getId());
				roomIds.append(",");
			}
			String roomIdsStr = roomIds.substring(0, roomIds.length() - 1);
			roomIdsStr = roomIdsStr + ")";
			Expression e = (Expression) Cnd.wrap("ROOMID IN" + roomIdsStr);
			cnd = cnd.and(e);
		}
		if (null != enterDateFrom && !"".equals(enterDateFrom)) {
			cnd = cnd.and("ENTERDATE", ">=", enterDateFrom);
		}
		if (null != enterDateTo && !"".equals(enterDateTo)) {
			cnd = cnd.and("ENTERDATE", "<=", enterDateTo);
		}
		if (null != expectedCheckOutDateFrom && !"".equals(expectedCheckOutDateFrom)) {
			cnd = cnd.and("EXPECTEDCHECKOUTDATE", ">=", expectedCheckOutDateFrom);
		}
		if (null != expectedCheckOutDateTo && !"".equals(expectedCheckOutDateTo)) {
			cnd = cnd.and("EXPECTEDCHECKOUTDATE", "<=", expectedCheckOutDateTo);
		}
		if (null != leaveDateFrom && !"".equals(leaveDateFrom)) {
			cnd = cnd.and("LEAVEDATE", ">=", leaveDateFrom);
		}
		if (null != leaveDateTo && !"".equals(leaveDateTo)) {
			cnd = cnd.and("LEAVEDATE", "<=", leaveDateTo);
		}
		if (null != occupancyDays && !"".equals(occupancyDays)) {
			cnd = cnd.and("NUMBER", "=", occupancyDays);
		}
		if (null != status && !"-1".equals(status)) {
			cnd = cnd.and("ISOCCUPANCY", "=", status);
		}
		JqgridData<RoomOccupancy> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}
}
