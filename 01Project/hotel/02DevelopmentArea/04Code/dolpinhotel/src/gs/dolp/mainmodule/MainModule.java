package gs.dolp.mainmodule;

import gs.dolp.dolpinhotel.setup.RoomModule;
import gs.dolp.dolpinhotel.setup.RoomTypeModule;
import gs.dolp.system.module.MenuModule;
import gs.dolp.system.module.RoleModule;
import gs.dolp.system.module.UserModule;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.JsonIocProvider;

@Modules( { UserModule.class, RoleModule.class, MenuModule.class, RoomTypeModule.class, RoomModule.class })
@IocBy(type = JsonIocProvider.class, args = { "dao.js", "ioc.js" })
@SetupBy(MvcSetup.class)
@Fail("json")
public class MainModule {

}