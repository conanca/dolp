package gs.dolp.system.module;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.UserService;

import java.sql.SQLException;
import java.util.Map;

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
		reData.setSystemMessage(new SystemMessage("保存成功!", null, null));
		return reData;
	}

	@At
	public ResponseData deleteRow(@Param("id") String ids) {
		userService.deleteUsers(ids);
		AjaxResData reData = new AjaxResData();
		reData.setSystemMessage(new SystemMessage("删除成功!", null, null));
		return reData;
	}

	@At
	public ResponseData assignRole(@Param("userId") String userId, @Param("assignedRoleIds[]") String[] roleIds) {
		return userService.updateRole(userId, roleIds);
	}

	@At("/getCurrentRoleIDs/*")
	public int[] getCurrentRoleIDs(String userId) throws Exception {
		return userService.getCurrentRoleIDs(userId);
	}

	@At
	public ResponseData assignPost(@Param("userId") String userId, @Param("orgId") String orgId,
			@Param("selectedPostIds[]") String[] postIds) throws SQLException {
		return userService.updatePost(userId, orgId, postIds);
	}

	@At
	public Map<String, String> getUserMap(@Param("userIds") String userIds) {
		return userService.getUserMap(userIds);
	}
}
