package com.dolplay.dolpbase.system.module;


import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.service.PrivilegeService;

@InjectName("privilegeModule")
@At("/system/privilege")
public class PrivilegeModule {

	private PrivilegeService privilegeService;

	@At("/getGridData/*")
	public ResponseData getGridData(int menuId, @Param("..") JqgridReqData jqReq) {
		return privilegeService.getGridData(menuId, jqReq);
	}

	@At()
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Privilege privilege) {
		return privilegeService.CUDPrivilege(oper, ids, privilege);
	}
}
