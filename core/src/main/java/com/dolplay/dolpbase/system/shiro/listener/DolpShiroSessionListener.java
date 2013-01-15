package com.dolplay.dolpbase.system.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.nutz.dao.Cnd;

import com.dolplay.dolpbase.common.util.DolpWebs;

public class DolpShiroSessionListener implements SessionListener {

	@Override
	public void onStart(Session session) {
		//		Client client = new Client();
		//		User cUser = (User) session.getAttribute("CurrentUser");
		//		client.setUserId(cUser.getId());
		//		client.setSessionId(session.getId().toString());
		//		client.setLogonTime(new Timestamp((new Date()).getTime()));
		//		client.setIpAddr(session.getHost());
		//		DaoProvider.getDao().insert(client);
	}

	@Override
	public void onStop(Session session) {
		DolpWebs.dao().clear("SYSTEM_CLIENT", Cnd.where("SESSIONID", "=", session.getId()));
	}

	@Override
	public void onExpiration(Session session) {
		onStop(session);
	}

}
