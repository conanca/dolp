package com.dolplay.dolpbase.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.mgt.SecurityManager;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.SimpleZTreeNode;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Menu;
import com.dolplay.dolpbase.system.domain.MenuTreeGridRow;

@IocBean(args = { "refer:dao" })
public class MenuService extends DolpBaseService<Menu> {

	public static final String SYSTEM_MAXRIGHTVALUE = "SYSTEM_MAXRIGHTVALUE";
	@Inject
	private SecurityManager securityManager;

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
	private List<MenuTreeGridRow> getMenuNodes(Long nodeId, Long nLeft, Long nRight, Integer nLevel,
			String permissionIds) {
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
				.create("SELECT NODE.ID,NODE.NAME,NODE.URL,NODE.DESCRIPTION,(COUNT(PARENT.ID) - 1) AS LEVEL,NODE.LFT,NODE.RGT,NODE.RGT=NODE.LFT+1 AS ISLEAF,FALSE AS EXPANDED FROM SYSTEM_MENU AS NODE,SYSTEM_MENU AS PARENT $condition AND NODE.LFT BETWEEN PARENT.LFT AND PARENT.RGT AND NODE.PERMISSIONID IN($permissionIds) GROUP BY NODE.ID ORDER BY NODE.LFT");
		sql.vars().set("permissionIds", permissionIds);
		sql.setCondition(cnd);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(MenuTreeGridRow.class));
		dao().execute(sql);
		List<MenuTreeGridRow> rs = sql.getList(MenuTreeGridRow.class);
		List<MenuTreeGridRow> menus = new ArrayList<MenuTreeGridRow>();
		for (MenuTreeGridRow menuEntity : rs) {
			if (menuEntity.getLevel().intValue() == nLevel.intValue()) {
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
			HttpSession session) {
		long[] permissionIdArr = (long[]) session.getAttribute("CurrentPermission");
		String permissionIds = "0";

		if (permissionIdArr != null) {
			for (Long permissionId : permissionIdArr) {
				if (!Lang.isEmpty(permissionId)) {
					permissionIds += permissionId + ",";
				}
			}
		}
		permissionIds = permissionIds.substring(0, permissionIds.length() - 1);
		AdvancedJqgridResData<MenuTreeGridRow> jq = new AdvancedJqgridResData<MenuTreeGridRow>();
		jq.setPage(1);
		jq.setTotal(1);
		jq.setRecords(0);
		List<MenuTreeGridRow> rows = getMenuNodes(nodeId, nLeft, nRight, nLevel, permissionIds);
		jq.setRows(rows);
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
				.create("SELECT M1.ID,M1.NAME,M1.NAME AS TITLE,FALSE AS CHECKED,FALSE AS OPEN,(LFT+1<>RGT)AS ISPARENT FROM SYSTEM_MENU M1 WHERE M1.LFT>@parentLft AND M1.RGT<@parentRgt AND NOT EXISTS (SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT AND M1.RGT<M2.RGT AND M2.LFT>@parentLft AND M2.RGT<@parentRgt)");
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRgt", parentRgt);
		// 查询实体的回调
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(SimpleZTreeNode.class));
		dao().execute(sql);
		List<SimpleZTreeNode> rs = sql.getList(SimpleZTreeNode.class);
		resData.setLogic(rs);
		return resData;
	}

	/**
	 *  菜单管理页面，右侧菜单grid的显示（只显示本level的菜单，下一级的和更深的节点不显示）
	 * @param jqReq
	 * @param parentId
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Menu> getGridData(JqgridReqData jqReq, Long parentId, Boolean isSearch, Menu menuSearch) {
		long parentLft;
		long parentRgt = 0;
		parentId = parentId == null ? 0 : parentId;
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

		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != isSearch && isSearch && null != menuSearch) {
			String name = menuSearch.getName();
			if (!Strings.isEmpty(name)) {
				cnd.and("M1.NAME", "LIKE", StringUtils.quote(name, '%'));
			}
			String url = menuSearch.getUrl();
			if (!Strings.isEmpty(url)) {
				cnd.and("M1.URL", "LIKE", StringUtils.quote(url, '%'));
			}
			String description = menuSearch.getDescription();
			if (!Strings.isEmpty(description)) {
				cnd.and("M1.DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
			}
		}

		Sql sql = Sqls
				.create("SELECT * FROM SYSTEM_MENU M1 $condition AND M1.LFT>@parentLft AND M1.RGT<@parentRgt AND NOT EXISTS (SELECT * FROM SYSTEM_MENU M2 WHERE M1.LFT>M2.LFT AND M1.RGT<M2.RGT AND M2.LFT>@parentLft AND M2.RGT<@parentRgt)");
		sql.params().set("parentLft", parentLft);
		sql.params().set("parentRgt", parentRgt);
		sql.setCondition(cnd);
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
			respData.setInfo("删除成功!");
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
				respData.setError("该菜单节点已满,添加失败!");
			} else {
				// 设置左右值
				menu.setLft(brotherOfnewMenu.getLft() + 2);
				menu.setRgt(brotherOfnewMenu.getRgt() + 2);
				dao().insert(menu);
				respData.setInfo("添加成功!");
			}
		} else if ("edit".equals(oper)) {
			dao().update(menu);
			respData.setInfo("修改成功!");
		} else {
			respData.setError("未知操作!");
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
			respData.setError("该菜单节点已满,添加失败!");
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
			respData.setInfo("添加成功!");
		}

		return respData;
	}

}