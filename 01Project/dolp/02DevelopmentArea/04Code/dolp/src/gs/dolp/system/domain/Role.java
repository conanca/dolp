package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_ROLE")
public class Role {
	@Column
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String description;
	//	@Many(target = Privilege.class, field = "roleId")
	//	private List<Privilege> privileges;
	@ManyMany(target = User.class, relation = "SYSTEM_USER_ROLE", from = "ROLEID", to = "USERID")
	private List<User> users;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//	public List<Privilege> getPrivileges() {
	//		return privileges;
	//	}
	//
	//	public void setPrivileges(List<Privilege> privileges) {
	//		this.privileges = privileges;
	//	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
