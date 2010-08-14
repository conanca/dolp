package gs.dolp;

import javax.servlet.ServletConfig;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.Setup;

public class MvcSetup implements Setup {

	/**
	 * 当服务启动的时候，自动检查数据库，如果必要的数据表不存在，创建它们 并创建一个默认的USER 记录
	 */
	public void init(ServletConfig config) {
		Ioc ioc = Mvcs.getIoc(config.getServletContext());
		Dao dao = ioc.get(Dao.class, "dao");
		if (!dao.exists("SYSTEM_USER")) {
			// Create tables
			Tables.define(dao, Tables.loadFrom("tables.dod"));
			FileSqlManager fm = new FileSqlManager("initData.sql");
			dao.execute(fm.createCombo(fm.keys()));
		}
	}

	public void destroy(ServletConfig config) {
		// TODO Auto-generated method stub
		// 服务停止时，暂时什么都不做
	}
}
