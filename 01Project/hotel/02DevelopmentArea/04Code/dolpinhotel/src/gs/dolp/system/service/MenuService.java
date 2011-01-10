package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResData;
import gs.dolp.common.jqgrid.domain.StandardJqgridResDataRow;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.aop.Aop;

public class MenuService extends AdvJqgridIdEntityService<Menu> {

	public MenuService(Dao dao) {
		super(dao);
	}

	/**
	 * west布局的菜单显示调用到的方法
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @param roleIds
	 * @return
	 */
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

	/**
	 * west布局的菜单显示
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @param logonUser
	 * @return
	 */
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

	public List<StandardJqgridResDataRow> getMenuNodesOnlyParent(int nodeId, int nLeft, int nRight, int nLevel) {
		StringBuilder addWhere = new StringBuilder();
		if (nodeId != 0) {
			addWhere.append(" AND NODE.LFT > ").append(nLeft).append(" AND NODE.RGT < ").append(nRight);
			nLevel++;
		} else {
			nLevel = 0;
		}
		final int nLevel1 = nLevel;

		Sql sql = Sqls.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
				+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
				+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
				+ " WHERE (NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT) AND NODE.RGT<>NODE.LFT+1" + addWhere
				+ " GROUP BY NODE.ID ORDER BY NODE.LFT");

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
	public StandardJqgridResData getGridDataOnlyParent(int nodeId, int nLeft, int nRight, int nLevel) {
		StandardJqgridResData jq = new StandardJqgridResData();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<StandardJqgridResDataRow> rows = getMenuNodesOnlyParent(nodeId, nLeft, nRight, nLevel);
		jq.setRows(rows);
		return jq;
	}

	/**
	 * 角色可见分配菜单页面中的菜单显示调用到的方法
	 * @param roleId
	 * @return
	 */
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

	/**
	 * 角色可见分配菜单页面中的菜单显示
	 * @param roleId
	 * @return
	 */
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

	@Aop(value = "log")
	private Menu getParentNode(int parentId) {
		return fetch(parentId);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Menu> getGridData(JqgridReqData jqReq, int parentId) {
		Menu parentNode = getParentNode(parentId);
		Cnd cnd = Cnd.where("LFT", ">", parentNode.getLft()).and("RGT", "<", parentNode.getRgt());
		AdvancedJqgridResData<Menu> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDMenu(String oper, String id, String name, String url, String description, int parentId) {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			Condition cnd = Cnd.wrap(new StringBuilder("ID IN (").append(id).append(")").toString());
			clear(cnd);
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			//获取父菜单;
			Menu parentMenu = fetch(parentId);
			int parentLft = parentMenu.getLft();
			int parentRight = parentMenu.getRgt();
			//获取父菜单下，lft,rgt最小的不连续的值，如果没有不连续的，则取lft,rgt最大的
			Sql sql = Sqls
					.create("SELECT M1.* FROM SYSTEM_MENU M1 WHERE NOT EXISTS "
							+ "(SELECT * FROM SYSTEM_MENU M2 WHERE M2.LFT = M1.RGT+1 AND (M1.LFT>$parentLft AND M1.RGT<$parentRight ))"
							+ " AND (M1.LFT>$parentLft AND M1.RGT<$parentRight"
							+ " AND M1.LFT<(SELECT MAX(LFT) FROM SYSTEM_MENU WHERE SYSTEM_MENU.LFT>$parentLft AND SYSTEM_MENU.LFT<$parentRight))");
			sql.vars().set("parentLft", parentLft);
			sql.vars().set("parentRight", parentRight);
			sql.setCallback(new SqlCallback() {
				public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
					List<Menu> brotherOfnewMenus = new ArrayList<Menu>();
					while (rs.next()) {
						Menu brotherOfnewMenu = new Menu();
						brotherOfnewMenu.setLft(rs.getInt("LFT"));
						brotherOfnewMenu.setRgt(rs.getInt("RGT"));
						brotherOfnewMenus.add(brotherOfnewMenu);
					}
					return brotherOfnewMenus;
				}
			});
			dao().execute(sql);
			List<Menu> brotherOfnewMenus = (List<Menu>) sql.getResult();
			Menu brotherOfnewMenu = brotherOfnewMenus.get(0);
			Menu menu = new Menu();
			menu.setName(name);
			menu.setUrl(url);
			menu.setDescription(description);
			menu.setLft(brotherOfnewMenu.getLft() + 2);
			menu.setRgt(brotherOfnewMenu.getRgt() + 2);
			dao().insert(menu);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Menu menu = fetch(Long.parseLong(id));
			menu.setName(name);
			menu.setUrl(url);
			menu.setDescription(description);
			dao().update(menu);
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}
}
