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
