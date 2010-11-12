package gs.dolp.dolpinhotel.management;

import gs.dolp.common.jqgrid.domain.StandardJqgridResData;

import java.sql.SQLException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;

@InjectName("availableRoomCheckModule")
@At("/dolpinhotel/management/availableroomcheck")
public class AvailableRoomCheckModule {

	AvailableRoomCheckService availableRoomCheckService;

	@At
	public StandardJqgridResData getGridData() throws SQLException {
		return availableRoomCheckService.AvailableRoomCount();
	}
}
