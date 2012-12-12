package com.dolplay.dolpbase.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
	@RequiresPermissions("organization:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Organization organizationSearch) {
		return organizationService.getGridData(jqReq, isSearch, organizationSearch);
	}

	@At
	@RequiresPermissions("organization:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids,
			@Param("..") Organization organization) {
		return organizationService.CUDOrganization(oper, ids, organization);
	}

	@At
	@RequiresPermissions("organization:read:*")
	public ResponseData getNodes(@Param("id") Long id, @Param("name") String name) {
		return organizationService.getNodes(id, name);
	}
}