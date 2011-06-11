package gs.dolp.common.util;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import com.jolbox.bonecp.BoneCPDataSource;

public class DaoHandler {
	public static Dao getDao() {
		Ioc ioc = new NutIoc(new JsonLoader("dao.js"));
		BoneCPDataSource ds = ioc.get(BoneCPDataSource.class, "dataSource");
		Dao dao = new NutDao(ds);
		return dao;
	}
}
