package gs.dolp.system.module;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.system.domain.MenuEntity;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.MenuService;
import gs.dolp.system.service.SystemService;
import gs.dolp.system.service.UserService;

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

	@At
	public ResponseData getSystemName() {
		return systemService.getSystemName();
	}

	@At
	public void logon(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		User logonUser = userService.userAuthenticate(number, password);
		session.setAttribute("logonUser", logonUser);
	}

	@At
	@Ok("forward:/main.html")
	@Fail("forward:/login.html")
	public void main(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		if (cUser == null) {
			throw new RuntimeException("用户未登录!");
		}
	}

	@At
	@Ok("redirect:/login.html")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	public ResponseData getCurrentUserName(HttpSession session) {
		AjaxResData reData = new AjaxResData();
		User cUser = (User) session.getAttribute("logonUser");
		if (cUser != null) {
			reData.setReturnData(cUser.getName());
			reData.setUserdata(new SystemMessage("登录成功!", null, null));
		}
		return reData;
	}

	/**
	 * west布局的菜单显示
	 * @param nodeId
	 * @param nLeft
	 * @param nRight
	 * @param nLevel
	 * @param session
	 * @return
	 */
	@At
	public AdvancedJqgridResData<MenuEntity> dispMenu(@Param("nodeid") int nodeId, @Param("n_left") int nLeft,
			@Param("n_right") int nRight, @Param("n_level") int nLevel, HttpSession session) {
		User logonUser = (User) session.getAttribute("logonUser");
		return menuService.getGridData(nodeId, nLeft, nRight, nLevel, logonUser);
	}
}
