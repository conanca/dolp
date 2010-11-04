package gs.dolp.dolpinhotel.management;

import gs.dolp.dolpinhotel.setup.Room;
import gs.dolp.dolpinhotel.setup.RoomType;
import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Expression;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class RoomOccupancyService extends IdEntityForjqGridService<RoomOccupancy> {

	public RoomOccupancyService(Dao dao) {
		super(dao);
	}

	/**
	 * 房间入住登记——涉及到保存房间入住表，顾客信息表等操作
	 * @param enterDate
	 * @param expectedCheckOutDate
	 * @param roomId
	 * @param customers
	 * @throws ParseException
	 */
	@Aop(value = "log")
	public void saveRoomOccupancy(String enterDate, String expectedCheckOutDate, final int roomId,
			final Customer[] customers) throws ParseException {

		if (!Strings.isBlank(enterDate) && roomId != 0) {
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

	@Aop(value = "log")
	public JqgridAdvancedData<RoomOccupancy> getGridData(String page, String rows, String sidx, String sord,
			String number, String enterDateFrom, String enterDateTo, String expectedCheckOutDateFrom,
			String expectedCheckOutDateTo, String leaveDateFrom, String leaveDateTo, String occupancyDays,
			String status, int billId) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (!Strings.isBlank(number)) {
			List<Room> rooms = dao().query(Room.class, Cnd.wrap("NUMBER LIKE '%" + number + "%'"), null);
			int[] roomIds = new int[rooms.size()];
			for (int i = 0; i < rooms.size(); i++) {
				roomIds[i] = rooms.get(i).getId();
			}
			Expression e = Cnd.where("ROOMID", "IN", roomIds);
			cnd = cnd.and(e);
		}
		if (!Strings.isBlank(enterDateFrom)) {
			cnd = cnd.and("ENTER_DATE", ">=", enterDateFrom);
		}
		if (!Strings.isBlank(enterDateTo)) {
			cnd = cnd.and("ENTER_DATE", "<=", enterDateTo);
		}
		if (!Strings.isBlank(expectedCheckOutDateFrom)) {
			cnd = cnd.and("EXPECTED_CHECK_OUT_DATE", ">=", expectedCheckOutDateFrom);
		}
		if (!Strings.isBlank(expectedCheckOutDateTo)) {
			cnd = cnd.and("EXPECTED_CHECK_OUT_DATE", "<=", expectedCheckOutDateTo);
		}
		if (!Strings.isBlank(leaveDateFrom)) {
			cnd = cnd.and("LEAVE_DATE", ">=", leaveDateFrom);
		}
		if (!Strings.isBlank(leaveDateTo)) {
			cnd = cnd.and("LEAVE_DATE", "<=", leaveDateTo);
		}
		if (!Strings.isBlank(occupancyDays)) {
			cnd = cnd.and("OCCUPANCY_DAYS", "=", occupancyDays);
		}
		if (!Strings.isBlank(status) && !"-1".equals(status)) {
			cnd = cnd.and("STATUS", "=", status);
		}
		if (billId != 0) {
			cnd = cnd.and("BILLID", "=", billId);
		}
		JqgridAdvancedData<RoomOccupancy> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		return jq;
	}

	/**
	 * 结帐——涉及新建账单，更新房间入住的离开时间、入住天数和消费金额，更新房间状态 等操作
	 * @param ids
	 * @param leaveDate
	 * @throws ParseException
	 */
	@Aop(value = "log")
	public void checkOut(final int[] ids, String leaveDate) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final Timestamp leaveDateTime = new Timestamp(dateFormat.parse(leaveDate).getTime());

		final Bill bill = new Bill();
		bill.setDate(leaveDateTime);
		final StringBuffer BillNoSb = new StringBuffer("DH");
		BillNoSb.append(new SimpleDateFormat("yyyyMMdd").format(leaveDateTime));

		Trans.exec(new Atom() {
			public void run() {

				int currDayBillCount = dao().count(Bill.class, Cnd.where("DATE", "=", leaveDateTime)) + 1;
				BillNoSb.append(Strings.alignRight(String.valueOf(currDayBillCount), 4, '0'));
				bill.setNumber(BillNoSb.toString());
				dao().insert(bill);
				int billId = bill.getId();
				double billAmount = 0;

				for (int id : ids) {
					RoomOccupancy roomOccupancy = dao().fetchLinks(fetch((long) id), "room");
					// 获取房间并更新其状态
					Room room = roomOccupancy.getRoom();
					room.setIsOccupancy(0);
					roomOccupancy.setRoom(room);
					roomOccupancy.setStatus(1);
					// 设置离开日期
					roomOccupancy.setLeaveDate(leaveDateTime);
					// 计算并设置消费金额
					int days = (int) (leaveDateTime.getTime() - roomOccupancy.getEnterDate().getTime()) / 86400000;
					roomOccupancy.setOccupancyDays(days);
					RoomType roomType = dao().fetch(RoomType.class, room.getRoomTypeId());
					double amount = roomType.getPrice() * days;
					billAmount += amount;
					roomOccupancy.setAmount(amount);
					roomOccupancy.setBillId(billId);
					dao().update(roomOccupancy);
					dao().update(room);
				}
				bill.setAmount(billAmount);
				dao().update(bill);
			}
		});
	}
}