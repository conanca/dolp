package gs.dolp.system.module;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@InjectName("userModule")
@At("/system/user")
public class UserModule {

	private UserService userService;

	@At
	@Filters
	public void login(@Param("num") String number, @Param("pwd") String password, HttpSession session) {
		User logonUser = userService.userAuthenticate(number, password);
		session.setAttribute("logonUser", logonUser);
	}

	@At
	@Filters
	@Ok("redirect:/login.html")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@At
	@Filters
	public ResponseData getCurrentUserName(HttpSession session) {
		AjaxResData reData = new AjaxResData();
		User cUser = (User) session.getAttribute("logonUser");
		if (cUser != null) {
			reData.setReturnData(cUser.getName());
			reData.setUserdata(new SystemMessage("登录成功!", null, null));
		}
		return reData;
	}

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("number") String number,
			@Param("name") String name) {
		return userService.getGridData(jqReq, number, name);
	}

	@At
	public String getNewUserNumber() {
		return userService.getNewUserNumber();
	}

	@At("/userNumberIsDuplicate/*")
	public boolean userNumberIsDuplicate(String userNumber) {
		return userService.userNumberIsDuplicate(userNumber);
	}

	@At
	public ResponseData save(@Param("..") User user) {
		AjaxResData reData = new AjaxResData();
		userService.save(user);
		reData.setUserdata(new SystemMessage("保存成功!", null, null));
		return reData;
	}

	@At
	public ResponseData deleteRow(@Param("id") String ids) {
		userService.deleteUsers(ids);
		AjaxResData reData = new AjaxResData();
		reData.setUserdata(new SystemMessage("删除成功!", null, null));
		return reData;
	}

	@At
	public ResponseData assignRole(@Param("userId") String userId, @Param("assignedRoleIds[]") String[] roleIds) {
		return userService.updateRole(userId, roleIds);
	}

	@At("/getCurrentRoleIDs/*")
	public int[] getCurrentRoleIDs(String userId) {
		return userService.getCurrentRoleIDs(userId);
	}
}
