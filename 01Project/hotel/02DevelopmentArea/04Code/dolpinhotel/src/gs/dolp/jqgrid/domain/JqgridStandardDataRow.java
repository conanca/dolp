package gs.dolp.jqgrid.domain;

import java.util.List;

/**
 * @author Administrator
 * jqGrid所需的标准格式的数据的Row数据
 */
public class JqgridStandardDataRow {
	private int id;
	private List<String> cell;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getCell() {
		return cell;
	}

	public void setCell(List<String> cell) {
		this.cell = cell;
	}

}
