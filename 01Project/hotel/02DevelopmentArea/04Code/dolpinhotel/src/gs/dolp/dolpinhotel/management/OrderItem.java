package gs.dolp.dolpinhotel.management;

import gs.dolp.dolpinhotel.setup.Room;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("ORDER_ITEM")
public class OrderItem {
	@Column
	@Id
	private int id;
	@Column
	private int roomId;
	@One(target = Room.class, field = "roomId")
	public Room room;
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
	private int orderId;
	@One(target = Order.class, field = "orderId")
	public Order order;

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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
