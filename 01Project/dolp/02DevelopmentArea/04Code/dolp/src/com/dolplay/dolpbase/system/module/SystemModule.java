package com.dolplay.dolpbase.system.module;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.system.domain.Privilege;
import com.dolplay.dolpbase.system.domain.User;
import com.dolplay.dolpbase.system.service.ClientService;
import com.dolplay.dolpbase.system.service.SystemService;
import com.dolplay.dolpbase.system.service.UserService;

@IocBean
@Filters
public class SystemModule {

	@Inject
	private SystemService systemService;
	@Inject
	private UserService userService;
	@Inject
	private ClientService clientService;

	@At
	public ResponseData getSystemName() {
		return systemService.getSystemName();
	}

	@At("/getSysEnumItemMap/*")
	public ResponseData getSysEnumItemMap(String sysEnumName) {
		return systemService.getSysEnumItemMap(sysEnumName);
	}

	/**
	 * dummy existing url, 用于 jqGrid 的 CRUD on Local Data
	 */
	@At
	public void doNothing() {
	}

	@At
	public void logon(@Param("num") String number, @Param("pwd") String password, HttpSession session,
			HttpServletRequest request) {
		User logonUser = userService.userAuthenticate(number, password);
		session.setAttribute("logonUser", logonUser);
		List<Privilege> currentPrivileges = userService.getCurrentPrivileges(logonUser.getId());
		session.setAttribute("currPrivs", currentPrivileges);
		// 将该session相应的登录信息放入在线用户表中
		clientService.insert(session, request);
	}

	@At
	@Ok("forward:/main.html")
	@Fail("redirect:/index.html")
	public void main(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		if (cUser == null) {
			throw new RuntimeException("用户未登录!");
		}
	}

	@At
	@Ok("redirect:/index.html")
	public void logout(HttpSession session) {
		// 清除该用户的session
		session.invalidate();
	}

	@At
	public ResponseData getCurrentUserName(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		return systemService.getCurrentUserName(cUser);
	}

	@At
	@Ok("raw")
	public InputStream export(@Param("colNames") String[] colNames,
			@Param("rowDatas") LinkedHashMap<String, String>[] rowDatas) {
		InputStream is = systemService.genExcel(colNames, rowDatas);
		return is;
	}
}