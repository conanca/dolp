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
import com.dolplay.dolpbase.system.domain.Permission;

@IocBean(args = { "refer:dao" })
public class PermissionService extends DolpBaseService<Permission> {

	public PermissionService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Permission> getGridData(JqgridReqData jqReq, Boolean isSearch,
			Permission permissionSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != permissionSearch) {
			if (null != isSearch && isSearch) {
				String name = permissionSearch.getName();
				if (!Strings.isEmpty(name)) {
					cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
				}
				String description = permissionSearch.getDescription();
				if (!Strings.isEmpty(description)) {
					cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
				}
			}
		}
		AdvancedJqgridResData<Permission> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDPrivilege(String oper, String ids, Permission permission) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(permission);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(permission);
			respData.setInfo("修改成功!");
		} else {
			respData.setInfo("未知操作!");
		}
		return respData;
	}
}