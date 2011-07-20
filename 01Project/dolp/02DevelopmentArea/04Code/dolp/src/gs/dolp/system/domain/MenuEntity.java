package gs.dolp.system.domain;

import org.nutz.dao.entity.annotation.Column;

public class MenuEntity extends Menu implements TreeNode {
	@Column
	private Integer level;
	@Column
	private boolean isLeaf;
	@Column
	private boolean expanded;
	@Column
	private boolean checked;
	@Column
	private boolean open;
	@Column
	private boolean isParent;

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