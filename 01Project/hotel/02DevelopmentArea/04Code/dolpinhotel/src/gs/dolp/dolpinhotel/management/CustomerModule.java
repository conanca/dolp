package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.JqgridData;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("customerModule")
@At("/dolpinhotel/management/customer")
@Fail("json")
public class CustomerModule {

	private CustomerService customerService;

	@At("/getGridDataByRoomOccId/*")
	@Ok("json")
	public JqgridData<Customer> getGridDataByRoomOccId(int roomOccId, @Param("page") String page,
			@Param("rows") String rows, @Param("sidx") String sidx, @Param("sord") String sord) {
		return customerService.getGridDataByRoomOccId(roomOccId, page, rows, sidx, sord);
	}
}
