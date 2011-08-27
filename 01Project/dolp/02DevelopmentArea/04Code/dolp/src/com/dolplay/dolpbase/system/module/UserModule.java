package com.dolplay.dolpbase.system.module;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.User;
import com.dolplay.dolpbase.system.service.UserService;

@IocBean
@At("/system/user")
public class UserModule {

	@Inject
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
	public ResponseData getCurrentRoleIDs(String userId) {
		return userService.getCurrentRoleIdArr(userId);
	}

	@At
	public ResponseData assignPost(@Param("userId") String userId, @Param("orgId") String orgId,
			@Param("selectedPostIds[]") String[] postIds) {
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

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public ResponseData importUsers(@Param("userImportFile") File f) {
		return userService.importUsers(f);
	}
}