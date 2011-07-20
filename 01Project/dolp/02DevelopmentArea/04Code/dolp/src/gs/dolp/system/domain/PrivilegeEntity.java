package gs.dolp.system.domain;

import org.nutz.dao.entity.annotation.Column;

public class PrivilegeEntity extends Privilege implements TreeNode {
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