package com.dolplay.dolpinhotel;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.common.util.IocProvider;
import com.dolplay.dolpbase.system.secheduler.DolpSchedulerAdder;
import com.dolplay.dolpbase.system.util.MvcSetupDefaultHandler;
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
		// 初始化IocProvider
		IocProvider.init(Mvcs.getIoc());
		// 初始化dolp的数据表
		MvcSetupDefaultHandler.dolpTableInit();
		
		// 此处添加自定义的操作如初始化数据表,增加调度任务等
		// 初始化dolpin hotel的默认表数据
		Dao dao = DaoProvider.getDao();
		dao.create(Room.class, true);
		dao.create(RoomType.class, true);
		dao.create(Bill.class, true);
		dao.create(Customer.class, true);
		dao.create(RoomOccupancy.class, true);
		FileSqlManager fm = new FileSqlManager("init_hotel_h2.sql");
		List<Sql> sqlList = fm.createCombo(fm.keys());
		dao.execute(sqlList.toArray(new Sql[sqlList.size()]));

		// 进行必要的检查操作
		MvcSetupDefaultHandler.defaultCheck();
		// 增加两个dolp的调度任务
		try {
			DolpSchedulerAdder.add();
		} catch (Exception e) {
			logger.error("增加默认调度任务时发生异常", e);
		}
		// 启动调度任务
		MvcSetupDefaultHandler.startScheduler();
		// 清空在线用户表
		dao.clear("SYSTEM_CLIENT");
	}

	/**
	 * 当应用系统停止的时候:
	 * 1.清空在线用户表;
	 * 2.停止调度程序;
	 */
	@Override
	public void destroy(NutConfig config) {
		MvcSetupDefaultHandler.defaultDestroy();
	}
}