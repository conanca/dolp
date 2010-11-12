package gs.dolp;

import gs.dolp.common.view.MyJsonViewMaker;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.JsonIocProvider;

@Modules(scanPackage = true)
@IocBy(type = JsonIocProvider.class, args = { "dao.js", "ioc.js" })
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("jsonx")
@Views(MyJsonViewMaker.class)
public class MainModule {

}