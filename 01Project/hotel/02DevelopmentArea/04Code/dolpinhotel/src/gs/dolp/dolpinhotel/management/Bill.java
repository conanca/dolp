package gs.dolp.dolpinhotel.management;

import java.sql.Timestamp;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_BILL")
public class Bill {
	@Id
	private int id;
	@Column
	private String number;
	@Column
	private double amount;
	@Column
	private Timestamp date;
	@Many(target = RoomOccupancy.class, field = "billId")
	private List<RoomOccupancy> roomOccupancy;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<RoomOccupancy> getRoomOccupancy() {
		return roomOccupancy;
	}

	public void setRoomOccupancy(List<RoomOccupancy> roomOccupancy) {
		this.roomOccupancy = roomOccupancy;
	}

}
