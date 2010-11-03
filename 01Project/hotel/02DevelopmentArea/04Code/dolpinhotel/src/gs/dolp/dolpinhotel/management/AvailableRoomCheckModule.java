package gs.dolp.dolpinhotel.management;

import gs.dolp.jqgrid.domain.JqgridStandardData;

import java.sql.SQLException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;

@InjectName("availableRoomCheckModule")
@At("/dolpinhotel/management/availableroomcheck")
public class AvailableRoomCheckModule {

	AvailableRoomCheckService availableRoomCheckService;

	@At
	public JqgridStandardData getGridData() throws SQLException {
		return availableRoomCheckService.AvailableRoomCount();
	}
}
