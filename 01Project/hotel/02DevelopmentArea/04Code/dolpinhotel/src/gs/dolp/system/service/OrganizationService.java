package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.system.domain.Organization;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class OrganizationService extends DolpBaseService<Organization> {

	public OrganizationService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Organization> getGridData(JqgridReqData jqReq, int parentOrgId) {
		Condition cnd = Cnd.where("PARENTORGID", "=", parentOrgId);
		AdvancedJqgridResData<Organization> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public AjaxResData getNodes(int id, String name) {
		AjaxResData respData = new AjaxResData();
		Condition cnd = Cnd.where("PARENTORGID", "=", id);
		List<Organization> orgNodes = query(cnd, null);
		for (Organization orgNode : orgNodes) {
			dao().fetchLinks(orgNode, "childrenOrgs");
			if (orgNode.getChildrenOrgs() != null && orgNode.getChildrenOrgs().size() > 0) {
				orgNode.setParent(true);
				orgNode.setChildrenOrgs(null);
			}
		}
		respData.setReturnData(orgNodes);
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
			respData.setSystemMessage("删除成功!", null, null);
		} else if ("add".equals(oper)) {

			dao().insert(organization);
			respData.setSystemMessage("添加成功!", null, null);
		} else if ("edit".equals(oper)) {
			dao().update(organization);
			respData.setSystemMessage("修改成功!", null, null);
		} else {
			respData.setSystemMessage(null, "未知操作!", null);
		}
		return respData;
	}
}
