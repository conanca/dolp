package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_ROLE")
public class Role {
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private int isOrgaRela;
	@Column
	private int organizationId;
	@One(target = Organization.class, field = "organizationId")
	private Organization organization;
	@ManyMany(target = User.class, relation = "SYSTEM_USER_ROLE", from = "ROLEID", to = "USERID")
	private List<User> users;
	@ManyMany(target = Menu.class, relation = "SYSTEM_ROLE_MENU", from = "ROLEID", to = "MENUID")
	private List<Menu> menus;
	@ManyMany(target = Menu.class, relation = "SYSTEM_ROLE_PRIVILEGE", from = "ROLEID", to = "PRIVILEGEID")
	private List<Privilege> privileges;

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

	public int getIsOrgaRela() {
		return isOrgaRela;
	}

	public void setIsOrgaRela(int isOrgaRela) {
		this.isOrgaRela = isOrgaRela;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

}
