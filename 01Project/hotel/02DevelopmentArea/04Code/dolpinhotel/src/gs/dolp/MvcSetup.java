package gs.dolp;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.Setup;
import org.nutz.mvc.init.NutConfig;

public class MvcSetup implements Setup {

	/**
	 * 当服务启动的时候，自动检查数据库，如果必要的数据表不存在，创建它们 并创建默认的记录
	 */
	public void init(NutConfig config) {
		Ioc ioc = config.getIoc();
		Dao dao = ioc.get(Dao.class, "dao");
		if (!dao.exists("SYSTEM_USER")) {
			// Create tables
			Tables.define(dao, Tables.loadFrom("tables.dod"));
			FileSqlManager fm = new FileSqlManager("initData.sql");
			dao.execute(fm.createCombo(fm.keys()));
		}
	}

	public void destroy(NutConfig config) {
		// TODO Auto-generated method stub
		// 服务停止时，暂时什么都不做
	}
}
