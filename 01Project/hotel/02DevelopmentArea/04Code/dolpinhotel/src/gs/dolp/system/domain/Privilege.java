package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.Json;

@Table("SYSTEM_PRIVILEGE")
public class Privilege {
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private int menuId;
	@One(target = Menu.class, field = "menuId")
	private Menu menu;
	@Column
	private String methodPath;
	@ManyMany(target = Role.class, relation = "SYSTEM_ROLE_PRIVILEGE", from = "PRIVILEGEID", to = "ROLEID")
	private List<Role> roles;

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

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMethodPath() {
		return methodPath;
	}

	public void setMethodPath(String methodPath) {
		this.methodPath = methodPath;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String toString() {
		return Json.toJson(this);
	}
}