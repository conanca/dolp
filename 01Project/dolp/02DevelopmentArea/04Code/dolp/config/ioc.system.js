var ioc = {

	log : {
		type : 'org.nutz.aop.interceptor.LoggingMethodInterceptor'
	},

	userService : {
		type : "com.dolplay.dolpbase.system.service.UserService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	userModule : {
		type : "com.dolplay.dolpbase.system.module.UserModule",
		fields : {
			userService : {
				refer : 'userService'
			}
		}
	},

	roleService : {
		type : "com.dolplay.dolpbase.system.service.RoleService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	roleModule : {
		type : "com.dolplay.dolpbase.system.module.RoleModule",
		fields : {
			roleService : {
				refer : 'roleService'
			}
		}
	},

	menuService : {
		type : "com.dolplay.dolpbase.system.service.MenuService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	menuModule : {
		type : "com.dolplay.dolpbase.system.module.MenuModule",
		fields : {
			menuService : {
				refer : 'menuService'
			}
		}
	},
	
	organizationService : {
		type : "com.dolplay.dolpbase.system.service.OrganizationService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	organizationModule : {
		type : "com.dolplay.dolpbase.system.module.OrganizationModule",
		fields : {
			organizationService : {
				refer : 'organizationService'
			}
		}
	},
	
	sysEnumService : {
		type : "com.dolplay.dolpbase.system.service.SysEnumService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysEnumItemService : {
		type : "com.dolplay.dolpbase.system.service.SysEnumItemService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysEnumModule : {
		type : "com.dolplay.dolpbase.system.module.SysEnumModule",
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
		type : "com.dolplay.dolpbase.system.service.SysParaService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	sysParaModule : {
		type : "com.dolplay.dolpbase.system.module.SysParaModule",
		fields : {
			sysParaService : {
				refer : 'sysParaService'
			}
		}
	},
	
	systemService : {
		type : "com.dolplay.dolpbase.system.service.SystemService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	systemModule : {
		type : "com.dolplay.dolpbase.system.module.SystemModule",
		fields : {
			systemService : {
				refer : 'systemService'
			},
			userService : {
				refer : 'userService'
			},
			clientService : {
				refer : 'clientService'
			}
		}
	},
	
	privilegeService : {
		type : "com.dolplay.dolpbase.system.service.PrivilegeService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	privilegeModule : {
		type : "com.dolplay.dolpbase.system.module.PrivilegeModule",
		fields : {
			privilegeService : {
				refer : 'privilegeService'
			}
		}
	},
	
	messageService : {
		type : "com.dolplay.dolpbase.system.service.MessageService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	messageModule : {
		type : "com.dolplay.dolpbase.system.module.MessageModule",
		fields : {
			messageService : {
				refer : 'messageService'
			}
		}
	},
	
	clientService : {
		type : "com.dolplay.dolpbase.system.service.ClientService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	clientModule : {
		type : "com.dolplay.dolpbase.system.module.ClientModule",
		fields : {
			clientService : {
				refer : 'clientService'
			}
		}
	}
};