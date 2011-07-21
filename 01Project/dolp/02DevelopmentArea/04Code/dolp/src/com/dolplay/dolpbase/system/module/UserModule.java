package com.dolplay.dolpbase.system.module;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.User;
import com.dolplay.dolpbase.system.service.UserService;

@InjectName("userModule")
@At("/system/user")
public class UserModule {

	private UserService userService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("number") String number,
			@Param("name") String name) {
		return userService.getGridData(jqReq, number, name);
	}

	@At
	public ResponseData getNewUserNumber() {
		return userService.getNewUserNumber();
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") User user) {
		return userService.CUDUser(oper, ids, user);
	}

	@At
	public ResponseData assignRole(@Param("userId") String userId, @Param("assignedRoleIds[]") String[] roleIds) {
		return userService.updateRole(userId, roleIds);
	}

	@At("/getCurrentRoleIDs/*")
	public ResponseData getCurrentRoleIDs(String userId) throws Exception {
		return userService.getCurrentRoleIdArr(userId);
	}

	@At
	public ResponseData assignPost(@Param("userId") String userId, @Param("orgId") String orgId,
			@Param("selectedPostIds[]") String[] postIds) throws SQLException {
		return userService.updatePost(userId, orgId, postIds);
	}

	@At
	public ResponseData changeCurrentUserPassword(HttpSession session, @Param("oldPassword") String oldPassword,
			@Param("newPassword") String newPassword) {
		User cUser = (User) session.getAttribute("logonUser");
		return userService.changePasswordForCurrentUser(cUser, oldPassword, newPassword);
	}

	@At
	public ResponseData changeUserPassword(@Param("userId") int userId, @Param("newPassword") String newPassword) {
		return userService.changePasswordForAUser(userId, newPassword);
	}
}