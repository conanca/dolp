package gs.dolp.dolpinhotel.management;

import gs.dolp.dolpinhotel.setup.Room;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_ROOM_OCCUPANCY")
public class RoomOccupancy {
	@Column
	@Id
	private int id;
	@Column
	private int roomId;
	@One(target = Room.class, field = "roomId")
	private Room room;
	@Column("ENTER_DATE")
	private Timestamp enterDate;
	@Column("LEAVE_DATE")
	private Timestamp leaveDate;
	@Column("OCCUPANCY_DAYS")
	private int occupancyDays;
	@Column
	private double amount;
	@Column
	private int status;
	@Column
	private int billId;
	@One(target = Bill.class, field = "billId")
	private Bill bill;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Timestamp getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Timestamp enterDate) {
		this.enterDate = enterDate;
	}

	public Timestamp getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Timestamp leaveDate) {
		this.leaveDate = leaveDate;
	}

	public int getOccupancyDays() {
		return occupancyDays;
	}

	public void setOccupancyDays(int occupancyDays) {
		this.occupancyDays = occupancyDays;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}
