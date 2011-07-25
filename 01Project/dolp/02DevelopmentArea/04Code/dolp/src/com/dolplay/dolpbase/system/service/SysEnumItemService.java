package com.dolplay.dolpbase.system.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.system.domain.SysEnum;
import com.dolplay.dolpbase.system.domain.SysEnumItem;

@IocBean(args = { "refer:dao" })
public class SysEnumItemService extends DolpBaseService<SysEnumItem> {

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysEnumItem> getGridData(JqgridReqData jqReq, int sysEnumId) {
		Cnd cnd = Cnd.where("SYSENUMID", "=", sysEnumId);
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

	@Aop(value = "log")
	public AjaxResData getSysEnumItemMap(String sysEnumName) {
		AjaxResData respData = new AjaxResData();
		SysEnum sysEnum = dao().fetch(SysEnum.class, Cnd.where("NAME", "=", sysEnumName));
		List<SysEnumItem> items = dao().query(SysEnumItem.class, Cnd.where("SYSENUMID", "=", sysEnum.getId()), null);
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (SysEnumItem item : items) {
			options.put(item.getText(), item.getValue());
		}
		respData.setReturnData(options);
		return respData;
	}
}