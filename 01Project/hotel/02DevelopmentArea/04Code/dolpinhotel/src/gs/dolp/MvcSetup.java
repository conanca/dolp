package gs.dolp;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.DTable;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MvcSetup implements Setup {

	/**
	 * 当服务启动的时候，自动检查数据库，如果必要的数据表不存在，创建它们并创建默认的记录
	 */
	@Override
	public void init(NutConfig config) {
		Ioc ioc = config.getIoc();
		Dao dao = ioc.get(Dao.class, "dao");
		if (!dao.exists("SYSTEM_USER")) {
			// 初始化表结构
			List<DTable> dts = Tables.loadFrom("tables_system.dod");
			dts.addAll(Tables.loadFrom("tables_hotel.dod"));
			Tables.define(dao, dts);
			// 初始化记录
			FileSqlManager fm = new FileSqlManager("init_system.sql");
			dao.execute(fm.createCombo(fm.keys()));
			fm = new FileSqlManager("init_hotel.sql");
			dao.execute(fm.createCombo(fm.keys()));
		}
	}

	@Override
	public void destroy(NutConfig config) {
		// 服务停止时，暂时什么都不做
	}

}
