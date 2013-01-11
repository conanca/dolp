var ioc = {

	dataSource : {
		type : "com.alibaba.druid.pool.DruidDataSource",
		events : {
			depose : 'close'
		},
		fields : {
			driverClass : 'org.h2.Driver',
			// 请修改下面的数据库连接信息
			url : 'jdbc:h2:~/dolpbase/db/dolp;CACHE_SIZE=131072;AUTO_RECONNECT=TRUE',
			username : 'sa',
			password : ''
		}
	},

	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [ {
			refer : 'dataSource'
		} ]
	}
};