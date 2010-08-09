package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.JqgridStandardData;

import java.sql.SQLException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

@InjectName("availableRoomCheckModule")
@At("/dolpinhotel/management/availableroomcheck")
@Fail("json")
public class AvailableRoomCheckModule {

	AvailableRoomCheckService availableRoomCheckService;

	@At
	@Ok("json")
	public JqgridStandardData getGridData() throws SQLException {
		return availableRoomCheckService.AvailableRoomCount();
	}
}
