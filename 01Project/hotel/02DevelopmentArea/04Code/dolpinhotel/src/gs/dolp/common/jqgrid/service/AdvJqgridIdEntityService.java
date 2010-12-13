package gs.dolp.common.jqgrid.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

/**
 * @author Administrator
 * 该服务类用于：通过 WHERE 条件和分页信息，从数据库查询数据，并封装为AdvancedJqgridResData格式。
 * 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public class AdvJqgridIdEntityService<T> extends IdEntityService<T> {

	public AdvJqgridIdEntityService(Dao dao) {
		super(dao);
	}

	/**
	 * 通过 WHERE 条件和分页信息，从数据库查询数据，并封装为AdvancedJqgridResData格式。
	 * @param cnd
	 * @param jqReq
	 * @return
	 */
	public AdvancedJqgridResData<T> getAdvancedJqgridRespData(Condition cnd, JqgridReqData jqReq) {
		// 设置页码
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 设置每页记录数
		int rows = jqReq.getRows();
		int pageSize = rows == 0 ? 10 : rows;
		// 设置排序字段
		String sidx = jqReq.getSidx();
		String sortColumn = Strings.isEmpty(sidx) ? "ID" : sidx;
		// 设置正序逆序
		String sord = jqReq.getSord();
		String sortOrder = Strings.isEmpty(sord) ? "asc" : sord;
		// 合计记录总数
		int count = count(cnd);
		// 计算页数
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		if (null == cnd) {
			if ("asc".equals(sortOrder)) {
				cnd = Cnd.orderBy().asc(sortColumn);
			} else {
				cnd = Cnd.orderBy().desc(sortColumn);
			}
		} else {
			if ("asc".equals(sortOrder)) {
				cnd = ((Cnd) cnd).asc(sortColumn);
			} else {
				cnd = ((Cnd) cnd).desc(sortColumn);
			}
		}
		// 建立分页信息
		Pager pager = dao().createPager(pageNumber, pageSize);
		// 查询
		List<T> list = query(cnd, pager);
		// 开始封装jqGrid的json格式数据类
		AdvancedJqgridResData<T> jq = new AdvancedJqgridResData<T>();
		jq.setTotal(totalPage);
		jq.setPage(pageNumber);
		jq.setRecords(count);
		jq.setRows(list);
		//jq.setUserdata(new SystemMessage("查询成功!", null, null));
		return jq;
	}
}
