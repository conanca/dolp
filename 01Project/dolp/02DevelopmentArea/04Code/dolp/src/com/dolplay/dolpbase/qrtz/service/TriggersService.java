package com.dolplay.dolpbase.qrtz.service;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;

import com.dolplay.dolpbase.qrtz.domain.Triggers;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

@IocBean(args = { "refer:dao" })
public class TriggersService extends DolpBaseService<Triggers> {

	public TriggersService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Triggers> getGridData(JqgridReqData jqReq, Boolean isSearch, Triggers triggersSearch) {
		Cnd cnd = null;
//		if (isSearch && null != triggersSearch) {
//			cnd = Cnd.where("1", "=", 1);
//			Long id = triggersSearch.getId();
//			if (null != id) {
//				cnd.and("ID", "=", id);
//			}
//		}
		AdvancedJqgridResData<Triggers> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}
}