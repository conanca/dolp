package gs.dolp.system.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_USER")
public class User {
	@Id
	private int id;
	@Column
	private String number;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String gender;
	@Column
	private int age;
	@Column
	private String birthday;
	@Column
	private String phone;
	@Column
	private int organizationId;
	@One(target = Organization.class, field = "organizationId")
	private Organization organization;
	@ManyMany(target = Role.class, relation = "SYSTEM_USER_ROLE", from = "USERID", to = "ROLEID")
	private List<Role> roles;

	public static User getInstance(ResultSet rs) throws SQLException {
		User user = new User();
		user.id = rs.getInt("ID");
		user.number = rs.getString("NUMBER");
		user.password = rs.getString("PASSWORD");
		user.name = rs.getString("NAME");
		user.gender = rs.getString("GENDER");
		user.age = rs.getInt("AGE");
		user.birthday = rs.getString("BIRTHDAY");
		user.phone = rs.getString("PHONE");
		user.organizationId = rs.getInt("ORGANIZATIONID");
		return user;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
