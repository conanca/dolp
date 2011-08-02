package com.dolplay.dolpinhotel.setup;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_ROOM")
public class Room {
	@Id
	private Integer id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String number;
	@Column
	private Integer roomTypeId;
	@One(target = RoomType.class, field = "roomTypeId")
	private RoomType roomType;
	@Column
	private Boolean isOccupancy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Boolean getIsOccupancy() {
		return isOccupancy;
	}

	public void setIsOccupancy(Boolean isOccupancy) {
		this.isOccupancy = isOccupancy;
	}
}
