package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.aop.Aop;
import org.nutz.service.Service;

public class SystemService extends Service {

	public SystemService(Dao dao) {
		super(dao);
	}

	public AjaxResData getSystemName() {
		AjaxResData reData = new AjaxResData();
		reData.setReturnData(SysParaService.getSysParaValue("SystemName", dao()));
		return reData;
	}

	@Aop(value = "log")
	public AjaxResData initDatabase() {
		AjaxResData reData = new AjaxResData();
		Tables.define(dao(), Tables.loadFrom("tables.dod"));
		FileSqlManager fm = new FileSqlManager("initData.sql");
		dao().execute(fm.createCombo(fm.keys()));
		reData.setUserdata(new SystemMessage("初始化数据库完成！", null, null));
		return reData;
	}

}
