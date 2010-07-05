package gs.dolp.system.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

public class UserServiceTest {

	private static UserService us;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Ioc ioc = new NutIoc(new JsonLoader("dao.js"));
		Dao dao = ioc.get(Dao.class, "dao");
		us = new UserService(dao);
	}

	@Test
	public void testAddRole() {
		us.updateRole("1", "14,15,16");
	}

}
