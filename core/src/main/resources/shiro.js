var ioc = {

	sessionListener : {
		type : "com.dolplay.dolpbase.system.listener.DolpShiroSessionListener"
	},
		
	sessionManager : {
		type : "org.apache.shiro.web.session.mgt.DefaultWebSessionManager",
		fields : {
			globalSessionTimeout : 3600000,
			sessionListeners : [{
				refer : "sessionListener"
			}]
		}
	},
	
	cacheManager : {
		type : "org.apache.shiro.cache.MemoryConstrainedCacheManager"
	},

	sha256CredentialsMatcher : {
		type : "org.apache.shiro.authc.credential.Sha256CredentialsMatcher",
		fields : {
			storedCredentialsHexEncoded : false,
			hashIterations : 1024,
			hashSalted : true
		}
	},

	shiroDbRealm : {
		type : "com.dolplay.dolpbase.common.shiro.ShiroDbRealm",
		fields : {
			credentialsMatcher : {
				refer : "sha256CredentialsMatcher"
			},
			userService : {
				refer : "userService"
			},
			roleService : {
				refer : "roleService"
			}
		}
	},

	securityManager : {
		type : "org.apache.shiro.web.mgt.DefaultWebSecurityManager",
		fields : {
			sessionMode : "http",
			sessionManager : {
				refer : "sessionManager"
			},
			cacheManager : {
				refer : "cacheManager"
			},
			realm : {
				refer : "shiroDbRealm"
			}
		}
	}
};