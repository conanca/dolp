package gs.dolp.common.jqgrid.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

/**
 * @author Administrator
 * 该服务类用于：通过 WHERE 条件和分页信息，从数据库查询数据，并封装为AdvancedJqgridResData格式。
 * 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public class JqgridService<T> extends IdEntityService<T> {

	public JqgridService(Dao dao) {
		super(dao);
	}

	/**
	 * 通过 查询条件cnd和排序分页信息jqReq，从数据库查询数据，并封装为AdvancedJqgridResData格式。
	 * @param cnd
	 * @param jqReq
	 * @return
	 */
	public AdvancedJqgridResData<T> getAdvancedJqgridRespData(Condition cnd, JqgridReqData jqReq) {
		// 设置页码
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 设置每页记录数
		int jqReqRows = jqReq.getRows();
		int pageSize = jqReqRows == 0 ? 10 : jqReqRows;
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
		// 查询,并设置rows
		List<T> rows = query(cnd, pager);
		// 开始封装jqGrid的json格式数据类
		AdvancedJqgridResData<T> jq = new AdvancedJqgridResData<T>();
		jq.setTotal(totalPage);
		jq.setPage(pageNumber);
		jq.setRecords(count);
		jq.setRows(rows);
		//jq.setSystemMessage("查询成功!", null, null);
		return jq;
	}

	/**
	 * 通过 Class、自定义Sql语句、查询条件cnd和排序分页信息jqReq，从数据库查询数据，并封装为AdvancedJqgridResData格式。
	 * 注：目前不支持分页和排序功能
	 * @param tClass
	 * @param sql
	 * @param cnd
	 * @param jqReq
	 * @return
	 */
	public AdvancedJqgridResData<T> getAdvancedJqgridRespData(Class<T> tClass, Sql sql, Condition cnd,
			JqgridReqData jqReq) {
		// 设置rows
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(tClass));
		sql.setCondition(cnd);
		// TODO 如果Sql支持setPager就好了。
		dao().execute(sql);
		List<T> rows = sql.getList(tClass);
		// 设置总记录数
		int countRecords = rows.size();
		// 设置开始页数
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 开始封装jqGrid的json格式数据类
		AdvancedJqgridResData<T> jq = new AdvancedJqgridResData<T>();
		jq.setTotal(0);
		jq.setPage(pageNumber);
		jq.setRecords(countRecords);
		jq.setRows(rows);
		return jq;
	}

	/**
	 * 通过自定义Sql语句、查询条件cnd和排序分页信息jqReq，从数据库查询数据，将各条记录和分页信息封装为StandardJqgridResData格式。
	 * @param sql
	 * @param cnd
	 * @param jqReq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StandardJqgridResData getStandardJqgridResData(Sql sql, Condition cnd, JqgridReqData jqReq) {
		// 设置rows
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
				int columnCount = rs.getMetaData().getColumnCount();
				int i = 1;
				while (rs.next()) {
					StandardJqgridResDataRow row = new StandardJqgridResDataRow();
					row.setId(i);
					for (int j = 1; j <= columnCount; j++) {
						row.addCellItem(rs.getString(j));
					}
					rows.add(row);
					i++;
				}
				return rows;
			}
		});
		dao().execute(sql);
		List<StandardJqgridResDataRow> rows = (List<StandardJqgridResDataRow>) sql.getResult();
		// 设置开始页数
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 设置每页记录数
		int pageRows = jqReq.getRows();
		int pageSize = pageRows == 0 ? 10 : pageRows;
		// 设置总记录数
		int recordsCount = rows.size();
		// 设置总页数
		int totalPage = recordsCount / pageSize + (recordsCount % pageSize == 0 ? 0 : 1);
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setTotal(totalPage);
		jq.setPage(pageNumber);
		jq.setRecords(recordsCount);
		jq.setRows(rows);
		//jq.setSystemMessage("查询成功!", null, null);
		return jq;
	}
}
