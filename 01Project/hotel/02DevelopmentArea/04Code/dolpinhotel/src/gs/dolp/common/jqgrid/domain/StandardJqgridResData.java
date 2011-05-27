package gs.dolp.common.jqgrid.domain;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.SystemMessage;

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

	public List<StandardJqgridResDataRow> getRows() {
		return rows;
	}

	public void setRows(List<StandardJqgridResDataRow> rows) {
		this.rows = rows;
	}

	public AjaxResData getUserdata() {
		return userdata;
	}

	public void setUserdata(AjaxResData userdata) {
		this.userdata = userdata;
	}

	/**
	 * 用于简化创建AjaxResData类的帮助函数
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
}
