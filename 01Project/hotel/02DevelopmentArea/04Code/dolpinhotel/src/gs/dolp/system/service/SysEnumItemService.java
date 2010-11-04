package gs.dolp.system.service;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class SysEnumItemService extends IdEntityForjqGridService<SysEnumItem> {

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public JqgridAdvancedData<SysEnumItem> getGridData(String page, String rows, String sidx, String sord, int sysEnumId) {
		Cnd cnd = Cnd.where("SYSENUMID", "=", sysEnumId);
		JqgridAdvancedData<SysEnumItem> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		return jq;
	}

	@Aop(value = "log")
	public void CUDSysEnumItem(String oper, String id, String text, String value, int sysEnumId) {
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap("ID IN (" + id + ")");
			clear(cnd);
		}
		if ("add".equals(oper)) {
			SysEnumItem item = new SysEnumItem();
			item.setText(text);
			item.setValue(value);
			item.setSysEnumId(sysEnumId);
			dao().insert(item);
		}
		if ("edit".equals(oper)) {
			SysEnumItem item = new SysEnumItem();
			item.setId(Integer.parseInt(id));
			item.setText(text);
			item.setValue(value);
			item.setSysEnumId(sysEnumId);
			dao().update(item);
		}
	}

	@Aop(value = "log")
	public Map<String, String> getSysEnumItem(String sysEnumName) {
		SysEnum sysEnum = dao().fetch(SysEnum.class, Cnd.where("NAME", "=", sysEnumName));
		List<SysEnumItem> items = dao().query(SysEnumItem.class, Cnd.where("SYSENUMID", "=", sysEnum.getId()), null);
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (SysEnumItem item : items) {
			options.put(item.getText(), item.getValue());
		}
		return options;
	}
}
