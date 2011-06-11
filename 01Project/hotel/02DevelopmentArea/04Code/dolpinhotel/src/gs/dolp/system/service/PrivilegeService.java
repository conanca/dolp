package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.system.domain.Privilege;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

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
	public AjaxResData CUDPrivilege(String oper, String id, String name, String description, int menuId,
			String methodPath) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			Privilege privilege = new Privilege();
			privilege.setName(name);
			privilege.setDescription(description);
			privilege.setMenuId(menuId);
			privilege.setMethodPath(methodPath);
			dao().insert(privilege);
			respData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			Privilege privilege = new Privilege();
			privilege.setId(Integer.parseInt(id));
			privilege.setName(name);
			privilege.setDescription(description);
			privilege.setMenuId(menuId);
			privilege.setMethodPath(methodPath);
			dao().update(privilege);
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}
}
