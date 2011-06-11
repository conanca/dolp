package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.service.OnlineUserService;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("onlineUserModule")
@At("/system/onlineUser")
public class OnlineUserModule {

	private OnlineUserService onlineUserService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return onlineUserService.getGridData(jqReq);
	}

	@At
	public ResponseData kickOff(@Param("sessionIds[]") String[] sessionIds) {
		return onlineUserService.kickOff(sessionIds);
	}
}
