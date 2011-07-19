package gs.dolp;

import gs.dolp.common.schedule.SchedulerRunner;
import gs.dolp.common.util.DaoHandler;
import gs.dolp.system.domain.Client;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.Message;
import gs.dolp.system.domain.Organization;
import gs.dolp.system.domain.Privilege;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;
import gs.dolp.system.domain.SysPara;
import gs.dolp.system.domain.User;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MvcSetup implements Setup {

	/**
	 * 当应用系统启动的时候，自动检查数据库，如果必要的数据表不存在，创建它们并创建默认的记录;
	 * 清空在线用户表;
	 * 启动调度程序
	 */
	@Override
	public void init(NutConfig config) {
		Dao dao = DaoHandler.getDao();
		// 初始化系统基本的数据表
		if (!dao.exists("SYSTEM_USER")) {
			// 建实体类的表
			dao.create(Client.class, true);
			dao.create(Menu.class, true);
			dao.create(Message.class, true);
			dao.create(Organization.class, true);
			dao.create(Privilege.class, true);
			dao.create(Role.class, true);
			dao.create(SysEnum.class, true);
			dao.create(SysEnumItem.class, true);
			dao.create(SysPara.class, true);
			dao.create(User.class, true);

			// 添加默认记录
			FileSqlManager fm = new FileSqlManager("init_system.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}

		// 初始化quartz的数据表
		if (!dao.exists("QRTZ_JOB_DETAILS")) {
			FileSqlManager fm = new FileSqlManager("tables_quartz.sql");
			List<Sql> sqlList = fm.createCombo(fm.keys());
			dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		}

		// 清空在线用户表
		DaoHandler.getDao().clear("SYSTEM_CLIENT");

		//		// 启动Scheduler
		//		SchedulerRunner runner = new SchedulerRunner();
		//		try {
		//			runner.run();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 当应用系统停止的时候，清空在线用户表;
	 * 停止调度程序;
	 * 关闭 DaoHandler的数据库连接
	 */
	@Override
	public void destroy(NutConfig config) {
		// 清空在线用户表
		DaoHandler.getDao().clear("SYSTEM_CLIENT");
		// 停止Scheduler
		SchedulerRunner runner = new SchedulerRunner();
		try {
			runner.stop();
			DaoHandler.getDataSource().getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
