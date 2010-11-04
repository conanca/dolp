package gs.dolp.system.module;

import gs.dolp.jqgrid.domain.JqgridAdvancedData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("userModule")
@At("/system/user")
//@Filters( { @By(type = CheckSession.class, args = { "logonUser", "/login.jsp" }) })
public class UserModule {

	private UserService userService;

	@At
	public JqgridAdvancedData<User> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return userService.getGridData(page, rows, sidx, sord);
	}

	@At
	@Ok("redirect:/main.jsp")
	@Fail("redirect:/error/login_fail.jsp")
	@Filters
	public void login(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		session.setAttribute("logonUser", userService.userAuthenticate(number, password));
	}

	@At
	@Ok("redirect:/login.jsp")
	@Filters
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	public String getCurrentUserName(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		return cUser.getName();
	}

	@At
	public void save(@Param("..") User user) {
		if (user.getId() == 0) {
			userService.dao().insert(user);
		} else {
			userService.dao().update(user);
		}
	}

	@At
	public void deleteRow(@Param("id") String ids) {
		userService.deleteUsers(ids);
	}

	@At
	public void assignRole(@Param("userId") String userId, @Param("assignedRoleIds[]") String[] roleIds) {
		userService.updateRole(userId, roleIds);
	}

	@At("/getCurrentRoleIDs/*")
	public int[] getCurrentRoleIDs(String userId) {
		return userService.getCurrentRoleIDs(userId);
	}
}
