package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.service.OrganizationService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("organizationModule")
@At("/system/organization")
public class OrganizationModule {

	private OrganizationService organizationService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("parentOrgId") int parentOrgId) {
		return organizationService.getGridData(jqReq, parentOrgId);
	}

	@At
	public ResponseData getNodes(@Param("id") int id, @Param("name") String name) {
		return organizationService.getNodes(id, name);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("code") String code,
			@Param("name") String name, @Param("description") String description, @Param("parentOrgId") int parentOrgId) {
		return organizationService.CUDOrganization(oper, id, code, name, description, parentOrgId);
	}
}
