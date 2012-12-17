package com.dolplay.dolpbase.system.module;

import java.io.InputStream;
import java.util.LinkedHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
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
	@RequiresGuest
	public void logon(@Param("num") String number, @Param("pwd") String password) {
		AuthenticationToken token = new UsernamePasswordToken(number, password);
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			User user = userService.fetchByNumber(number);
			Session session = subject.getSession();
			session.setAttribute("CurrentUser", user);
			long[] permissionIdArr = userService.getCurrentPermissionIdList(user.getId());
			session.setAttribute("CurrentPermission", permissionIdArr);

			// 将该session相应的登录信息放入在线用户表中
			clientService.insert(session);
		} catch (UnknownAccountException e) {
			throw new RuntimeException("用户不存在");
		} catch (AuthenticationException e) {
			throw new RuntimeException("验证错误");
		} catch (Exception e) {
			throw new RuntimeException("登录失败", e);
		}

	}

	@At
	@Ok("forward:/main.html")
	@Fail("redirect:/index.html")
	public void main() {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			throw new RuntimeException("用户未登录!");
		}
	}

	@At
	@Ok("redirect:/index.html")
	public void logout() {
		// 清除该用户的session
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}

	@At
	public ResponseData getCurrentUserName() {
		User cUser = (User) SecurityUtils.getSubject().getSession().getAttribute("CurrentUser");
		return systemService.getCurrentUserName(cUser);
	}

	@At
	@Ok("raw:stream")
	public InputStream export(@Param("colNames") String[] colNames,
			@Param("rowDatas") LinkedHashMap<String, String>[] rowDatas) {
		InputStream is = systemService.genExcel(colNames, rowDatas);
		return is;
	}
}