package gs.dolp.dolpinhotel.management;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("customerModule")
@At("/dolpinhotel/management/customer")
public class CustomerModule {

	private CustomerService customerService;

	@At("/getGridDataByRoomOccId/*")
	public ResponseData getGridDataByRoomOccId(int roomOccId, @Param("..") JqgridReqData jqReq) {
		return customerService.getGridDataByRoomOccId(roomOccId, jqReq);
	}
}
