var ioc = {

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
	}
};