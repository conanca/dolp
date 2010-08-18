package gs.dolp.dolpinhotel.management;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_CUSTOMER")
public class Customer {
	@Column
	@Id
	private int id;
	@Column
	private int no;
	@Column
	private String name;
	@Column
	private String gender;
	@Column("CERTIFICATE_TYPE")
	private String certificateType;
	@Column("CREDENTIAL_NUMBER")
	private String credentialNumber;
	@Column
	private String address;
	@Column
	private int roomOccupancyId;
	@One(target = RoomOccupancy.class, field = "roomOccupancyId")
	private RoomOccupancy roomOccupancy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}

	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRoomOccupancyId() {
		return roomOccupancyId;
	}

	public void setRoomOccupancyId(int roomOccupancyId) {
		this.roomOccupancyId = roomOccupancyId;
	}

	public RoomOccupancy getRoomOccupancy() {
		return roomOccupancy;
	}

	public void setRoomOccupancy(RoomOccupancy roomOccupancy) {
		this.roomOccupancy = roomOccupancy;
	}

}