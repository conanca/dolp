package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
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
	public ResponseSysMsgData CUDSysEnum(String oper, String id, String name, String description) {
		ResponseSysMsgData reData = new ResponseSysMsgData();
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			SysEnum sysEnum = new SysEnum();
			sysEnum.setName(name);
			sysEnum.setDescription(description);
			dao().insert(sysEnum);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			SysEnum sysEnum = new SysEnum();
			sysEnum.setId(Integer.parseInt(id));
			sysEnum.setName(name);
			sysEnum.setDescription(description);
			dao().update(sysEnum);
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}
}
