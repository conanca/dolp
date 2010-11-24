package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;
import org.nutz.service.Service;

public class MenuService extends Service {

	public MenuService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public StandardJqgridResData getGridData(int nodeId, int nLeft, int nRight, int nLevel) {
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<StandardJqgridResDataRow> rows = getMenuNodes(nodeId, nLeft, nRight, nLevel);
		jq.setRows(rows);
		return jq;
	}

	@SuppressWarnings("unchecked")
	public List<StandardJqgridResDataRow> getMenuNodes(int nodeId, int nLeft, int nRight, int nLevel) {
		String addWhere = "";
		if (nodeId != 0) {
			addWhere = " AND NODE.LFT > " + nLeft + " AND NODE.RGT < " + nRight;
			nLevel++;
		} else {
			nLevel = 0;
		}
		final int nLevel1 = nLevel;

		Sql sql = Sqls.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
				+ "(COUNT(PARENT.NAME) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
				+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
				+ " WHERE NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT " + addWhere
				+ " GROUP BY NODE.NAME ORDER BY NODE.LFT");

		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
				int i = 1;
				while (rs.next()) {
					if (nLevel1 == rs.getInt("LEVEL")) {
						List cell = new ArrayList();
						cell.add(rs.getInt("ID"));
						cell.add(rs.getString("NAME"));
						cell.add(rs.getString("URL"));
						cell.add(rs.getString("DESCRIPTION"));
						cell.add(rs.getInt("LEVEL"));
						cell.add(rs.getInt("LFT"));
						cell.add(rs.getInt("RGT"));
						cell.add(rs.getBoolean("ISLEAF"));
						cell.add(false);
						StandardJqgridResDataRow row = new StandardJqgridResDataRow();
						row.setId(i);
						row.setCell(cell);
						rows.add(row);
						i++;
					}
				}
				return rows;
			}
		});
		dao().execute(sql);
		return (List<StandardJqgridResDataRow>) sql.getResult();
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

	//	@Aop(value = "log")
	//	public List<StandardJqgridResDataRow> list2Rows(List<Menu> list) {
	//
	//		List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
	//
	//		Sql sql = Sqls
	//				.create("SELECT t1.ID FROM SYSTEM_MENU AS t1 LEFT JOIN SYSTEM_MENU as t2 ON t1.ID = t2.PARENTID WHERE t2.ID IS NULL");
	//		sql.setCallback(new SqlCallback() {
	//			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
	//				List<Integer> list = new LinkedList<Integer>();
	//				while (rs.next())
	//					list.add(rs.getInt("ID"));
	//				return list;
	//			}
	//		});
	//		dao().execute(sql);
	//		List<Integer> leafNodeIdList = sql.getList(Integer.class);
	//		for (Menu menu : list) {
	//			List cell = new ArrayList();
	//			cell.add(menu.getId());
	//			cell.add(menu.getName());
	//			cell.add(menu.getUrl());
	//			cell.add(menu.getDescription());
	//			boolean isleaf = false;
	//			if (leafNodeIdList.contains(menu.getId())) {
	//				isleaf = true;
	//			}
	//			cell.add(isleaf);
	//			cell.add(false);
	//			StandardJqgridResDataRow row = new StandardJqgridResDataRow();
	//			row.setId(menu.getId());
	//			row.setCell(cell);
	//			rows.add(row);
	//		}
	//		return rows;
	//	}
	//
	//	@Aop(value = "log")
	//	public void deleteMenus(String ids) {
	//		if (!Strings.isEmpty(ids)) {
	//			String[] idArr = ids.split(",");
	//			for (String id : idArr) {
	//				Menu menu = dao().fetch(Menu.class, Long.parseLong(id));
	//				dao().deleteWith(menu, "childrenMenus");
	//			}
	//		}
	//	}
}
