package com.dolplay.dolpinhotel.management;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_CUSTOMER")
public class Customer {
	@Id
	private Integer id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	@Column
	@ColDefine(type = ColType.CHAR, width = 2)
	private String gender;
	@Column("CERTIFICATE_TYPE")
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String certificateType;
	@Column("CREDENTIAL_NUMBER")
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String credentialNumber;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String address;
	@Column
	private Integer roomOccupancyId;
	@One(target = RoomOccupancy.class, field = "roomOccupancyId")
	private RoomOccupancy roomOccupancy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getRoomOccupancyId() {
		return roomOccupancyId;
	}

	public void setRoomOccupancyId(Integer roomOccupancyId) {
		this.roomOccupancyId = roomOccupancyId;
	}

	public RoomOccupancy getRoomOccupancy() {
		return roomOccupancy;
	}

	public void setRoomOccupancy(RoomOccupancy roomOccupancy) {
		this.roomOccupancy = roomOccupancy;
	}
}