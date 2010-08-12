package gs.dolp.dolpinhotel.management;

import java.sql.Timestamp;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("DOLPINHOTEL_ORDER")
public class Order {
	@Column
	@Id
	private int id;
	@Column
	private String number;
	@Column("CHECK_IN_DATE")
	private Timestamp checkInDate;
	@Column("EXPECTED_CHECK_OUT_DATE")
	private Timestamp expectedCheckOutDate;
	@Column("CHECK_OUT_DATE")
	private Timestamp checkOutDate;
	@Column("OCCUPANCY_DAYS")
	private int occupancyDays;
	@Column
	private double amount;
	@Many(target = OrderItem.class, field = "orderId")
	private List<OrderItem> orderItems;
	@Many(target = Customer.class, field = "orderId")
	private List<Customer> customers;
	@Column
	private int payerId;
	@One(target = Customer.class, field = "payerId")
	public Customer payer;

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

	public Timestamp getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Timestamp checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Timestamp getExpectedCheckOutDate() {
		return expectedCheckOutDate;
	}

	public void setExpectedCheckOutDate(Timestamp expectedCheckOutDate) {
		this.expectedCheckOutDate = expectedCheckOutDate;
	}

	public Timestamp getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Timestamp checkOutDate) {
		this.checkOutDate = checkOutDate;
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

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public int getPayerId() {
		return payerId;
	}

	public void setPayerId(int payerId) {
		this.payerId = payerId;
	}

	public Customer getPayer() {
		return payer;
	}

	public void setPayer(Customer payer) {
		this.payer = payer;
	}

}
