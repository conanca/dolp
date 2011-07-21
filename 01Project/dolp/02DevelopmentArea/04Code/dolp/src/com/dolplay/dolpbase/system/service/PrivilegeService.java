package com.dolplay.dolpbase.system.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.system.domain.Privilege;

public class PrivilegeService extends DolpBaseService<Privilege> {

	public PrivilegeService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Privilege> getGridData(int menuId, JqgridReqData jqReq) {
		Condition cnd = Cnd.where("MENUID", "=", menuId);
		AdvancedJqgridResData<Privilege> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDPrivilege(String oper, String ids, Privilege privilege) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(privilege);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(privilege);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}
}