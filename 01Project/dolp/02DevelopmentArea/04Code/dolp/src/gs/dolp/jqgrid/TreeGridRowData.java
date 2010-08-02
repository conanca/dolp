package gs.dolp.jqgrid;

public class TreeGridRowData {
	private int level_field;
	private int parent_id_field;
	private boolean leaf_field;
	private boolean expanded_field;

	public int getLevel_field() {
		return level_field;
	}

	public void setLevel_field(int levelField) {
		level_field = levelField;
	}

	public int getParent_id_field() {
		return parent_id_field;
	}

	public void setParent_id_field(int parentIdField) {
		parent_id_field = parentIdField;
	}

	public boolean isLeaf_field() {
		return leaf_field;
	}

	public void setLeaf_field(boolean leafField) {
		leaf_field = leafField;
	}

	public boolean isExpanded_field() {
		return expanded_field;
	}

	public void setExpanded_field(boolean expandedField) {
		expanded_field = expandedField;
	}

}
