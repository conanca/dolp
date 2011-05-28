package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.SysPara;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class SysParaService extends JqgridService<SysPara> {

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
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			reData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			SysPara sysPara = new SysPara();
			sysPara.setName(name);
			sysPara.setValue(value);
			sysPara.setDescription(description);
			dao().insert(sysPara);
			reData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			SysPara sysPara = new SysPara();
			sysPara.setId(Integer.parseInt(id));
			sysPara.setName(name);
			sysPara.setValue(value);
			sysPara.setDescription(description);
			dao().update(sysPara);
			reData.setSystemMessage("修改成功!", null, null);
		}
		return reData;
	}

	@Aop(value = "log")
	public static String getSysParaValue(String name, Dao dao) {
		SysPara sysPara = dao.fetch(SysPara.class, Cnd.where("NAME", "=", name));
		if (sysPara == null) {
			StringBuilder message = new StringBuilder("系统参数:\"");
			message.append(name);
			message.append("\"不存在!");
			throw new RuntimeException(message.toString());
		}
		return sysPara.getValue();
	}
}