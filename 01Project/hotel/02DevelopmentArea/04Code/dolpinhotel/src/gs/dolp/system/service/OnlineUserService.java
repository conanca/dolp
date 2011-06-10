package gs.dolp.system.service;

import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.common.util.MVCHandler;
import gs.dolp.system.domain.OnlineUser;
import gs.dolp.system.domain.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;

public class OnlineUserService extends JqgridService<OnlineUser> {

	public OnlineUserService(Dao dao) {
		super(dao);
	}

	public void delete(HttpSession session) {
		this.clear(Cnd.where("SESSIONID", "=", session.getId()));
	}

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
	public AdvancedJqgridResData<OnlineUser> getGridData(JqgridReqData jqReq) {
		AdvancedJqgridResData<OnlineUser> jq = getAdvancedJqgridRespData(null, jqReq);
		return jq;
	}
}
