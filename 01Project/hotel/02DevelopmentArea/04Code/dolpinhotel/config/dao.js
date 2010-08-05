var ioc = {
	dataSource : {
		type : 'org.h2.jdbcx.JdbcConnectionPool',
		events : { depose : 'dispose' },
		args : [ "jdbc:h2:file:~/test", "sa", "" ] },

	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [{refer:'dataSource'}]
	}
};