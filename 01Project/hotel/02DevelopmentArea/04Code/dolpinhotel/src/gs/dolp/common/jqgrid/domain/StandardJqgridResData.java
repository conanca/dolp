package gs.dolp.common.jqgrid.domain;

import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 * 标准格式的jqGrid所需的后台响应数据
 */
public class StandardJqgridResData implements ResponseData {
	/**
	 * 页码
	 */
	private int page;

	/**
	 * 页数
	 */
	private int total;

	/**
	 * 总记录数
	 */
	private int records;

	/**
	 * 记录
	 */
	private List<StandardJqgridResDataRow> rows;

	/**
	 * 用户自定义数据——系统信息
	 */
	private SystemMessage userdata;

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

	public SystemMessage getUserdata() {
		return userdata;
	}

	public void setUserdata(SystemMessage userdata) {
		this.userdata = userdata;
	}

	public String toString() {
		return Json.toJson(this);
	}
}
