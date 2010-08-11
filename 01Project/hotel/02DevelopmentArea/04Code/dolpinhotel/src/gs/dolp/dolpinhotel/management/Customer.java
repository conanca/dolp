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
	private int isPayer;
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

	public int getIsPayer() {
		return isPayer;
	}

	public void setIsPayer(int isPayer) {
		this.isPayer = isPayer;
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