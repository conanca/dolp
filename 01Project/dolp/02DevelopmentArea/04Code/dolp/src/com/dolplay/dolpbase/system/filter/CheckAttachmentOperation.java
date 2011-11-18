package com.dolplay.dolpbase.system.filter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

import com.dolplay.dolpbase.common.domain.ExceptionAjaxResData;
import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.system.domain.User;

public class CheckAttachmentOperation implements ActionFilter {

	@Override
	public View match(ActionContext actionContext) {
		Object obj = actionContext.getRequest().getSession().getAttribute("logonUser");
		Long userId = ((User) obj).getId();
		Dao dao = DaoProvider.getDao();
		// 获取当前用户所有的未删的所发消息或所收消息的附件文件的池Id
		Sql sql = Sqls
				.create("SELECT IDINPOOL FROM SYSTEM_POOLFILE WHERE ID IN(SELECT POOLFILEID FROM SYSTEM_MESSAGE_POOLFILE WHERE MESSAGEID IN (SELECT ID FROM SYSTEM_MESSAGE WHERE SENDERUSERID = $userId OR (ID IN (SELECT MESSAGEID FROM SYSTEM_MESSAGE_RECEIVERUSER WHERE USERID = $userId ) AND (STATE = 1 OR STATE = 2))))");
		sql.vars().set("userId", userId);
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Long> ids = new ArrayList<Long>();
				while (rs.next()) {
					ids.add(rs.getLong("IDINPOOL"));
				}
				return ids;
			}
		});
		dao.execute(sql);
		@SuppressWarnings("unchecked")
		List<Long> ids = (List<Long>) sql.getResult();
		Long fileId = Long.parseLong(actionContext.getRequest().getParameter("id"));
		if (!ids.contains(fileId)) {
			UTF8JsonView jsonView = new UTF8JsonView(null);
			ExceptionAjaxResData excpAjaxResData = new ExceptionAjaxResData();
			excpAjaxResData.setSystemMessage(null, null, "用户无权操作该文件!");
			jsonView.setData(excpAjaxResData);
			return jsonView;
		} else {
			return null;
		}
	}
}
