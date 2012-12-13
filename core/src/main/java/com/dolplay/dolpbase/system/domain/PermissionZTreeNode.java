package com.dolplay.dolpbase.system.domain;

import org.nutz.dao.entity.annotation.Column;

import com.dolplay.dolpbase.common.domain.ZTreeNode;

public class PermissionZTreeNode extends Permission implements ZTreeNode {

	@Column
	private boolean checked;
	@Column
	private boolean open;
	@Column
	private boolean isParent;

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