package gs.dolp.system.module;

import gs.dolp.jqgrid.JqgridData;
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
@Fail("json")
//@Filters( { @By(type = CheckSession.class, args = { "logonUser", "/login.jsp" }) })
public class UserModule {

	private UserService userService;

	@At
	@Ok("json")
	public JqgridData<User> getGridData(@Param("page") String page, @Param("rows") String rows,
			@Param("sidx") String sidx, @Param("sord") String sord) {
		return userService.getGridData(page, rows, sidx, sord);
	}

	@At
	@Ok("redirect:/main.jsp")
	@Fail("redirect:/1.jsp")
	@Filters
	public void login(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		session.setAttribute("logonUser", userService.userAuthenticate(number, password));
	}

	@At
	@Ok("redirect:/login.jsp")
	@Fail("json")
	@Filters
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	@Fail("json")
	public User save(@Param("..") User user) {
		if (user.getId() == 0) {
			userService.dao().insert(user);
		} else {
			userService.dao().update(user);
		}
		return user;
	}

	@At
	@Fail("json")
	public void deleteRow(@Param("id") String ids) {
		userService.deleteUsers(ids);
	}

	@At
	@Fail("json")
	public void addRole(@Param("userId") String userId, @Param("roleIds") String roleIds) {
		userService.updateRole(userId, roleIds);
	}
}
