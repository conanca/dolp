package gs.dolp.common.jqgrid.service;

import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.service.Service;

/**
 * @author Administrator
 * 该服务类用于：将各条记录和分页信息封装为StandardJqgridResData格式。
 * 使用时可以继承该类。
 */
public class StaJqgridService extends Service {

	public StaJqgridService(Dao dao) {
		super(dao);
	}

	/**
	 * 将各条记录和分页信息封装为StandardJqgridResData格式。
	 * @param jqReq
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public StandardJqgridResData getGridData(JqgridReqData jqReq, List<StandardJqgridResDataRow> rows) throws Exception {
		// 设置开始页数
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 设置每页记录数
		int pageRows = jqReq.getRows();
		int pageSize = pageRows == 0 ? 10 : pageRows;
		int recordsCount = rows.size();
		int totalPage = recordsCount / pageSize + (recordsCount % pageSize == 0 ? 0 : 1);
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setTotal(totalPage);
		jq.setPage(pageNumber);
		jq.setRecords(recordsCount);
		jq.setRows(rows);
		//jq.setUserdata(new SystemMessage("查询成功!", null, null));
		return jq;
	}

}
