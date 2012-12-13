package com.dolplay.dolpbase.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.Permission;
import com.dolplay.dolpbase.system.service.PermissionService;

@IocBean
@At("/system/permission")
public class PermissionModule {

	@Inject
	private PermissionService permissionService;

	@At
	@RequiresPermissions("permission:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Permission permissionSearch) {
		return permissionService.getGridData(jqReq, isSearch, permissionSearch);
	}

	@At
	@RequiresPermissions("permission:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Permission permission) {
		return permissionService.CUDPermission(oper, ids, permission);
	}

	@At("/permissionTreeNodes/*")
	public ResponseData getPermissionTreeNodesByRoleId(Long roleId, @Param("id") Long id) {
		return permissionService.getPermissionTreeNodesByRoleId(roleId, id);
	}
}