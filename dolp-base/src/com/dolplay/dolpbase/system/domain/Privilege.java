package com.dolplay.dolpbase.system.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_PRIVILEGE")
public class Privilege {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@Column
	private Long menuId;
	@One(target = Menu.class, field = "menuId")
	private Menu menu;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String methodPath;
	@ManyMany(target = Role.class, relation = "SYSTEM_ROLE_PRIVILEGE", from = "PRIVILEGEID", to = "ROLEID")
	private List<Role> roles;

	public static Privilege getInstance(ResultSet rs) throws SQLException {
		Privilege privilege = new Privilege();
		privilege.id = rs.getLong("ID");
		privilege.name = rs.getString("NAME");
		privilege.description = rs.getString("DESCRIPTION");
		privilege.menuId = rs.getLong("MENUID");
		privilege.methodPath = rs.getString("METHODPATH");
		return privilege;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
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
}