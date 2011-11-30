package com.dolplay.dolpbase.system.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.nutz.dao.entity.annotation.Column;

import com.dolplay.dolpbase.common.domain.ZTreeNode;

public class MenuZTreeNode extends Menu implements ZTreeNode {
	@Column
	private boolean checked;
	@Column
	private boolean open;
	@Column
	private boolean isParent;

	public static MenuZTreeNode getInstance(ResultSet rs) throws SQLException {
		MenuZTreeNode menuEntity = new MenuZTreeNode();
		menuEntity.setId(rs.getLong("ID"));
		menuEntity.setName(rs.getString("NAME"));
		//menuEntity.setUrl(rs.getString("URL"));
		menuEntity.setDescription(rs.getString("DESCRIPTION"));
		menuEntity.setLft(rs.getLong("LFT"));
		menuEntity.setRgt(rs.getLong("RGT"));
		menuEntity.checked = rs.getBoolean("CHECKED");
		menuEntity.open = rs.getBoolean("OPEN");
		menuEntity.isParent = rs.getBoolean("ISPARENT");
		return menuEntity;
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