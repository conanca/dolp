package com.dolplay.dolpbase;

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

import com.dolplay.dolpbase.common.view.DolpJsonViewMaker;
import com.dolplay.dolpbase.system.filter.CheckLogon;
import com.dolplay.dolpbase.system.filter.CheckPrivilege;

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "dao.js", "ioc.system.js",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.dolplay.dolpbase" })
@Encoding(input = "UTF-8", output = "UTF-8")
@SetupBy(MvcSetup.class)
@Ok("json")
@Fail("dolpjson")
@Filters({ @By(type = CheckLogon.class), @By(type = CheckPrivilege.class) })
@Views(DolpJsonViewMaker.class)
public class MainModule {

}