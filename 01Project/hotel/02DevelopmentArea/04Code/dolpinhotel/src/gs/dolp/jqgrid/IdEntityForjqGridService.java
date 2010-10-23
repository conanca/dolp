package gs.dolp.jqgrid;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

/**
 * @author Administrator
 * 该服务类用于：通过 WHERE 条件和分页信息，从数据库查询数据，并封装为JqgridData格式。
 * 使用时可以继承该类。
 * @param <T>
 */
public class IdEntityForjqGridService<T> extends IdEntityService<T> {

	public IdEntityForjqGridService(Dao dao) {
		super(dao);
	}

	/**
	 * @param cnd
	 * @param page
	 * @param rows
	 * @param sidx
	 * @param sord
	 * 通过 WHERE 条件和分页信息，从数据库查询数据，并封装为JqgridData格式。
	 * @return
	 */
	public JqgridData<T> getjqridDataByCnd(Condition cnd, String page, String rows, String sidx, String sord) {
		// 设置开始页数和每页记录数
		int pageNumber = 1;
		if (!Strings.isEmpty(page)) {
			pageNumber = Integer.parseInt(page);
		}
		int pageSize = 10;
		if (!Strings.isEmpty(rows)) {
			pageSize = Integer.parseInt(rows);
		}
		// 合计记录总数
		int count = count(cnd);
		// 计算页数
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		// 设置排序字段及正序逆序
		String sortColumn = "ID";
		if (!Strings.isEmpty(sidx)) {
			sortColumn = sidx;
		}
		String sortOrder = "asc";
		if (!Strings.isEmpty(sord)) {
			sortOrder = sord;
		}
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
		JqgridData<T> jq = new JqgridData<T>();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list);
		return jq;
	}
}
