var ioc = {

	log : {
		type : 'org.nutz.aop.interceptor.LoggingMethodInterceptor'
	},

	userService : {
		type : "gs.dolp.system.service.UserService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	userModule : {
		type : "gs.dolp.system.module.UserModule",
		fields : {
			userService : {
				refer : 'userService'
			}
		}
	},

	roleService : {
		type : "gs.dolp.system.service.RoleService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	roleModule : {
		type : "gs.dolp.system.module.RoleModule",
		fields : {
			roleService : {
				refer : 'roleService'
			}
		}
	},

	menuService : {
		type : "gs.dolp.system.service.MenuService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	menuModule : {
		type : "gs.dolp.system.module.MenuModule",
		fields : {
			menuService : {
				refer : 'menuService'
			}
		}
	},
	
	sysEnumService : {
		type : "gs.dolp.system.service.SysEnumService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysEnumItemService : {
		type : "gs.dolp.system.service.SysEnumItemService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysEnumModule : {
		type : "gs.dolp.system.module.SysEnumModule",
		fields : {
			sysEnumService : {
				refer : 'sysEnumService'
			},
			sysEnumItemService : {
				refer : 'sysEnumItemService'
			}
		}
	},
	
	sysParaService : {
		type : "gs.dolp.system.service.SysParaService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysParaModule : {
		type : "gs.dolp.system.module.SysParaModule",
		fields : {
			sysParaService : {
				refer : 'sysParaService'
			}
		}
	},
	
	systemService : {
		type : "gs.dolp.system.service.SystemService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	systemModule : {
		type : "gs.dolp.system.module.SystemModule",
		fields : {
			systemService : {
				refer : 'systemService'
			},
			userService : {
				refer : 'userService'
			},
			menuService : {
				refer : 'menuService'
			}
		}
	},
	
	//------------------分割线--------------------------------

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