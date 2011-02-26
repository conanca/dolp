package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.Organization;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class OrganizationService extends JqgridService<Organization> {

	public OrganizationService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Organization> getGridData(JqgridReqData jqReq, int parentOrgId) {
		Condition cnd = null;
		cnd = Cnd.where("PARENTORGID", "=", parentOrgId);
		AdvancedJqgridResData<Organization> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public List<Organization> getNodes(int id, String name) {
		Condition cnd = null;
		cnd = Cnd.where("PARENTORGID", "=", id);
		List<Organization> orgNodes = query(cnd, null);
		for (Organization orgNode : orgNodes) {
			dao().fetchLinks(orgNode, "childrenOrgs");
			if (orgNode.getChildrenOrgs() != null && orgNode.getChildrenOrgs().size() > 0) {
				orgNode.setParent(true);
				orgNode.setChildrenOrgs(null);
			}
		}
		return orgNodes;
	}

	@Aop(value = "log")
	public AjaxResData CUDOrganization(String oper, String id, String code, String name, String description,
			int parentOrgId) {
		AjaxResData reData = new AjaxResData();
		if ("del".equals(oper)) {
			final Condition cnd = Cnd.where("ID", "IN", id.split(","));
			final List<Organization> organizations = query(cnd, null);
			Trans.exec(new Atom() {
				public void run() {
					for (Organization organization : organizations) {
						dao().clearLinks(organization, "childrenOrgs");
					}
					clear(cnd);
				}
			});
			reData.setUserdata(new SystemMessage("删除成功!", null, null));
		}
		if ("add".equals(oper)) {
			Organization organization = new Organization();
			organization.setCode(code);
			organization.setName(name);
			organization.setDescription(description);
			organization.setParentOrgId(parentOrgId);
			dao().insert(organization);
			reData.setUserdata(new SystemMessage("添加成功!", null, null));
		}
		if ("edit".equals(oper)) {
			Organization organization = new Organization();
			organization.setId(Integer.parseInt(id));
			organization.setCode(code);
			organization.setName(name);
			organization.setDescription(description);
			organization.setParentOrgId(parentOrgId);
			dao().update(organization);
			reData.setUserdata(new SystemMessage("修改成功!", null, null));
		}
		return reData;
	}
}
