package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("customerModule")
@At("/dolpinhotel/management/customer")
public class CustomerModule {

	private CustomerService customerService;

	@At("/getGridDataByRoomOccId/*")
	public AdvancedJqgridResData<Customer> getGridDataByRoomOccId(int roomOccId, @Param("..") JqgridReqData jqReq) {
		return customerService.getGridDataByRoomOccId(roomOccId, jqReq);
	}
}
