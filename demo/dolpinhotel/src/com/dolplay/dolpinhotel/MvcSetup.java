package com.dolplay.dolpinhotel;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.system.secheduler.DolpScheduler;
import com.dolplay.dolpbase.system.util.MvcSetupDefaultHandler;
import com.dolplay.dolpbase.system.util.NutConfigStorage;
import com.dolplay.dolpinhotel.management.Bill;
import com.dolplay.dolpinhotel.management.Customer;
import com.dolplay.dolpinhotel.management.RoomOccupancy;
import com.dolplay.dolpinhotel.setup.Room;
import com.dolplay.dolpinhotel.setup.RoomType;

public class MvcSetup implements Setup {
	private static Logger logger = LoggerFactory.getLogger(MvcSetup.class);

	/**
	 * 当应用系统启动的时候:
	 * 1.自动检查数据库，如果必要的数据表不存在，创建它们并创建默认的记录;
	 * 2.清空在线用户表;
	 * 3.启动调度程序
	 */
	@Override
	public void init(NutConfig config) {
		NutConfigStorage.loadNutConfig(config);
		Dao dao = DaoProvider.getDao();
		if (!dao.exists("SYSTEM_USER")) {
			MvcSetupDefaultHandler.defaultInit(config);
		}
		// 初始化默认表数据
		if (!dao.exists("DOLPINHOTEL_ROOMTYPE")) {
			dao.create(Room.class, true);
			dao.create(RoomType.class, true);
			dao.create(Bill.class, true);
			dao.create(Customer.class, true);
			dao.create(RoomOccupancy.class, true);
			FileSqlManager fm = new FileSqlManager("init_hotel_h2.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}
		// 默认检查
		MvcSetupDefaultHandler.defaultCheck(config);
	}

	/**
	 * 当应用系统停止的时候:
	 * 1.清空在线用户表;
	 * 2.停止调度程序;
	 */
	@Override
	public void destroy(NutConfig config) {
		// 清空在线用户表
		DaoProvider.getDao().clear("SYSTEM_CLIENT");
		try {
			DolpScheduler.stop();
		} catch (Exception e) {
			logger.error("Stop SchedulerRunner exception", e);
		}
	}
}