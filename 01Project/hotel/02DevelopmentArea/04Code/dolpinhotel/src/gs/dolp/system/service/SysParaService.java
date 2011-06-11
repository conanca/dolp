package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.system.domain.SysPara;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class SysParaService extends DolpBaseService<SysPara> {

	public SysParaService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysPara> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<SysPara> jq = getAdvancedJqgridRespData(null, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysPara(String oper, String id, String name, String value, String description) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			SysPara sysPara = new SysPara();
			sysPara.setName(name);
			sysPara.setValue(value);
			sysPara.setDescription(description);
			dao().insert(sysPara);
			respData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			SysPara sysPara = new SysPara();
			sysPara.setId(Integer.parseInt(id));
			sysPara.setName(name);
			sysPara.setValue(value);
			sysPara.setDescription(description);
			dao().update(sysPara);
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}
}