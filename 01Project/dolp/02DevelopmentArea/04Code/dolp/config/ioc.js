var ioc = {
	/*
	 * User 的服务类
	 */
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
	}
};
