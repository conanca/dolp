package gs.dolp.dolpinhotel.setup;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_ROOM")
public class Room {
	@Id
	private int id;
	@Column
	private String number;
	@Column
	private int roomTypeId;
	@One(target = RoomType.class, field = "roomTypeId")
	private RoomType roomType;
	@Column
	private int isOccupancy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public int getIsOccupancy() {
		return isOccupancy;
	}

	public void setIsOccupancy(int isOccupancy) {
		this.isOccupancy = isOccupancy;
	}

}
