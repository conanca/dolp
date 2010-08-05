package gs.dolp.jqgrid;

import java.util.List;

import org.nutz.json.Json;

public class JqgridStandardData {
	private int page;
	private int total;
	private int records;
	private List<JqgridDataRow> rows;

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

	public List<JqgridDataRow> getRows() {
		return rows;
	}

	public void setRows(List<JqgridDataRow> rows) {
		this.rows = rows;
	}

	public String toString() {
		return Json.toJson(this);
	}
}
