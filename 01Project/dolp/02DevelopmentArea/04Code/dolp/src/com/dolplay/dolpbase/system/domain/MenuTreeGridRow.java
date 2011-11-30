package com.dolplay.dolpbase.system.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.nutz.dao.entity.annotation.Column;

import com.dolplay.dolpbase.common.domain.TreeGridRow;

public class MenuTreeGridRow extends Menu implements TreeGridRow {
	@Column
	private Integer level;
	@Column
	private boolean isLeaf;
	@Column
	private boolean expanded;

	public static MenuTreeGridRow getInstance(ResultSet rs) throws SQLException {
		MenuTreeGridRow menuEntity = new MenuTreeGridRow();
		menuEntity.setId(rs.getLong("ID"));
		menuEntity.setName(rs.getString("NAME"));
		menuEntity.setUrl(rs.getString("URL"));
		menuEntity.setDescription(rs.getString("DESCRIPTION"));
		menuEntity.setLft(rs.getLong("LFT"));
		menuEntity.setRgt(rs.getLong("RGT"));
		menuEntity.level = rs.getInt("LEVEL");
		menuEntity.isLeaf = rs.getBoolean("ISLEAF");
		menuEntity.expanded = rs.getBoolean("EXPANDED");
		return menuEntity;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
}