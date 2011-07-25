package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Organization;
import com.dolplay.dolpbase.system.service.OrganizationService;

@IocBean
@At("/system/organization")
public class OrganizationModule {

	@Inject
	private OrganizationService organizationService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("parentOrgId") int parentOrgId) {
		return organizationService.getGridData(jqReq, parentOrgId);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids,
			@Param("..") Organization organization) {
		return organizationService.CUDOrganization(oper, ids, organization);
	}

	@At
	public ResponseData getNodes(@Param("id") int id, @Param("name") String name) {
		return organizationService.getNodes(id, name);
	}
}