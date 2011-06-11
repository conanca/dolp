package gs.dolp;

import gs.dolp.common.util.DaoHandler;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.DTable;
import org.nutz.dao.tools.Tables;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MvcSetup implements Setup {

	/**
	 * 当应用系统启动的时候，自动检查数据库，如果必要的数据表不存在，创建它们并创建默认的记录;
	 * 清空在线用户表
	 */
	@Override
	public void init(NutConfig config) {
		Dao dao = DaoHandler.getDao();
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
		DaoHandler.clearOnlineUser(dao);
	}

	/**
	 * 当应用系统停止的时候，清空在线用户表
	 */
	@Override
	public void destroy(NutConfig config) {
		DaoHandler.clearOnlineUser(DaoHandler.getDao());
	}

}
