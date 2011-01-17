package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;

import org.nutz.dao.Dao;
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
}
