package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class CustomerService extends AdvJqgridIdEntityService<Customer> {

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
