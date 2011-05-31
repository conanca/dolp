package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class SysEnumItemService extends JqgridService<SysEnumItem> {

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<SysEnumItem> getGridData(JqgridReqData jqReq, int sysEnumId) {
		Cnd cnd = Cnd.where("SYSENUMID", "=", sysEnumId);
		AdvancedJqgridResData<SysEnumItem> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDSysEnumItem(String oper, String id, String text, String value, int sysEnumId) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			clear(cnd);
			respData.setSystemMessage("删除成功!", null, null);
		}
		if ("add".equals(oper)) {
			SysEnumItem item = new SysEnumItem();
			item.setText(text);
			item.setValue(value);
			item.setSysEnumId(sysEnumId);
			dao().insert(item);
			respData.setSystemMessage("添加成功!", null, null);
		}
		if ("edit".equals(oper)) {
			SysEnumItem item = new SysEnumItem();
			item.setId(Integer.parseInt(id));
			item.setText(text);
			item.setValue(value);
			item.setSysEnumId(sysEnumId);
			dao().update(item);
			respData.setSystemMessage("修改成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getSysEnumItemMap(String sysEnumName) {
		AjaxResData respData = new AjaxResData();
		SysEnum sysEnum = dao().fetch(SysEnum.class, Cnd.where("NAME", "=", sysEnumName));
		List<SysEnumItem> items = dao().query(SysEnumItem.class, Cnd.where("SYSENUMID", "=", sysEnum.getId()), null);
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (SysEnumItem item : items) {
			options.put(item.getText(), item.getValue());
		}
		respData.setReturnData(options);
		return respData;
	}
}
