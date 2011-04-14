package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.Privilege;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class PrivilegeService extends JqgridService<Privilege> {

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
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			reData.setSystemMessage(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			Privilege privilege = new Privilege();
			privilege.setName(name);
			privilege.setDescription(description);
			privilege.setMenuId(menuId);
			privilege.setMethodPath(methodPath);
			dao().insert(privilege);
			reData.setSystemMessage(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Privilege privilege = new Privilege();
			privilege.setId(Integer.parseInt(id));
			privilege.setName(name);
			privilege.setDescription(description);
			privilege.setMenuId(menuId);
			privilege.setMethodPath(methodPath);
			dao().update(privilege);
			reData.setSystemMessage(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}
}
