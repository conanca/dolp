package gs.dolp.system.module;

import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.ResponseSysMsgData;
import gs.dolp.common.jqgrid.domain.SystemMessage;
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
	@Fail("jsonx")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("number") String number,
			@Param("name") String name) {
		return userService.getGridData(jqReq, number, name);
	}

	@At
	@Ok("redirect:/main.jsp")
	@Fail("redirect:/login.jsp")
	@Filters
	public void login(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		User logonUser = userService.userAuthenticate(number, password);
		session.setAttribute("logonUser", logonUser);
	}

	@At
	@Ok("redirect:/login.jsp")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	public String getCurrentUserName(HttpSession session) {
		User cUser = (User) session.getAttribute("logonUser");
		if (cUser != null) {
			return cUser.getName();
		} else {
			throw new RuntimeException("Please Logon!");
		}
	}

	@At
	public ResponseData save(@Param("..") User user) {
		if (user.getId() == 0) {
			userService.dao().insert(user);
		} else {
			userService.dao().update(user);
		}
		ResponseSysMsgData reData = new ResponseSysMsgData();
		reData.setUserdata(new SystemMessage("保存成功!", null, null));
		return reData;
	}

	@At
	public ResponseData deleteRow(@Param("id") String ids) {
		userService.deleteUsers(ids);
		ResponseSysMsgData reData = new ResponseSysMsgData();
		reData.setUserdata(new SystemMessage("删除成功!", null, null));
		return reData;
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
