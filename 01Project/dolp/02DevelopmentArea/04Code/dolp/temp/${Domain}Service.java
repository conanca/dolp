package ${package}.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.DolpBaseService;
import ${package}.domain.${Domain};

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class ${Domain}Service extends DolpBaseService<${Domain}> {

	public ${Domain}Service(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<${Domain}> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<${Domain}> jq = getAdvancedJqgridRespData(null, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUD${Domain}(String oper, String ids, ${Domain} ${Domain?uncap_first}) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			dao().insert(${Domain?uncap_first});
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(${Domain?uncap_first});
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}
}