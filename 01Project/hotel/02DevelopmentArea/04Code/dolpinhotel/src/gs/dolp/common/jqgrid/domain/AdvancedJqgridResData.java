package gs.dolp.common.jqgrid.domain;

import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 *	jqGrid所需的扩展格式的数据，需设置jsonReader:{ repeatitems: false }
 *	rows中为自定义的实体数据
 * @param <T>
 */
public class AdvancedJqgridResData<T> implements ResponseData {
	private int page;
	private int total;
	private int records;
	private List<T> rows;
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

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
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
