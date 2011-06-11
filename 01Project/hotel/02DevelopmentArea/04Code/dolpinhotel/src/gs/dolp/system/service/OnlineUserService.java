package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.jqgrid.AdvancedJqgridResData;
import gs.dolp.common.domain.jqgrid.JqgridReqData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.common.util.DolpSessionContext;
import gs.dolp.common.util.MVCHandler;
import gs.dolp.system.domain.OnlineUser;
import gs.dolp.system.domain.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class OnlineUserService extends DolpBaseService<OnlineUser> {

	public OnlineUserService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public void clear(HttpSession session) {
		clear(Cnd.where("SESSIONID", "=", session.getId()));
	}

	@Aop(value = "log")
	public void insert(HttpSession session, HttpServletRequest request) {
		OnlineUser onlineUser = new OnlineUser();
		User cUser = (User) session.getAttribute("logonUser");
		onlineUser.setUserId(cUser.getId());
		onlineUser.setSessionId(session.getId());
		onlineUser.setLogonTime(new Timestamp((new Date()).getTime()));
		onlineUser.setIpAddr(MVCHandler.getIpAddr(request));
		onlineUser.setBrowser(MVCHandler.getBrowser(request));
		dao().insert(onlineUser);
	}

	@Aop(value = "log")
	public AjaxResData kickOff(String[] sessionIds) {
		AjaxResData resData = new AjaxResData();
		if (sessionIds != null && sessionIds.length > 0) {
			for (String sessionId : sessionIds) {
				DolpSessionContext.getSession(sessionId).invalidate();
			}
			resData.setSystemMessage("已踢出用户！", null, null);
		} else {
			resData.setSystemMessage(null, "未踢出任何用户！", null);
		}
		return resData;
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<OnlineUser> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<OnlineUser> jq = getAdvancedJqgridRespData(null, jqReq);
		List<OnlineUser> onlineUsers = jq.getRows();
		for (OnlineUser onlineUser : onlineUsers) {
			dao().fetchLinks(onlineUser, "user");
		}
		return jq;
	}
}
