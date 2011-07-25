package com.dolplay.dolpbase.system.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.DolpSessionContext;
import com.dolplay.dolpbase.common.util.MVCHandler;
import com.dolplay.dolpbase.system.domain.Client;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" })
public class ClientService extends DolpBaseService<Client> {

	public ClientService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public void clear(HttpSession session) {
		clear(Cnd.where("SESSIONID", "=", session.getId()));
	}

	@Aop(value = "log")
	public void insert(HttpSession session, HttpServletRequest request) {
		Client client = new Client();
		User cUser = (User) session.getAttribute("logonUser");
		client.setUserId(cUser.getId());
		client.setSessionId(session.getId());
		client.setLogonTime(new Timestamp((new Date()).getTime()));
		client.setIpAddr(MVCHandler.getIpAddr(request));
		client.setBrowser(MVCHandler.getBrowser(request));
		dao().insert(client);
	}

	@Aop(value = "log")
	public AjaxResData kickOff(String[] sessionIds) {
		AjaxResData resData = new AjaxResData();
		if (sessionIds != null && sessionIds.length > 0) {
			for (String sessionId : sessionIds) {
				DolpSessionContext.getSession(sessionId).invalidate();
			}
			resData.setSystemMessage("已踢出用户!", null, null);
		} else {
			resData.setSystemMessage(null, "未踢出任何用户!", null);
		}
		return resData;
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Client> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<Client> jq = getAdvancedJqgridRespData(null, jqReq);
		List<Client> clients = jq.getRows();
		for (Client client : clients) {
			dao().fetchLinks(client, "user");
		}
		return jq;
	}
}