package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.domain.Privilege;
import gs.dolp.system.service.PrivilegeService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

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
