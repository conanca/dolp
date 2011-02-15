var ioc = {

	dataSource : {
		type : "com.jolbox.bonecp.BoneCPDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClass : 'org.h2.Driver',
			jdbcUrl : 'jdbc:h2:/dolp/db/dolp;CACHE_SIZE=131072;AUTO_RECONNECT=TRUE',
			username : 'sa',
			password : '',
			minConnectionsPerPartition : 5 ,
			maxConnectionsPerPartition : 20
		}
	},

	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [ {
			refer : 'dataSource'
		} ]
	}
};