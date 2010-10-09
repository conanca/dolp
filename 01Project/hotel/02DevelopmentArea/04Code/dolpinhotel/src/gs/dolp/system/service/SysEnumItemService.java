package gs.dolp.system.service;

import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;
import gs.dolp.system.domain.SysEnumItem;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class SysEnumItemService extends IdEntityForjqGridService<SysEnumItem> {
	private static final Log log = Logs.getLog(SysEnumItemService.class);

	public SysEnumItemService(Dao dao) {
		super(dao);
	}

	public JqgridData<SysEnumItem> getGridData(String page, String rows, String sidx, String sord, int SysEnumId) {
		Cnd cnd = Cnd.where("SYSENUMID", "=", SysEnumId);
		JqgridData<SysEnumItem> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}

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
			dao().update(item);
		}
	}
}
