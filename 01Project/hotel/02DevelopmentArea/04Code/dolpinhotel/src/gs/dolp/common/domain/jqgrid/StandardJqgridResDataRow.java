package gs.dolp.common.domain.jqgrid;

import java.util.ArrayList;
import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 * 标准格式的jqGrid所需的后台响应数据的rows的格式定义
 */
public class StandardJqgridResDataRow {
	/**
	 * 记录的ID
	 */
	private int id;

	/**
	 * 记录的详细信息
	 */
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

	public void addCellItem(String cellItem) {
		if (cell == null) {
			cell = new ArrayList<String>();
		}
		cell.add(cellItem);
	}

	public String toString() {
		return Json.toJson(this);
	}
}
