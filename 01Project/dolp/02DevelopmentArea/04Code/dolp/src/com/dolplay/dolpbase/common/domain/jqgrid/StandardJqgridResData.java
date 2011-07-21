package com.dolplay.dolpbase.common.domain.jqgrid;

import java.util.List;

import org.nutz.json.Json;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.SystemMessage;

/**
 * @author Administrator
 * 标准格式的jqGrid所需的后台响应数据
 */
public class StandardJqgridResData implements ResponseData {
	/**
	 * 页码
	 */
	private Integer page;

	/**
	 * 页数
	 */
	private Integer total;

	/**
	 * 总记录数
	 */
	private Integer records;

	/**
	 * 记录
	 */
	private List<StandardJqgridResDataRow> rows;

	/**
	 * 用户自定义数据
	 */
	private AjaxResData userdata;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
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