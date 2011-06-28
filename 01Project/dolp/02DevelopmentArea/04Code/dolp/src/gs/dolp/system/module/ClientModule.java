package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.service.ClientService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("clientModule")
@At("/system/client")
public class ClientModule {

	private ClientService clientService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return clientService.getGridData(jqReq);
	}

	@At
	public ResponseData kickOff(@Param("sessionIds[]") String[] sessionIds) {
		return clientService.kickOff(sessionIds);
	}
}
