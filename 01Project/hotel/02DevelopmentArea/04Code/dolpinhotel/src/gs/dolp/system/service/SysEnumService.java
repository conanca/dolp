package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.SysEnum;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class SysEnumService extends AdvJqgridIdEntityService<SysEnum> {

	public SysEnumService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysEnum> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<SysEnum> jq = getAdvancedJqgridRespData(null, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public void CUDSysEnum(String oper, String id, String name, String description) {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
		}
		if ("add".equals(oper)) {
			SysEnum sysEnum = new SysEnum();
			sysEnum.setName(name);
			sysEnum.setDescription(description);
			dao().insert(sysEnum);
		}
		if ("edit".equals(oper)) {
			SysEnum sysEnum = new SysEnum();
			sysEnum.setId(Integer.parseInt(id));
			sysEnum.setName(name);
			sysEnum.setDescription(description);
			dao().update(sysEnum);
		}
	}
}
