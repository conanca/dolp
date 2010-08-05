package gs.dolp.system.service;

import gs.dolp.jqgrid.JqgridDataRow;
import gs.dolp.jqgrid.JqgridStandardData;
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

	public JqgridStandardData getGridData() {
		int pageNumber = 1;
		int pageSize = 1000;
		Pager pager = dao().createPager(pageNumber, pageSize);
		Condition cnd = Cnd.wrap("1=1");
		List<Menu> list = query(cnd, pager);
		JqgridStandardData jq = new JqgridStandardData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(1);
		jq.setRows(list2Rows(list));
		log.debug(jq.toString());
		return jq;
	}

	//	public List<Menu> getRowList(List<Menu> list, Pager pager) {
	//		if (list != null) {
	//			for (Menu menu : list) {
	//				int level = menu.getLevel() + 1;
	//				int parentId = menu.getId();
	//				Condition cnd = Cnd.where("LEVEL", "=", level).and("PARENTID", "=", parentId == 0 ? "null" : parentId)
	//						.orderBy().asc("ID");
	//				if (count(cnd) == 0) {
	//					return list;
	//				}
	//				List<Menu> newList = query(cnd, pager);
	//			}
	//		}
	//
	//	}

	@SuppressWarnings("unchecked")
	public List<JqgridDataRow> list2Rows(List<Menu> list) {

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
			List cell = new ArrayList();
			cell.add(menu.getId());
			cell.add(menu.getName());
			cell.add(menu.getUrl());
			cell.add(menu.getDescription());
			cell.add(menu.getLevel());
			cell.add(menu.getLevel());
			cell.add(menu.getParentId() == 0 ? null : menu.getParentId());
			boolean isleaf = false;
			if (leafNodeIdList.contains(menu.getId())) {
				isleaf = true;
			}
			cell.add(isleaf);
			cell.add(false);
			JqgridDataRow row = new JqgridDataRow();
			row.setId(menu.getId());
			row.setCell(cell);
			rows.add(row);
		}
		return rows;
	}

	public void deleteMenus(String ids) {
		if (!Strings.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				Menu menu = dao().fetch(Menu.class, Long.parseLong(id));
				dao().deleteWith(menu, "childrenMenus");
			}
		}
	}
}
