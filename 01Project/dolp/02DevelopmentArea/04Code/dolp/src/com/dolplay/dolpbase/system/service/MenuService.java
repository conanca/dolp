package com.dolplay.dolpbase.system.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.SimpleZTreeNode;
import com.dolplay.dolpbase.common.domain.ZTreeNode;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.DolpCollectionHandler;
import com.dolplay.dolpbase.system.domain.Menu;
import com.dolplay.dolpbase.system.domain.MenuTreeGridRow;
import com.dolplay.dolpbase.system.domain.MenuZTreeNode;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.domain.Role;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" })
public class MenuService extends DolpBaseService<Menu> {

	public static final String SYSTEM_MAXRIGHTVALUE = "SYSTEM_MAXRIGHTVALUE";

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
	private List<MenuTreeGridRow> getMenuNodes(Long nodeId, Long nLeft, Long nRight, Integer nLevel, String roleIds) {
		nodeId = nodeId == null ? 0 : nodeId;
		Condition cnd = null;
		if (nodeId != 0) {
			cnd = Cnd.where("NODE.LFT", ">", nLeft).and("NODE.RGT", "<", nRight);
			nLevel++;
		} else {
			cnd = Cnd.where("1", "=", 1);
			nLevel = 0;
		}
		Sql sql = Sqls
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF,FALSE AS EXPANDED FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT $condition AND NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT AND NODE.ID IN(SELECT DISTINCT MENUID FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID IN ($roleIds)) GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("roleIds", roleIds);
		sql.setCondition(cnd);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuTreeGridRow.class));
		dao().execute(sql);
		List<MenuTreeGridRow> rs = sql.getList(MenuTreeGridRow.class);
		List<MenuTreeGridRow> menus = new ArrayList<MenuTreeGridRow>();
		for (MenuTreeGridRow menuEntity : rs) {
			if (menuEntity.getLevel() == nLevel) {
				menus.add(menuEntity);
			}
		}
		return menus;
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
	public AdvancedJqgridResData<MenuTreeGridRow> getGridData(Long nodeId, Long nLeft, Long nRight, Integer nLevel,
			User logonUser) {
		if (logonUser == null) {
			throw new RuntimeException("用户未登录!");
		}
		User user = dao().fetchLinks(dao().fetch(User.class, logonUser.getId()), "roles");
		List<Role> roles = user.getRoles();
		if (null == roles || roles.size() == 0) {
			throw new RuntimeException("当前用户未被分配角色!");
		}
		String roleIds;
		AdvancedJqgridResData<MenuTreeGridRow> jq = new AdvancedJqgridResData<MenuTreeGridRow>();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		try {
			roleIds = DolpCollectionHandler.getIdsString(roles, ",");
			List<MenuTreeGridRow> rows = getMenuNodes(nodeId, nLeft, nRight, nLevel, roleIds);
			jq.setRows(rows);
		} catch (Exception e) {
			throw new RuntimeException("获取角色ID异常!", e);
		}
		return jq;
	}

	/**
	 * 菜单管理和权限管理页面，左侧菜单树的显示（不显示叶子节点）
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData getTreeNodes(Long nodeId) {
		AjaxResData resData = new AjaxResData();
		long parentLft;
		long parentRgt = 0;
		nodeId = nodeId == null ? 0 : nodeId;
		if (nodeId == 0) {
			parentLft = 0;
			// 取系统参数:"菜单节点最大Rigth值"
			long rootRgt = Long.valueOf(getSysParaValue(SYSTEM_MAXRIGHTVALUE));
			if (rootRgt <= 0) {
				throw new RuntimeException("系统参数:\"菜单节点最大Rigth值\"错误!");
			}
			parentRgt = rootRgt;
		} else {
			Menu parentNode = fetch(nodeId);
			parentLft = parentNode.getLft();
			parentRgt = parentNode.getRgt();
		}
		Sql sql = Sqls
				.create("SELECT M1.ID,M1.NAME,FALSE AS CHECKED,FALSE AS OPEN,(LFT+1<>RGT)AS ISPARENT FROM SYSTEM_MENU M1 WHERE M1.LFT>@parentLft AND M1.RGT<@parentRgt AND NOT EXISTS (SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT AND M1.RGT<M2.RGT AND M2.LFT>@parentLft AND M2.RGT<@parentRgt)");
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRgt", parentRgt);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(SimpleZTreeNode.class));
		dao().execute(sql);
		List<SimpleZTreeNode> rs = sql.getList(SimpleZTreeNode.class);
		resData.setReturnData(rs);
		return resData;
	}

	/**
	 *  菜单管理页面，右侧菜单grid的显示（只显示本level的菜单，下一级的和更深的节点不显示）
	 * @param jqReq
	 * @param parentId
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Menu> getGridData(JqgridReqData jqReq, Long parentId) {
		long parentLft;
		long parentRgt = 0;
		if (parentId == 0) {
			parentLft = 0;
			// 取系统参数:"菜单节点最大Rigth值"
			long rootRgt = Long.valueOf(getSysParaValue(SYSTEM_MAXRIGHTVALUE));
			if (rootRgt <= 0) {
				throw new RuntimeException("系统参数:\"菜单节点最大Rigth值\"错误!");
			}
			parentRgt = rootRgt;
		} else {
			Menu parentNode = fetch(parentId);
			parentLft = parentNode.getLft();
			parentRgt = parentNode.getRgt();
		}
		Sql sql = Sqls
				.create("SELECT * FROM SYSTEM_MENU M1 WHERE M1.LFT>@parentLft AND M1.RGT<@parentRgt AND NOT EXISTS (SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT AND M1.RGT<M2.RGT AND M2.LFT>@parentLft AND M2.RGT<@parentRgt)");
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRgt", parentRgt);
		// 开始封装jqGrid的json格式数据类
		AdvancedJqgridResData<Menu> jq = getAdvancedJqgridRespData(Menu.class, sql, jqReq);
		return jq;
	}

	/**
	 * 菜单管理页面，右侧菜单grid的增删改
	 * @param oper
	 * @param id
	 * @param name
	 * @param url
	 * @param description
	 * @param parentId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData CUDMenu(String oper, String ids, Menu menu, Long parentId) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			for (String aId : ids.split(",")) {
				Menu aMenu = fetch(Long.parseLong(aId));
				Cnd cnd = Cnd.where("LFT", ">=", aMenu.getLft()).and("RGT", "<=", aMenu.getRgt());
				clear(cnd);
			}
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {
			//获取父菜单;
			long parentLft = 0;
			long parentRight = 0;
			if (parentId > 0) {
				Menu parentMenu = fetch(parentId);
				parentLft = parentMenu.getLft();
				parentRight = parentMenu.getRgt();
			} else {
				parentRight = Integer.valueOf(getSysParaValue(SYSTEM_MAXRIGHTVALUE));
			}
			//获取父菜单下，lft,rgt最小的不连续的值，如果没有不连续的，则取lft,rgt最大的
			Sql sql = Sqls
					.create("SELECT * FROM SYSTEM_MENU M1 WHERE NOT EXISTS ( SELECT * FROM SYSTEM_MENU M2 WHERE M2.LFT = M1.RGT+1 ) AND LFT>@parentLft AND RGT<@parentRight-2 ORDER BY LFT");
			sql.params().set("parentLft", parentLft);
			sql.params().set("parentRight", parentRight);
			// 获取单个实体的回调
			sql.setCallback(Sqls.callback.entity());
			sql.setEntity(dao().getEntity(Menu.class));
			dao().execute(sql);
			Menu brotherOfnewMenu = sql.getObject(Menu.class);
			if (brotherOfnewMenu == null) {
				respData.setSystemMessage(null, "该菜单节点已满,添加失败!", null);
			} else {
				// 设置左右值
				menu.setLft(brotherOfnewMenu.getLft() + 2);
				menu.setRgt(brotherOfnewMenu.getRgt() + 2);
				dao().insert(menu);
				respData.setSystemMessage("添加成功!", null, null);
			}
		} else if ("edit".equals(oper)) {
			dao().update(menu);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}

	/**
	 * 添加 非叶节点
	 * @param parentId
	 * @param name
	 * @param description
	 * @param parentLevel
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData addMenuIsNotLeaf(Long parentId, String name, String description) {
		AjaxResData respData = new AjaxResData();
		//获取父菜单;
		long parentLft = 0;
		long parentRight = 0;
		if (parentId > 0) {
			Menu parentMenu = fetch(parentId);
			parentLft = parentMenu.getLft();
			parentRight = parentMenu.getRgt();
		} else {
			parentRight = Integer.valueOf(getSysParaValue(SYSTEM_MAXRIGHTVALUE));
		}
		//获取父菜单下，lft,rgt最小的不连续的值，如果没有不连续的，则取lft,rgt最大的
		Sql sql = Sqls
				.create("SELECT * FROM SYSTEM_MENU M1 WHERE NOT EXISTS ( SELECT * FROM SYSTEM_MENU M2 WHERE M2.LFT = M1.RGT+1 ) AND LFT>@parentLft AND RGT<@parentRight-2 ORDER BY LFT");
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRight", parentRight);
		// 获取实体列表的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(Menu.class));
		dao().execute(sql);
		List<Menu> brotherOfnewMenus = sql.getList(Menu.class);
		if (brotherOfnewMenus == null || brotherOfnewMenus.size() == 0) {
			respData.setSystemMessage(null, "该菜单节点已满,添加失败!", null);
		} else {
			// 新建菜单
			Menu menu = new Menu();
			menu.setName(name);
			menu.setDescription(description);
			menu.setLft(brotherOfnewMenus.get(0).getRgt() + 1);
			if (brotherOfnewMenus.size() == 1) {
				menu.setRgt(parentRight - 1);
			} else if (brotherOfnewMenus.size() >= 2) {
				menu.setRgt(brotherOfnewMenus.get(1).getLft() - 1);
			}
			dao().insert(menu);
			respData.setSystemMessage("添加成功!", null, null);
		}

		return respData;
	}

	/**
	 * 角色管理页面中的权限分配菜单树显示
	 * @param roleId
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData getPrivilegeTreeNodesByRoleId(Long roleId, Long nodeId, Long nLeft, Long nRight) {
		AjaxResData respData = new AjaxResData();
		List<ZTreeNode> nodes = new ArrayList<ZTreeNode>();
		roleId = roleId == null ? 0 : roleId;

		// 获取菜单数据节点
		long parentLft;
		long parentRgt = 0;
		nodeId = nodeId == null ? 0 : nodeId;
		if (nodeId == 0) {
			parentLft = 0;
			// 取系统参数:"菜单节点最大Rigth值"
			long rootRgt = Long.valueOf(getSysParaValue(SYSTEM_MAXRIGHTVALUE));
			if (rootRgt <= 0) {
				throw new RuntimeException("系统参数:\"菜单节点最大Rigth值\"错误!");
			}
			parentRgt = rootRgt;
		} else {
			Menu parentNode = fetch(nodeId);
			parentLft = parentNode.getLft();
			parentRgt = parentNode.getRgt();
		}
		Sql sql = Sqls
				.create("SELECT M1.*,M1.ID IN(SELECT MENUID FROM SYSTEM_ROLE_MENU WHERE SYSTEM_ROLE_MENU.ROLEID = @roleId) AS CHECKED,FALSE AS OPEN,(LFT+1<>RGT)AS ISPARENT FROM  SYSTEM_MENU M1 WHERE M1.LFT>@parentLft AND M1.RGT<@parentRgt AND NOT EXISTS (SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT AND M1.RGT<M2.RGT AND M2.LFT>@parentLft AND M2.RGT<@parentRgt)");
		sql.params().set("roleId", roleId);
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRgt", parentRgt);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuZTreeNode.class));
		dao().execute(sql);
		List<MenuZTreeNode> menuZTreeNodes = sql.getList(MenuZTreeNode.class);
		// 如果菜单含操作权限数据，则将其设为父节点
		for (MenuZTreeNode menuZTreeNode : menuZTreeNodes) {
			long privilegesCount = dao().count(Privilege.class, Cnd.where("MENUID", "=", menuZTreeNode.getId()));
			if (privilegesCount > 0) {
				menuZTreeNode.setParent(true);
			}
		}
		nodes.addAll(menuZTreeNodes);

		// 获取权限数据节点
		sql = Sqls
				.create("SELECT ID,NAME,ID IN(SELECT PRIVILEGEID FROM SYSTEM_ROLE_PRIVILEGE WHERE SYSTEM_ROLE_PRIVILEGE.ROLEID = @roleId) AS CHECKED,FALSE AS OPEN,FALSE AS ISPARENT FROM SYSTEM_PRIVILEGE WHERE MENUID=@nodeId");
		sql.params().set("roleId", roleId);
		sql.params().set("nodeId", nodeId);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(SimpleZTreeNode.class));
		dao().execute(sql);
		List<SimpleZTreeNode> privilegeZTreeNodes = sql.getList(SimpleZTreeNode.class);

		nodes.addAll(privilegeZTreeNodes);
		respData.setReturnData(nodes);
		return respData;
	}
}