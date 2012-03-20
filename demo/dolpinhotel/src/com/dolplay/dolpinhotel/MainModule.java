package com.dolplay.dolpinhotel;

import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.dolplay.dolpbase.common.view.DolpViewMaker;
import com.dolplay.dolpbase.system.filter.CheckLogon;
import com.dolplay.dolpbase.system.filter.CheckPrivilege;

// 如果在其他包下还有子模块，在此处value内，增加任意一个子模块的类名+.class
@Modules(packages = { "com.dolplay.dolpbase.system.module", "com.dolplay.dolpinhotel" }, scanPackage = true)
// 如果在其他包下还有类交予ico容器管理，需要在"com.dolplay.dolpbase"的后面加上
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "dao.js", "ioc.system.js",
		"ioc.dolpbase.js", "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.dolplay.dolpbase",
		"com.dolplay.dolpinhotel" })
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("dolpjson")
@Filters({ @By(type = CheckLogon.class), @By(type = CheckPrivilege.class) })
@Views(DolpViewMaker.class)
public class MainModule {

}