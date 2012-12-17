package com.dolplay.dolpbase.system.filter;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.system.domain.User;

/**
 * 对消息附件文件的操作的权限过滤器
 * @author Administrator
 */
public class CheckAttachmentOperation implements ActionFilter {

	@Override
	public View match(ActionContext actionContext) {
		// 获取当前用户所有的未删的所发消息或所收消息的附件文件的池Id，若该附件文件不在其中，则提示用户无权限
		Long fileIdInPool = Long.parseLong(actionContext.getRequest().getParameter("id"));
		Object obj = actionContext.getRequest().getSession().getAttribute("CurrentUser");
		Long userId = ((User) obj).getId();
		Sql sql = Sqls
				.create("SELECT @fileIdInPool IN (SELECT IDINPOOL FROM SYSTEM_POOLFILE WHERE ID IN(SELECT POOLFILEID FROM SYSTEM_MESSAGE_POOLFILE WHERE MESSAGEID IN (SELECT ID FROM SYSTEM_MESSAGE WHERE SENDERUSERID = @userId OR (ID IN (SELECT MESSAGEID FROM SYSTEM_MESSAGE_RECEIVERUSER WHERE USERID = @userId ) AND (STATE = 1 OR STATE = 2)))))");
		sql.params().set("fileIdInPool", fileIdInPool);
		sql.params().set("userId", userId);
		sql.setCallback(Sqls.callback.bool());
		DaoProvider.getDao().execute(sql);
		boolean isIn = sql.getBoolean();
		if (isIn) {
			return null;
		} else {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			jsonView.setData(AjaxResData.getInstanceErrorNotice("用户无权操作该文件!"));
			return jsonView;
		}
	}
}
