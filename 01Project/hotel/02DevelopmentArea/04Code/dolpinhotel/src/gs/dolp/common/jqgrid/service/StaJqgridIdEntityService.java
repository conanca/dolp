package gs.dolp.common.jqgrid.service;

import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;
import gs.dolp.common.jqgrid.domain.SystemMessage;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.service.Service;

public class StaJqgridIdEntityService extends Service {

	public StaJqgridIdEntityService(Dao dao) {
		super(dao);
	}

	public StandardJqgridResData getAdvancedJqgridRespData(List<StandardJqgridResDataRow> rows, JqgridReqData jqReq) {
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setTotal(0);
		jq.setPage(1);
		jq.setRecords(0);
		jq.setRows(rows);
		jq.setUserdata(new SystemMessage("查询成功!", null, null));
		return jq;
	}

}
