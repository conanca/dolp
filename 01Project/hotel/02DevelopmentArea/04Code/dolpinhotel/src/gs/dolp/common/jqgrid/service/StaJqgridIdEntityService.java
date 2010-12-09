package gs.dolp.common.jqgrid.service;

import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.nutz.service.Service;

public abstract class StaJqgridIdEntityService extends Service {

	public StaJqgridIdEntityService(Dao dao) {
		super(dao);
	}

	public StandardJqgridResData getGridData(JqgridReqData jqReq) throws Exception {
		// 设置开始页数
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 设置每页记录数
		int pageRows = jqReq.getRows();
		int pageSize = pageRows == 0 ? 10 : pageRows;
		// 设置排序字段
		String sidx = jqReq.getSidx();
		String sortColumn = Strings.isEmpty(sidx) ? "ID" : sidx;
		// 设置正序逆序
		String sord = jqReq.getSord();
		String sortOrder = Strings.isEmpty(sord) ? "asc" : sord;

		List<StandardJqgridResDataRow> rows = this.getRows(pageNumber, pageSize, sortColumn, sortOrder);
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

	/**
	 * 子类需实现该方法，用以获取grid的各条记录
	 * @param pageNumber
	 * @param pageSize
	 * @param sortColumn
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	public abstract List<StandardJqgridResDataRow> getRows(int pageNumber, int pageSize, String sortColumn,
			String sortOrder) throws Exception;

}
