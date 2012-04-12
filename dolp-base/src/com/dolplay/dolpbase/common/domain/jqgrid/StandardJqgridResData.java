package com.dolplay.dolpbase.common.domain.jqgrid;

import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 * 标准格式的jqGrid所需的后台响应数据
 */
public class StandardJqgridResData extends JqgridResData {
	/**
	* 记录
	*/
	private List<StandardJqgridResDataRow> rows;

	/**
	 * 获取指定字段的值的数组
	 * @param columnIndex
	 * @return
	 */
	public String[] getArrValueOfTheColumn(int columnIndex) {
		if (rows == null || rows.size() == 0) {
			return null;
		}
		int length = rows.size();
		String[] arrValue = new String[length];
		for (int i = 0; i < length; i++) {
			arrValue[i] = rows.get(i).getCell().get(columnIndex);
		}
		return arrValue;
	}

	public String toString() {
		return Json.toJson(this);
	}

	public List<StandardJqgridResDataRow> getRows() {
		return rows;
	}

	public void setRows(List<StandardJqgridResDataRow> rows) {
		this.rows = rows;
	}
}