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
	
	organizationService : {
		type : "gs.dolp.system.service.OrganizationService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	organizationModule : {
		type : "gs.dolp.system.module.OrganizationModule",
		fields : {
			organizationService : {
				refer : 'organizationService'
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
			clientService : {
				refer : 'clientService'
			}
		}
	},
	
	privilegeService : {
		type : "gs.dolp.system.service.PrivilegeService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	privilegeModule : {
		type : "gs.dolp.system.module.PrivilegeModule",
		fields : {
			privilegeService : {
				refer : 'privilegeService'
			}
		}
	},
	
	messageService : {
		type : "gs.dolp.system.service.MessageService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	messageModule : {
		type : "gs.dolp.system.module.MessageModule",
		fields : {
			messageService : {
				refer : 'messageService'
			}
		}
	},
	
	clientService : {
		type : "gs.dolp.system.service.ClientService",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	clientModule : {
		type : "gs.dolp.system.module.ClientModule",
		fields : {
			clientService : {
				refer : 'clientService'
			}
		}
	}
};