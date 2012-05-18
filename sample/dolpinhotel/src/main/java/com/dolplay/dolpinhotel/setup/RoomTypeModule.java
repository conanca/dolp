package com.dolplay.dolpinhotel.setup;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;

@IocBean
@At("/dolpinhotel/setup/roomtype")
public class RoomTypeModule {

	@Inject
	private RoomTypeService roomTypeService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq) {
		return roomTypeService.getGridData(jqReq);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") RoomType roomType) {
		return roomTypeService.CUDRoomType(oper, ids, roomType);
	}

	@At
	public ResponseData getAllRoomTypeMap() {
		return roomTypeService.getAllRoomTypeMap();
	}

}
