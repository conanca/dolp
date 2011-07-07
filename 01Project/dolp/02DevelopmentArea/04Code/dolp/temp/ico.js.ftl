	${Domain?uncap_first}Service : {
		type : "${package}.service.${Domain}Service",
		args : {
			dao : {
				refer : 'dao'
			}
		}
	},

	${Domain?uncap_first}Module : {
		type : "${package}.module.${Domain}Module",
		fields : {
			${Domain?uncap_first}Service : {
				refer : '${Domain?uncap_first}Service'
			}
		}
	}