package gs.dolp.common.domain.jqgrid;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.SystemMessage;

import java.lang.reflect.Field;
import java.util.List;

import org.nutz.json.Json;

/**
 * @author Administrator
 *	扩展了格式的jqGrid所需的response数据，需设置jsonReader:{ repeatitems: false }
 *	rows中为自定义的T型实体数据的集合
 * @param <T>
 */
public class AdvancedJqgridResData<T> implements ResponseData {
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
	private List<T> rows;

	/**
	 * 用户自定义数据
	 */
	private AjaxResData userdata;

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

	public AjaxResData getUserdata() {
		return userdata;
	}

	public void setUserdata(AjaxResData userdata) {
		this.userdata = userdata;
	}

	/**
	 * 用于简化 创建userdata对象 的帮助函数
	 * @param returnData
	 * @param infoMessages
	 * @param warnMessages
	 * @param errorMessages
	 */
	public void setUserdata(Object returnData, String infoMessages, String warnMessages, String errorMessages) {
		AjaxResData userdata = new AjaxResData(returnData, new SystemMessage(infoMessages, warnMessages, errorMessages));
		setUserdata(userdata);
	}

	/**
	 * 用于简化 创建userdata对象的returnData 的帮助函数
	 * @param returnData
	 */
	public void setReturnData(Object returnData) {
		if (userdata != null) {
			userdata.setReturnData(returnData);
		} else {
			setUserdata(new AjaxResData(returnData, null));
		}
	}

	/**
	 * 用于简化 创建userdata对象的systemMessage 的帮助函数
	 * @param infoMessage
	 * @param warnMessage
	 * @param errorMessage
	 */
	public void setSystemMessage(String infoMessage, String warnMessage, String errorMessage) {
		SystemMessage systemMessage = new SystemMessage(infoMessage, warnMessage, errorMessage);
		if (userdata != null) {
			userdata.setSystemMessage(systemMessage);
		} else {
			setUserdata(new AjaxResData(null, systemMessage));
		}
	}

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
}
