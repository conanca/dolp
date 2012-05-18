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
import com.dolplay.dolpbase.system.domain.SysEnumItem;

@IocBean(args = { "refer:dao" })
public class SysEnumItemService extends DolpBaseService<SysEnumItem> {

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysEnumItem> getGridData(JqgridReqData jqReq, Boolean isSearch,
			SysEnumItem sysEnumItemSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != sysEnumItemSearch) {
			Long sysEnumId = sysEnumItemSearch.getSysEnumId();
			if (null != sysEnumItemSearch) {
				cnd = Cnd.where("SYSENUMID", "=", sysEnumId);
			}
			if (null != isSearch && isSearch) {
				String text = sysEnumItemSearch.getText();
				if (!Strings.isEmpty(text)) {
					cnd.and("TEXT", "LIKE", StringUtils.quote(text, '%'));
				}
				String value = sysEnumItemSearch.getValue();
				if (!Strings.isEmpty(value)) {
					cnd.and("VALUE", "LIKE", StringUtils.quote(value, '%'));
				}
			}
		}

		AdvancedJqgridResData<SysEnumItem> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysEnumItem(String oper, String ids, SysEnumItem sysEnumItem) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(sysEnumItem);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(sysEnumItem);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}
}