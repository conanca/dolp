package gs.dolp.common.jqgrid.domain;

/**
 * @author Administrator
 * jqGrid向后台请求的参数的集合封装成的对象
 */
public class JqgridReqData {

	/**
	 * 显示第几页
	 */
	private int page;

	/**
	 * 每页显示的记录数
	 */
	private int rows;

	/**
	 * 排序字段
	 */
	private String sidx;

	/**
	 * 升序/降序
	 */
	private String sord;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

}
