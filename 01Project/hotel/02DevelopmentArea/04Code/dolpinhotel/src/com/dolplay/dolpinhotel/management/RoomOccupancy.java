package com.dolplay.dolpinhotel.management;

import java.sql.Timestamp;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.dolplay.dolpinhotel.setup.Room;

@Table("DOLPINHOTEL_ROOM_OCCUPANCY")
public class RoomOccupancy {
	@Id
	private Integer id;
	@Column
	private Integer roomId;
	@One(target = Room.class, field = "roomId")
	private Room room;
	@Column("ENTER_DATE")
	private Timestamp enterDate;
	@Column("EXPECTED_CHECK_OUT_DATE")
	private Timestamp expectedCheckOutDate;
	@Column("LEAVE_DATE")
	private Timestamp leaveDate;
	@Column("OCCUPANCY_DAYS")
	private Integer occupancyDays;
	@Column
	@ColDefine(type = ColType.FLOAT, width = 10, precision = 2)
	private Double amount;
	@Column
	private Integer status;
	@Column
	private Integer billId;
	@One(target = Bill.class, field = "billId")
	private Bill bill;
	@Many(target = Customer.class, field = "roomOccupancyId")
	private List<Customer> customer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
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

	public Timestamp getExpectedCheckOutDate() {
		return expectedCheckOutDate;
	}

	public void setExpectedCheckOutDate(Timestamp expectedCheckOutDate) {
		this.expectedCheckOutDate = expectedCheckOutDate;
	}

	public Timestamp getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Timestamp leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Integer getOccupancyDays() {
		return occupancyDays;
	}

	public void setOccupancyDays(Integer occupancyDays) {
		this.occupancyDays = occupancyDays;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}
}
