package com.dolplay.dolpbase.system.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.nutz.dao.entity.annotation.Column;

import com.dolplay.dolpbase.common.domain.ZTreeNode;

public class PrivilegeZTreeNode extends Privilege implements ZTreeNode {
	@Column
	private boolean checked;
	@Column
	private boolean open;
	@Column
	private boolean isParent;

	public static Privilege getInstance(ResultSet rs) throws SQLException {
		PrivilegeZTreeNode privilegeZTreeNode = new PrivilegeZTreeNode();
		privilegeZTreeNode.setId(rs.getLong("ID"));
		privilegeZTreeNode.setName(rs.getString("NAME"));
		privilegeZTreeNode.setDescription(rs.getString("DESCRIPTION"));
		privilegeZTreeNode.setMenuId(rs.getLong("MENUID"));
		privilegeZTreeNode.setMethodPath(rs.getString("METHODPATH"));
		privilegeZTreeNode.checked = rs.getBoolean("CHECKED");
		privilegeZTreeNode.open = rs.getBoolean("OPEN");
		privilegeZTreeNode.isParent = rs.getBoolean("ISPARENT");
		return privilegeZTreeNode;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
}