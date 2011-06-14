package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.system.service.UserService;

import java.sql.SQLException;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

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
	public ResponseData editRow(@Param("oper") String oper, @Param("id") String id, @Param("number") String number,
			@Param("name") String name, @Param("gender") String gender, @Param("age") int age,
			@Param("birthday") String birthday, @Param("phone") String phone) {
		return userService.CUDUser(oper, id, number, name, gender, age, birthday, phone);
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
}
