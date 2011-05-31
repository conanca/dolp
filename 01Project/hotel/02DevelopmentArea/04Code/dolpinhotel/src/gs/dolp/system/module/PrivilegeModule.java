package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
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

	@At("/editRow/*")
	public ResponseData editRow(int menuId, @Param("oper") String oper, @Param("id") String id,
			@Param("name") String name, @Param("description") String description, @Param("methodPath") String methodPath) {
		return privilegeService.CUDPrivilege(oper, id, name, description, menuId, methodPath);
	}
}
