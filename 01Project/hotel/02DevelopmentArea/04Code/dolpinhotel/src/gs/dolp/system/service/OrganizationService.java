package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.AdvJqgridIdEntityService;
import gs.dolp.system.domain.Organization;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class OrganizationService extends AdvJqgridIdEntityService<Organization> {

	public OrganizationService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Organization> getGridData(JqgridReqData jqReq, int parentOrgId) {
		Condition cnd = null;
		if (parentOrgId != 0) {
			cnd = Cnd.where("PARENTORGID", "=", parentOrgId);
		}
		AdvancedJqgridResData<Organization> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public List<Organization> getNodes(int id, String name) {
		Condition cnd = null;
		cnd = Cnd.where("PARENTORGID", "=", id);
		List<Organization> orgNodes = query(cnd, null);
		for (Organization orgNode : orgNodes) {
			dao().fetchLinks(orgNode, "nodes");
			if (orgNode.getNodes() != null && orgNode.getNodes().size() > 0) {
				orgNode.setParent(true);
			}
		}
		return orgNodes;
	}
}
