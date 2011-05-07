var ioc = {

	roomTypeService : {
		type : "gs.dolp.dolpinhotel.setup.RoomTypeService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	roomTypeModule : {
		type : "gs.dolp.dolpinhotel.setup.RoomTypeModule",
		fields : {
			roomTypeService : {
				refer : 'roomTypeService'
			}
		}
	},

	roomService : {
		type : "gs.dolp.dolpinhotel.setup.RoomService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	roomModule : {
		type : "gs.dolp.dolpinhotel.setup.RoomModule",
		fields : {
			roomService : {
				refer : 'roomService'
			}
		}
	},

	availableRoomCheckService : {
		type : "gs.dolp.dolpinhotel.management.AvailableRoomCheckService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	availableRoomCheckModule : {
		type : "gs.dolp.dolpinhotel.management.AvailableRoomCheckModule",
		fields : {
			availableRoomCheckService : {
				refer : 'availableRoomCheckService'
			}
		}
	},

	roomOccupancyService : {
		type : "gs.dolp.dolpinhotel.management.RoomOccupancyService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	roomOccupancyModule : {
		type : "gs.dolp.dolpinhotel.management.RoomOccupancyModule",
		fields : {
			roomOccupancyService : {
				refer : 'roomOccupancyService'
			}
		}
	},

	customerService : {
		type : "gs.dolp.dolpinhotel.management.CustomerService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	customerModule : {
		type : "gs.dolp.dolpinhotel.management.CustomerModule",
		fields : {
			customerService : {
				refer : 'customerService'
			}
		}
	},

	billService : {
		type : "gs.dolp.dolpinhotel.management.BillService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	billModule : {
		type : "gs.dolp.dolpinhotel.management.BillModule",
		fields : {
			billService : {
				refer : 'billService'
			}
		}
	}
};