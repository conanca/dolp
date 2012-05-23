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
import com.dolplay.dolpbase.system.domain.Privilege;

@IocBean(args = { "refer:dao" })
public class PrivilegeService extends DolpBaseService<Privilege> {

	public PrivilegeService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Privilege> getGridData(JqgridReqData jqReq, Boolean isSearch, Privilege privilegeSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != privilegeSearch) {
			Long menuId = privilegeSearch.getMenuId();
			if (null != menuId) {
				cnd = Cnd.where("MENUID", "=", menuId);
			}
			if (null != isSearch && isSearch) {
				String name = privilegeSearch.getName();
				if (!Strings.isEmpty(name)) {
					cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
				}
				String description = privilegeSearch.getDescription();
				if (!Strings.isEmpty(description)) {
					cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
				}
				String methodPath = privilegeSearch.getMethodPath();
				if (!Strings.isEmpty(methodPath)) {
					cnd.and("METHODPATH", "LIKE", StringUtils.quote(methodPath, '%'));
				}
			}
		}
		AdvancedJqgridResData<Privilege> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDPrivilege(String oper, String ids, Privilege privilege) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(privilege);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(privilege);
			respData.setInfo("修改成功!");
		} else {
			respData.setInfo("未知操作!");
		}
		return respData;
	}
}