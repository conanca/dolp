package gs.dolp.system.listener;

import gs.dolp.common.util.DaoHandler;
import gs.dolp.common.util.DolpSessionContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.nutz.dao.Cnd;

public class DolpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		// 将新建的session放入DolpSessionContext中
		DolpSessionContext.AddSession(event.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// 将销毁的session从DolpSessionContext中删除
		HttpSession session = event.getSession();
		DolpSessionContext.DelSession(session);
		// 将相应的记录从在线用户表中删除
		DaoHandler.getDao().clear("SYSTEM_CLIENT", Cnd.where("SESSIONID", "=", session.getId()));
	}

}
