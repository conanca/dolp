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
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.SimpleZTreeNode;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Organization;

@IocBean(args = { "refer:dao" })
public class OrganizationService extends DolpBaseService<Organization> {

	public OrganizationService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Organization> getGridData(JqgridReqData jqReq, Boolean isSearch,
			@Param("..") Organization organizationSearch) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (null != organizationSearch) {
			Long parentOrgId = organizationSearch.getParentOrgId();
			if (null != parentOrgId) {
				cnd = Cnd.where("PARENTORGID", "=", parentOrgId);
			}
			if (null != isSearch && isSearch) {
				String code = organizationSearch.getCode();
				if (!Strings.isEmpty(code)) {
					cnd.and("CODE", "LIKE", StringUtils.quote(code, '%'));
				}
				String name = organizationSearch.getName();
				if (!Strings.isEmpty(name)) {
					cnd.and("NAME", "LIKE", StringUtils.quote(name, '%'));
				}
				String description = organizationSearch.getDescription();
				if (!Strings.isEmpty(description)) {
					cnd.and("DESCRIPTION", "LIKE", StringUtils.quote(description, '%'));
				}
			}
		}
		AdvancedJqgridResData<Organization> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData getNodes(Long id) {
		AjaxResData respData = new AjaxResData();
		id = id == null ? 0 : id;
		Sql sql = Sqls
				.create("SELECT O.ID,O.NAME,O.NAME AS TITLE,FALSE AS CHECKED,FALSE AS OPEN,(SELECT COUNT(0) > 0 FROM SYSTEM_ORGANIZATION O1 WHERE O1.PARENTORGID = O.ID) AS ISPARENT FROM SYSTEM_ORGANIZATION O WHERE O.PARENTORGID = @nodeId");
		sql.params().set("nodeId", id);

		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(SimpleZTreeNode.class));
		dao().execute(sql);
		List<SimpleZTreeNode> permissionZTreeNodes = sql.getList(SimpleZTreeNode.class);
		respData.setLogic(permissionZTreeNodes);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData CUDOrganization(String oper, String ids, Organization organization) {
		AjaxResData respData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", ids.split(","));
			final List<Organization> organizations = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Organization organization : organizations) {
						dao().clearLinks(organization, "childrenOrgs");
					}
					clear(cnd);
				}
			});
			respData.setInfo("删除成功!");
		} else if ("add".equals(oper)) {
			dao().insert(organization);
			respData.setInfo("添加成功!");
		} else if ("edit".equals(oper)) {
			dao().update(organization);
			respData.setInfo("修改成功!");
		} else {
			respData.setError("未知操作!");
		}
		return respData;
	}
}