package com.dolplay.dolpinhotel.management;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;

@IocBean(args = { "refer:dao" })
public class CustomerService extends DolpBaseService<Customer> {

	public CustomerService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Customer> getGridDataByRoomOccId(int roomOccId, JqgridReqData jqReq) {
		Cnd cnd = Cnd.where("ROOMOCCUPANCYID", "=", roomOccId);
		AdvancedJqgridResData<Customer> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}
}
