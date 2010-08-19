package gs.dolp.jqgrid;


import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

public class IdEntityForjqGridService<T> extends IdEntityService<T> {

	public IdEntityForjqGridService(Dao dao) {
		super(dao);
	}

	public JqgridData<T> getjqridDataByCnd(Condition cnd, String page, String rows, String sidx, String sord) {
		// 设置排序字段及正序逆序
		String sortColumn = "ID";
		String sortOrder = "asc";
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
		int pageNumber = 1;
		int pageSize = 10;
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
		// 查询
		List<T> list = query(cnd, pager);
		// 合计记录总数
		int count = count();
		// 计算页数
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 封装jqGrid的json格式数据类
		JqgridData<T> jq = new JqgridData<T>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		return jq;
	}
}
