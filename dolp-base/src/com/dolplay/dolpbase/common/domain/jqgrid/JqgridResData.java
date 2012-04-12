package com.dolplay.dolpbase.common.domain.jqgrid;

import org.nutz.dao.pager.Pager;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.SystemMessage;

/**
 * jqGrid所需的后台响应数据的抽象类,不允许直接使用,需继承该类并添加rows属性
 * @author Administrator
 *
 */
public abstract class JqgridResData implements ResponseData {
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
	 * 用户自定义数据
	 */
	private AjaxResData userdata;

	/**
	 * 初始化分页信息
	 * @param pager
	 */
	public void initPager(Pager pager) {
		total = (pager.getPageCount());
		page = (pager.getPageNumber());
		records = (pager.getRecordCount());
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

	public AjaxResData getUserdata() {
		return userdata;
	}

	public void setUserdata(AjaxResData userdata) {
		this.userdata = userdata;
	}
}
