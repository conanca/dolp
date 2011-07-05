package gs.dolp;

import gs.dolp.common.view.DolpJsonViewMaker;
import gs.dolp.system.filter.CheckLogon;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.JsonIocProvider;

@Modules(scanPackage = true)
@IocBy(type = JsonIocProvider.class, args = { "dao.js", "ioc.system.js" })
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("dolpjson")
@Filters({ @By(type = CheckLogon.class)
//, @By(type = CheckPrivilege.class) 
})
@Views(DolpJsonViewMaker.class)
public class MainModule {

}