package com.dolplay.dolpbase.system.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.SysPara;

@IocBean(args = { "refer:dao" })
public class SysParaService extends DolpBaseService<SysPara> {

	public SysParaService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysPara> getGridData(JqgridReqData jqReq, Boolean isSearch, SysPara sysParaSearch) {
		Cnd cnd = null;
		if (isSearch && null != sysParaSearch) {
			cnd = Cnd.where("1", "=", 1);
			String name = sysParaSearch.getName();
			if (!Strings.isEmpty(name)) {
				cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
			}
			String value = sysParaSearch.getValue();
			if (!Strings.isEmpty(value)) {
				cnd.and("VALUE", "=", value);
			}
			String description = sysParaSearch.getDescription();
			if (!Strings.isEmpty(description)) {
				cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
			}
		}
		AdvancedJqgridResData<SysPara> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysPara(String oper, String ids, SysPara sysPara) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(sysPara);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(sysPara);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}
}