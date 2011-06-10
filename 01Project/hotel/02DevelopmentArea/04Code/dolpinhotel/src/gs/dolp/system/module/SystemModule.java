package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.system.domain.Privilege;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.MenuService;
import gs.dolp.system.service.OnlineUserService;
import gs.dolp.system.service.SystemService;
import gs.dolp.system.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("systemModule")
@Filters
public class SystemModule {

	private SystemService systemService;
	private UserService userService;
	private MenuService menuService;
	private OnlineUserService onlineUserService;

	@At
	public ResponseData getSystemName() {
		return systemService.getSystemName();
	}

	/**
	 * dummy existing url, 用于 jqGrid 的 CRUD on Local Data
	 */
	@At
	public void doNothing() {
	}

	@At
	public ResponseData initDatabase() {
		return systemService.initDatabase();
	}

	@At
	public void logon(@Param("num") String number, @Param("pwd") String password, HttpSession session,
			HttpServletRequest request) {
		User logonUser = userService.userAuthenticate(number, password);
		session.setAttribute("logonUser", logonUser);
		List<Privilege> currentPrivileges = userService.getCurrentPrivileges(logonUser.getId());
		session.setAttribute("currPrivs", currentPrivileges);
		// 将该用户放入在线用户表中
		onlineUserService.insert(session, request);
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
		session.invalidate();
		// 将该用户从在线用户表中删除
		onlineUserService.delete(session);
	}

	@At
	public ResponseData getCurrentUserName(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		return systemService.getCurrentUserName(cUser);
	}

	/**
	 * west布局的菜单显示
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@At
	public ResponseData dispMenu(@Param("nodeid") int nodeId, @Param("n_left") int nLeft, @Param("n_right") int nRight,
			@Param("n_level") int nLevel, HttpSession session) throws Exception {
		User logonUser = (User) session.getAttribute("logonUser");
		return menuService.getGridData(nodeId, nLeft, nRight, nLevel, logonUser);
	}

	@At
	@Ok("raw")
	public InputStream export(@Param("colNames") String[] colNames,
			@Param("rowDatas") LinkedHashMap<String, String>[] rowDatas) throws IOException {
		InputStream is = systemService.genExcel(colNames, rowDatas);
		return is;
	}

	@At
	public ResponseData changeUserPassword(HttpSession session, @Param("oldPassword") String oldPassword,
			@Param("newPassword") String newPassword) {
		User cUser = (User) session.getAttribute("logonUser");
		return systemService.changeUserPassword(cUser, oldPassword, newPassword);
	}

}
