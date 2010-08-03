package gs.dolp.system.service;

import gs.dolp.jqgrid.JqgridData1;
import gs.dolp.jqgrid.JqgridDataRow;
import gs.dolp.system.domain.Menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.service.IdEntityService;

public class MenuService extends IdEntityService<Menu> {
	private static final Log log = Logs.getLog(MenuService.class);

	public MenuService(Dao dao) {
		super(dao);
	}

	public JqgridData1 getGridData(String page, String rows, String sidx, String sord, int nodeid, int n_level) {
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
		Condition cnd = Cnd.wrap("1=1 ORDER BY " + sortColumn + " " + sortOrder);
		List<Menu> list = query(cnd, pager);

		int count = count();
		int totalPage = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		JqgridData1 jq = new JqgridData1();
		jq.setPage(pageNumber);
		jq.setTotal(totalPage);
		jq.setRows(list2Rows(list, nodeid, n_level));
		log.debug(jq.toString());
		return jq;
	}

	public List<JqgridDataRow> list2Rows(List<Menu> list, int nodeid, int n_level) {

		List<JqgridDataRow> rows = new ArrayList<JqgridDataRow>();

		Sql sql = Sqls
				.create("SELECT t1.ID FROM SYSTEM_MENU AS t1 LEFT JOIN SYSTEM_MENU as t2 ON t1.ID = t2.PARENTID WHERE t2.ID IS NULL");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Integer> list = new LinkedList<Integer>();
				while (rs.next())
					list.add(rs.getInt("ID"));
				return list;
			}
		});
		dao().execute(sql);
		List<Integer> leafNodeIdList = sql.getList(Integer.class);
		for (Menu menu : list) {
			JqgridDataRow row = new JqgridDataRow();
			row.setId(menu.getId());
			List<String> cell = new ArrayList<String>();
			cell.add(String.valueOf(menu.getId()));
			cell.add(menu.getName());
			cell.add(menu.getUrl());
			cell.add(menu.getDescription());
			cell.add(String.valueOf(menu.getRoleId()));
			cell.add(String.valueOf(menu.getLevel()));
			cell.add(String.valueOf(menu.getParentId()));
			boolean isleaf = false;
			if (leafNodeIdList.contains(menu.getId())) {
				isleaf = true;
			}
			cell.add(String.valueOf(isleaf));
			cell.add(String.valueOf(false));
			row.setCell(cell);
			rows.add(row);
		}
		return rows;
	}
}
