package gs.dolp.dolpinhotel.management;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("availableRoomCheckModule")
@At("/dolpinhotel/management/availableroomcheck")
public class AvailableRoomCheckModule {

	AvailableRoomCheckService availableRoomCheckService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jq) throws Exception {
		return availableRoomCheckService.getGridData(jq);
	}
}
