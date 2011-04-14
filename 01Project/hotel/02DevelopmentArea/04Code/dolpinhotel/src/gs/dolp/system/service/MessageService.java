package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.Message;
import gs.dolp.system.domain.User;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;

public class MessageService extends JqgridService<Message> {

	public MessageService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AjaxResData saveMessage(User senderUser, int[] receiverUserIds, String title, String content) {
		AjaxResData reData = new AjaxResData();
		Message message = getNewMessage(senderUser, receiverUserIds, title, content);
		message.setState(0);
		dao().insertLinks(message, "receivers");
		reData.setUserdata(new SystemMessage("!", null, null));
		return reData;
	}

	@Aop(value = "log")
	public AjaxResData sendMessage(User senderUser, int[] receiverUserIds, String title, String content) {
		AjaxResData reData = new AjaxResData();
		Message message = getNewMessage(senderUser, receiverUserIds, title, content);
		message.setState(1);
		dao().insertLinks(message, "receivers");
		reData.setUserdata(new SystemMessage("!", null, null));
		return reData;
	}

	@Aop(value = "log")
	public AjaxResData sendDraftMessage(String messageId) {
		AjaxResData reData = new AjaxResData();
		if (Strings.isEmpty(messageId)) {
			reData.setUserdata(new SystemMessage(null, "!", null));
			return reData;
		}
		int messId = Integer.valueOf(messageId);
		Message message = fetch(messId);
		message.setState(1);
		dao().update(message);
		reData.setUserdata(new SystemMessage("!", null, null));
		return reData;
	}

	@Aop(value = "log")
	private Message getNewMessage(User senderUser, int[] receiverUserIds, String title, String content) {
		Message message = new Message();
		message.setSender(senderUser);
		List<User> receivers = new ArrayList<User>();
		for (int id : receiverUserIds) {
			User user = new User();
			user.setId(id);
			receivers.add(user);
		}
		message.setReceivers(receivers);
		message.setTitle(title);
		message.setContent(content);
		return message;
	}

	@Aop(value = "log")
	public AjaxResData readMessade(User readerUser, String messageId) {
		AjaxResData reData = new AjaxResData();
		if (Strings.isEmpty(messageId)) {
			reData.setUserdata(new SystemMessage(null, "!", null));
			return reData;
		}
		dao().update("SYSTEM_MESSAGE_RECEIVERUSER", Chain.make("ISREAD", 1),
				Cnd.where("MESSAGEID", "=", messageId).and("USERID", "=", readerUser.getId()));
		reData.setUserdata(new SystemMessage("!", null, null));
		return reData;
	}

	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getReceivedMessageGridData(JqgridReqData jqReq, User readerUser) {
		List<Record> recList = dao().query("SYSTEM_MESSAGE_RECEIVERUSER", Cnd.where("USERID", "=", readerUser.getId()),
				null);
		Condition cnd = null;
		if (recList.size() > 0) {
			int[] messageIds = new int[recList.size()];
			int i = 0;
			for (Record rec : recList) {
				messageIds[i] = rec.getInt("MESSAGEID");
				i++;
			}
			cnd = Cnd.where("ID", "IN", messageIds);
		} else {
			cnd = Cnd.where("1", "=", "0");
		}
		AdvancedJqgridResData<Message> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	public AdvancedJqgridResData<Message> getSentMessageGridData(JqgridReqData jqReq, User senderUser, int state) {
		// TODO 查找Condition cnd = null并重构
		Condition cnd = null;
		cnd = Cnd.where("SENDERUSERID", "IN", senderUser.getId()).and("STATE", "=", state);
		AdvancedJqgridResData<Message> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	@Aop(value = "log")
	public int countUnreadMessage(User readerUser) {
		int count = 0;
		count = dao().count("SYSTEM_MESSAGE_RECEIVERUSER",
				Cnd.where("USERID", "=", readerUser.getId()).and("ISREAD", "=", 0));
		return count;
	}
}
