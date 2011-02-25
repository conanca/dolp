package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.SysEnum;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

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
	public AjaxResData CUDSysEnum(String oper, String id, String name, String description) {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(id).append(")").toString());
			final List<SysEnum> sysEnums = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (SysEnum sysEnum : sysEnums) {
						dao().clearLinks(sysEnum, "items");
					}
					clear(cnd);
				}
			});
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