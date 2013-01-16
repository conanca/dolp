package com.dolplay.dolpbase.common.domain.jqgrid;

import java.lang.reflect.Field;
import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 *	扩展了格式的jqGrid所需的后台响应数据，jqgrid需设置jsonReader:{ repeatitems: false }
 *	rows中为自定义的T型实体数据的集合
 * @param <T>
 */
public class AdvancedJqgridResData<T> extends JqgridResData {

	/**
	 * 记录
	 */
	private List<T> rows;

	/**
	 * 获取指定字段的值的数组
	 * @param column
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public String[] getArrValueOfTheColumn(String column) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		if (rows == null || rows.size() == 0) {
			return null;
		}
		int length = rows.size();
		String[] arrValue = new String[length];
		// 获取Field对象
		T obj = rows.get(0);
		Field filed = obj.getClass().getDeclaredField(column);
		filed.setAccessible(true);
		// 迭代各行，取指定Field的值，并放入arrValue中
		for (int i = 0; i < length; i++) {
			T row = rows.get(i);
			String fieldValueStr = filed.get(row).toString();
			arrValue[i] = fieldValueStr;
		}
		return arrValue;
	}

	public String toString() {
		return Json.toJson(this);
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}