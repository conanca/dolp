package com.dolplay.dolpbase.system.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Client;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" })
public class ClientService extends DolpBaseService<Client> {

	public ClientService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public void insert(Session session) {
		Client client = new Client();
		User cUser = (User) session.getAttribute("logonUser");
		client.setUserId(cUser.getId());
		client.setSessionId(session.getId().toString());
		client.setLogonTime(new Timestamp((new Date()).getTime()));
		client.setIpAddr(session.getHost());
		//client.setUserAgent(MVCHandler.getUserAgent(request));
		dao().insert(client);
	}

	@Aop(value = "log")
	public AjaxResData kickOff(String[] sessionIds) {
		AjaxResData resData = new AjaxResData();
		if (sessionIds != null && sessionIds.length > 0) {
			for (String sessionId : sessionIds) {
				//DolpSessionContext.getSession(sessionId).invalidate();
				DefaultSessionKey sessionKey = new DefaultSessionKey(sessionId);
				Session session = SecurityUtils.getSecurityManager().getSession(sessionKey);
				session.stop();
			}
			resData.setInfo("已踢出用户!");
		} else {
			resData.setNotice("未踢出任何用户!");
		}
		return resData;
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Client> getGridData(JqgridReqData jqReq, Boolean isSearch, Client clientSearch,
			String userName) {
		Cnd cnd = null;
		if (null != isSearch && isSearch && null != clientSearch) {
			cnd = Cnd.where("1", "=", 1);
			String ipAddr = clientSearch.getIpAddr();
			if (!Strings.isEmpty(ipAddr)) {
				cnd.and("IPADDR", "LIKE", StringUtils.quote(ipAddr, '%'));
			}
			String userAgent = clientSearch.getUserAgent();
			if (!Strings.isEmpty(userAgent)) {
				cnd.and("USERAGENT", "LIKE", StringUtils.quote(userAgent, '%'));
			}
			Timestamp logonTime = clientSearch.getLogonTime();
			if (null != logonTime) {
				cnd.and("LOGONTIME", "=", logonTime);
			}
			if (!Strings.isEmpty(userName)) {
				User user = dao().fetch(User.class, Cnd.where("name", "LIKE", StringUtils.quote(userName, '%')));
				if (null != user) {
					cnd.and("USERID", "=", user.getId());
				} else {
					cnd.and("1", "=", 0);
				}
			}
		}
		AdvancedJqgridResData<Client> jq = getAdvancedJqgridRespData(cnd, jqReq);
		List<Client> clients = jq.getRows();
		for (Client client : clients) {
			dao().fetchLinks(client, "user");
		}
		return jq;
	}
}