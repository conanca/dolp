package gs.dolp.jqgrid.domain;

import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 * jqGrid所需的标准格式的数据
 *
 */
public class JqgridStandardData {
	private int page;
	private int total;
	private int records;
	private List<JqgridStandardDataRow> rows;
	private UserData userdata;

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

	public List<JqgridStandardDataRow> getRows() {
		return rows;
	}

	public void setRows(List<JqgridStandardDataRow> rows) {
		this.rows = rows;
	}

	public UserData getUserdata() {
		return userdata;
	}

	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}
}
