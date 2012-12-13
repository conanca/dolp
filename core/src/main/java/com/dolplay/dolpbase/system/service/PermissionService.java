package com.dolplay.dolpbase.system.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.SimpleZTreeNode;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Permission;
import com.dolplay.dolpbase.system.domain.PermissionZTreeNode;

@IocBean(args = { "refer:dao" })
public class PermissionService extends DolpBaseService<Permission> {

	public PermissionService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Permission> getGridData(JqgridReqData jqReq, Boolean isSearch,
			Permission permissionSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != permissionSearch) {
			if (null != isSearch && isSearch) {
				String name = permissionSearch.getName();
				if (!Strings.isEmpty(name)) {
					cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
				}
				String description = permissionSearch.getDescription();
				if (!Strings.isEmpty(description)) {
					cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
				}
			}
		}
		AdvancedJqgridResData<Permission> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData CUDPermission(String oper, String ids, Permission permission) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			clear(cnd);
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(permission);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(permission);
			respData.setInfo("修改成功!");
		} else {
			respData.setInfo("未知操作!");
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
	public AjaxResData getPermissionTreeNodesByRoleId(Long roleId, Long nodeId) {
		AjaxResData respData = new AjaxResData();
		String o = "=";
		if (nodeId == null) {
			o = "IS";
		}
		Sql sql = Sqls
				.create("SELECT P.ID,P.NAME,P.DESCRIPTION,P.ID IN(SELECT PERMISSIONID FROM SYSTEM_ROLE_PERMISSION WHERE SYSTEM_ROLE_PERMISSION.ROLEID = @roleId) AS CHECKED,FALSE AS OPEN,(SELECT COUNT(0) > 0 FROM SYSTEM_PERMISSION P1 WHERE P1.PARENTPERMISSIONID = P.ID) AS ISPARENT FROM SYSTEM_PERMISSION P WHERE P.PARENTPERMISSIONID "
						+ o + " @nodeId");
		sql.params().set("roleId", roleId);
		sql.params().set("nodeId", nodeId);

		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(SimpleZTreeNode.class));
		dao().execute(sql);
		List<PermissionZTreeNode> permissionZTreeNodes = sql.getList(PermissionZTreeNode.class);
		respData.setLogic(permissionZTreeNodes);
		return respData;
	}
}