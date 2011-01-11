package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.MenuEntity;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.User;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
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
	public List<MenuEntity> getMenuNodes(int nodeId, int nLeft, int nRight, int nLevel, String roleIds) {
		StringBuilder addWhere = new StringBuilder();
		if (nodeId != 0) {
			addWhere.append(" AND NODE.LFT > ").append(nLeft).append(" AND NODE.RGT < ").append(nRight);
			nLevel++;
		} else {
			nLevel = 0;
		}

		Sql sql = Sqls
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
						+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
						+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
						+ " WHERE NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT "
						+ addWhere
						+ " AND NODE.ID IN(SELECT DISTINCT MENUID FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID in ($roleId))"
						+ " GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("roleId", roleIds);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuEntity.class));
		dao().execute(sql.setCondition(Cnd.where("LEVEL", "=", nLevel)));
		List<MenuEntity> rs = sql.getList(MenuEntity.class);
		return rs;
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
	public AdvancedJqgridResData<MenuEntity> getGridData(int nodeId, int nLeft, int nRight, int nLevel, User logonUser) {
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
		AdvancedJqgridResData<MenuEntity> jq = new AdvancedJqgridResData<MenuEntity>();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<MenuEntity> rows = getMenuNodes(nodeId, nLeft, nRight, nLevel, roleIdsSB.toString());
		jq.setRows(rows);
		return jq;
	}

	/**
	 * 不显示叶子节点
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @return
	 */
	public List<MenuEntity> getMenuNodesOnlyParent(int nodeId, int nLeft, int nRight, int nLevel) {
		StringBuilder addWhere = new StringBuilder();
		if (nodeId != 0) {
			addWhere.append(" AND NODE.LFT > ").append(nLeft).append(" AND NODE.RGT < ").append(nRight);
			nLevel++;
		} else {
			nLevel = 0;
		}

		Sql sql = Sqls.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
				+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
				+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
				+ " WHERE (NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT) AND NODE.RGT<>NODE.LFT+1" + addWhere
				+ " GROUP BY NODE.ID ORDER BY NODE.LFT");

		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuEntity.class));
		dao().execute(sql.setCondition(Cnd.where("LEVEL", "=", nLevel)));
		List<MenuEntity> rs = sql.getList(MenuEntity.class);
		return rs;
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<MenuEntity> getGridDataOnlyParent(int nodeId, int nLeft, int nRight, int nLevel) {
		AdvancedJqgridResData<MenuEntity> jq = new AdvancedJqgridResData<MenuEntity>();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<MenuEntity> rows = getMenuNodesOnlyParent(nodeId, nLeft, nRight, nLevel);
		jq.setRows(rows);
		return jq;
	}

	/**
	 * 角色可见分配菜单页面中的菜单显示调用到的方法
	 * @param roleId
	 * @return
	 */
	public List<MenuEntity> getMenuNodesByRoleId(int roleId) {
		// TODO H2 Datbase的BUG，所以此处写成 MENUID+1
		Sql sql = Sqls
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,"
						+ "NODE.ID IN(SELECT DISTINCT MENUID+1 FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID = $roleId) AS VISIBLE,"
						+ "(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF "
						+ " FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT "
						+ " WHERE NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("roleId", roleId);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuEntity.class));
		dao().execute(sql);
		List<MenuEntity> rs = sql.getList(MenuEntity.class);
		return rs;
	}

	/**
	 * 角色可见分配菜单页面中的菜单显示
	 * @param roleId
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<MenuEntity> getMenuByRoleId(int roleId) {
		AdvancedJqgridResData<MenuEntity> jq = new AdvancedJqgridResData<MenuEntity>();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<MenuEntity> rows = getMenuNodesByRoleId(roleId);
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
		int parentLft = parentNode.getLft();
		int parentRgt = parentNode.getRgt();
		Sql sql = Sqls.create("SELECT * FROM  SYSTEM_MENU M1 $condition AND NOT EXISTS "
				+ "(SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT"
				+ " AND M1.RGT<M2.RGT AND M2.LFT>$parentLft AND M2.RGT<$parentRgt)");
		sql.vars().set("parentLft", parentLft);
		sql.vars().set("parentRgt", parentRgt);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(Menu.class));
		Condition cnd = Cnd.where("LFT", ">", parentLft).and("RGT", "<", parentRgt);
		dao().execute(sql.setCondition(cnd));
		List<Menu> rs = sql.getList(Menu.class);
		// TODO 不支持分页和排序...

		// 开始封装jqGrid的json格式数据类
		AdvancedJqgridResData<Menu> jq = new AdvancedJqgridResData<Menu>();
		jq.setTotal(0);
		jq.setPage(1);
		jq.setRecords(0);
		jq.setRows(rs);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDMenu(String oper, String id, String name, String url, String description, int parentId) {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			for (String aId : id.split(",")) {
				Menu menu = fetch(Long.parseLong(aId));
				Cnd cnd = Cnd.where("LFT", ">=", menu.getLft()).and("RGT", "<=", menu.getRgt());
				clear(cnd);
			}
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			//获取父菜单;
			Menu parentMenu = fetch(parentId);
			int parentLft = parentMenu.getLft();
			int parentRight = parentMenu.getRgt();
			//获取父菜单下，lft,rgt最小的不连续的值，如果没有不连续的，则取lft,rgt最大的
			Sql sql = Sqls.fetchEntity("SELECT M1.* FROM SYSTEM_MENU M1 WHERE"
					+ " NOT EXISTS ( SELECT * FROM SYSTEM_MENU M2 WHERE M2.LFT = M1.RGT+1 )"
					+ " AND M1.LFT>$parentLft AND M1.RGT<$parentRight ORDER BY M1.LFT");
			sql.vars().set("parentLft", parentLft);
			sql.vars().set("parentRight", parentRight);
			// 获取单个实体的回调
			sql.setEntity(dao().getEntity(Menu.class));
			dao().execute(sql);
			Menu brotherOfnewMenu = sql.getObject(Menu.class);
			// 新建菜单
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
