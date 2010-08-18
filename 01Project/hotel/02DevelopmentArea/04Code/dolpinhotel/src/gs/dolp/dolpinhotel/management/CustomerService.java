package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.JqgridData;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

public class CustomerService extends IdEntityService<Customer> {
	private static final Log log = Logs.getLog(CustomerService.class);

	public CustomerService(Dao dao) {
		super(dao);
	}

	public JqgridData<Customer> getGridDataByRoomOccId(int roomOccId, String page, String rows, String sidx, String sord) {
		int pageNumber = 1;
		int pageSize = 10;
		String sortColumn = "ID";
		String sortOrder = "ASC";
		if (!Strings.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		if (!Strings.isEmpty(rows)) {
			pageSize = Integer.parseInt(rows);
		}
		if (!Strings.isEmpty(sidx)) {
			sortColumn = sidx;
		}
		if (!Strings.isEmpty(sord)) {
			sortOrder = sord;
		}
		Pager pager = dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("ROOMOCCUPANCYID=" + roomOccId + " ORDER BY " + sortColumn + " " + sortOrder);
		// 查询
		List<Customer> list = query(cnd, pager);
		// 合计记录总数
		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 封装jqGrid的json格式数据类
		JqgridData<Customer> jq = new JqgridData<Customer>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		log.debug(jq.toString());
		return jq;
	}
}
