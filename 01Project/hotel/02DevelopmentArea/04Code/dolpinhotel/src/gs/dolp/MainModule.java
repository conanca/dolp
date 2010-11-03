package gs.dolp;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.JsonIocProvider;

@Modules(scanPackage = true)
@IocBy(type = JsonIocProvider.class, args = { "dao.js", "ioc.js" })
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("json")
public class MainModule {

}