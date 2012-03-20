package com.dolplay.dolpbase.system.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.service.PrivilegeService;

@IocBean
@At("/system/privilege")
public class PrivilegeModule {

	@Inject
	private PrivilegeService privilegeService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Privilege privilegeSearch) {
		return privilegeService.getGridData(jqReq, isSearch, privilegeSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Privilege privilege) {
		return privilegeService.CUDPrivilege(oper, ids, privilege);
	}
}