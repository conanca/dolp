package gs.dolp.common.jqgrid.domain;

import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 * jqGrid所需的标准格式的数据
 *
 */
public class StandardJqgridResData implements ResponseData {
	private int page;
	private int total;
	private int records;
	private List<StandardJqgridResDataRow> rows;
	private ResUserData userdata;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<StandardJqgridResDataRow> getRows() {
		return rows;
	}

	public void setRows(List<StandardJqgridResDataRow> rows) {
		this.rows = rows;
	}

	public ResUserData getUserdata() {
		return userdata;
	}

	public void setUserdata(ResUserData userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}
}
