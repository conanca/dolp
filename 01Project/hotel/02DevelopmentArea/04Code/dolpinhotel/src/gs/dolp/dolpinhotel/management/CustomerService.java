package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.jqgrid.service.IdEntityForjqGridService;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class CustomerService extends IdEntityForjqGridService<Customer> {

	public CustomerService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public JqgridAdvancedData<Customer> getGridDataByRoomOccId(int roomOccId, String page, String rows, String sidx,
			String sord) {
		Cnd cnd = Cnd.where("ROOMOCCUPANCYID", "=", roomOccId);
		JqgridAdvancedData<Customer> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		return jq;
	}
}
