package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.User;

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
	public StandardJqgridResData getGridData(int nodeId, int nLeft, int nRight, int nLevel, User logonUser) {
		if (logonUser == null) {
			throw new RuntimeException("用户未登录!");
		}
		User user = dao().fetchLinks(dao().fetch(User.class, logonUser.getId()), "roles");
		List<Role> roles = user.getRoles();
		if (null == roles || roles.size() == 0) {
			throw new RuntimeException("当前用户未被分配角色!");
		}
		StringBuilder roleIdsSB = new StringBuilder();
		for (Role r : roles) {
			roleIdsSB.append(r.getId());
			roleIdsSB.append(",");
		}
		roleIdsSB.deleteCharAt(roleIdsSB.length() - 1);
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<StandardJqgridResDataRow> rows = getMenuNodes(nodeId, nLeft, nRight, nLevel, roleIdsSB.toString());
		jq.setRows(rows);
		return jq;
	}

	@SuppressWarnings("unchecked")
	public List<StandardJqgridResDataRow> getMenuNodes(int nodeId, int nLeft, int nRight, int nLevel, String roleIds) {
		StringBuilder addWhere = new StringBuilder();
		if (nodeId != 0) {
			addWhere.append(" AND NODE.LFT > ").append(nLeft).append(" AND NODE.RGT < ").append(nRight);
			nLevel++;
		} else {
			nLevel = 0;
		}
		final int nLevel1 = nLevel;

		Sql sql = Sqls
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
						+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
						+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
						+ " WHERE NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT "
						+ addWhere
						+ " AND NODE.ID IN(SELECT DISTINCT MENUID FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID in ($roleId))"
						+ " GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("roleId", roleIds);

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

	@Aop(value = "log")
	public StandardJqgridResData getMenuByRoleId(int roleId) {
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<StandardJqgridResDataRow> rows = getMenuNodesByRoleId(roleId);
		jq.setRows(rows);
		return jq;
	}

	@SuppressWarnings("unchecked")
	public List<StandardJqgridResDataRow> getMenuNodesByRoleId(int roleId) {
		// TODO H2 Datbase的BUG，所以此处写成 MENUID+1
		Sql sql = Sqls
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
						+ "NODE.ID IN(SELECT DISTINCT MENUID+1 FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID = $roleId) AS VISIBLE,"
						+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
						+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
						+ " WHERE NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("roleId", roleId);
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardJqgridResDataRow> rows = new ArrayList<StandardJqgridResDataRow>();
				int i = 1;
				while (rs.next()) {
					List cell = new ArrayList();
					cell.add(rs.getInt("ID"));
					cell.add(rs.getString("NAME"));
					cell.add(rs.getString("URL"));
					cell.add(rs.getString("DESCRIPTION"));
					cell.add(rs.getString("VISIBLE"));
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
				return rows;
			}
		});
		dao().execute(sql);
		return (List<StandardJqgridResDataRow>) sql.getResult();
	}
}
