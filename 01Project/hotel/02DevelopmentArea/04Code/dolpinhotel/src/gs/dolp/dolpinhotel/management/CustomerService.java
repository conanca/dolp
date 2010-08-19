package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.IdEntityForjqGridService;
import gs.dolp.jqgrid.JqgridData;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.log.Log;
import org.nutz.log.Logs;

public class CustomerService extends IdEntityForjqGridService<Customer> {
	private static final Log log = Logs.getLog(CustomerService.class);

	public CustomerService(Dao dao) {
		super(dao);
	}

	public JqgridData<Customer> getGridDataByRoomOccId(int roomOccId, String page, String rows, String sidx, String sord) {
		Cnd cnd = Cnd.where("ROOMOCCUPANCYID", "=", roomOccId);
		JqgridData<Customer> jq = getjqridDataByCnd(cnd, page, rows, sidx, sord);
		log.debug(jq);
		return jq;
	}
}
