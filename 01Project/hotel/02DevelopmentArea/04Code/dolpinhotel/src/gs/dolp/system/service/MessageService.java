package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.common.jqgrid.service.JqgridService;
import gs.dolp.system.domain.Message;
import gs.dolp.system.domain.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.aop.Aop;

public class MessageService extends JqgridService<Message> {

	public MessageService(Dao dao) {
		super(dao);
	}

	/**
	 * 按指定发件人、收件人、标题及内容新建消息，并将消息保存为草稿
	 * @param senderUser
	 * @param receiverUsers
	 * @param title
	 * @param content
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData saveMessage(int messageId, User senderUser, String[] receiverUsers, String title, String content) {
		AjaxResData reData = new AjaxResData();
		dao().clearLinks(fetch(messageId), "receivers");
		Message message = getNewMessage(senderUser, receiverUsers, title, content);
		message.setState(0);
		// 如果指定messageId,则只是更新该消息的内容；否则新增记录
		if (messageId > 0) {
			message.setId(messageId);
			dao().update(message);
		} else {
			dao().insert(message);
		}
		dao().insertRelation(message, "receivers");
		reData.setSystemMessage(new SystemMessage("保存草稿成功!", null, null));
		return reData;
	}

	/**
	 * 按指定发件人、收件人、标题及内容新建消息，并发送消息
	 * @param senderUser
	 * @param receiverUsers
	 * @param title
	 * @param content
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData sendMessage(int messageId, User senderUser, String[] receiverUsers, String title, String content) {
		AjaxResData reData = new AjaxResData();
		dao().clearLinks(fetch(messageId), "receivers");
		Message message = getNewMessage(senderUser, receiverUsers, title, content);
		message.setState(1);
		// 如果指定messageId,则只是更新该消息的内容；否则新增记录
		if (messageId > 0) {
			message.setId(messageId);
			dao().update(message);
		} else {
			dao().insert(message);
		}
		dao().insertRelation(message, "receivers");
		reData.setSystemMessage(new SystemMessage("发送成功!", null, null));
		return reData;
	}

	//	/**
	//	 * 将指定Id的草稿消息发送出去
	//	 * @param messageId
	//	 * @return
	//	 */
	//	@Aop(value = "log")
	//	public AjaxResData sendDraftMessage(String messageId) {
	//		AjaxResData reData = new AjaxResData();
	//		if (Strings.isEmpty(messageId)) {
	//			reData.setSystemMessage(new SystemMessage(null, "未选择消息!", null));
	//			return reData;
	//		}
	//		int messId = Integer.valueOf(messageId);
	//		Message message = fetch(messId);
	//		message.setState(1);
	//		dao().update(message);
	//		reData.setSystemMessage(new SystemMessage("发送成功!", null, null));
	//		return reData;
	//	}

	/**
	 * 删除已接收消息
	 * @param messageId
	 * @param user
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteReceivedMessage(int messageId, User user) {
		AjaxResData reData = new AjaxResData();
		if (messageId <= 0) {
			reData.setSystemMessage(new SystemMessage(null, "未选择消息!", null));
			return reData;
		}
		dao().clear("SYSTEM_MESSAGE_RECEIVERUSER",
				Cnd.where("USERID", "=", user.getId()).and("MESSAGEID", "=", messageId));
		reData.setSystemMessage(new SystemMessage("删除成功!", null, null));
		return reData;
	}

	/**
	 * 删除已发送消息
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteSentMessage(int messageId) {
		AjaxResData reData = new AjaxResData();
		if (messageId <= 0) {
			reData.setSystemMessage(new SystemMessage(null, "未选择消息!", null));
			return reData;
		}
		Message message = fetch(messageId);
		message.setState(2);
		dao().update(message);
		reData.setSystemMessage(new SystemMessage("删除成功!", null, null));
		return reData;
	}

	/**
	 * 删除草稿消息
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteDraftMessage(int messageId) {
		AjaxResData reData = new AjaxResData();
		if (messageId <= 0) {
			reData.setSystemMessage(new SystemMessage(null, "未选择消息!", null));
			return reData;
		}
		delete(Long.valueOf(messageId));
		reData.setSystemMessage(new SystemMessage("删除成功!", null, null));
		return reData;
	}

	/**
	 * 读消息(修改已读标志)
	 * @param readerUser
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData readMessade(User readerUser, int messageId) {
		AjaxResData reData = new AjaxResData();
		if (messageId <= 0) {
			reData.setSystemMessage(new SystemMessage(null, "未选择消息!", null));
			return reData;
		}
		dao().update("SYSTEM_MESSAGE_RECEIVERUSER", Chain.make("ISREAD", 1),
				Cnd.where("MESSAGEID", "=", messageId).and("USERID", "=", readerUser.getId()));
		//reData.setSystemMessage(new SystemMessage("读取成功!", null, null));
		return reData;
	}

	/**
	 * 列表显示收到的消息
	 * @param jqReq
	 * @param readerUser
	 * @return
	 */
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
			cnd = Cnd.where("ID", "IN", messageIds).and("STATE", "<>", 0);
		} else {
			cnd = Cnd.where("1", "=", "0");
		}
		AdvancedJqgridResData<Message> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	/**
	 * 列表显示发送的消息
	 * @param jqReq
	 * @param senderUser
	 * @param state
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getSentMessageGridData(JqgridReqData jqReq, User senderUser, int state) {
		// TODO 查找Condition cnd = null并重构
		Condition cnd = null;
		cnd = Cnd.where("SENDERUSERID", "=", senderUser.getId()).and("STATE", "=", state);
		AdvancedJqgridResData<Message> jq = getAdvancedJqgridRespData(cnd, jqReq);
		return jq;
	}

	/**
	 * 根据指定消息Id，获取收件人的number，以“,”分隔
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData getReceiverUserNum(int messageId) {
		AjaxResData reData = new AjaxResData();
		Message message = dao().fetchLinks(fetch(messageId), "receivers");
		List<User> receivers = message.getReceivers();
		StringBuilder sb = new StringBuilder();
		for (User user : receivers) {
			sb.append(user.getNumber());
			sb.append(",");
		}
		String receiverUserNums = sb.toString();
		reData.setReturnData(receiverUserNums);
		return reData;
	}

	/**
	 * 显示未读消息数
	 * @param readerUser
	 * @return
	 */
	@Aop(value = "log")
	public int countUnreadMessage(User readerUser) {
		int count = 0;
		count = dao().count("SYSTEM_MESSAGE_RECEIVERUSER",
				Cnd.where("USERID", "=", readerUser.getId()).and("ISREAD", "=", 0));
		return count;
	}

	/**
	 * 将收件人列表信息整理成其id的数组
	 * @param userNumArr
	 * @return
	 */
	private Set<Integer> numArr2IdArr(String[] userNumArr) {
		// 将形如 AA<1234>这样的元素转换成1234
		int length = userNumArr.length;
		Set<Integer> idArr = new HashSet<Integer>();
		for (String receiverUser : userNumArr) {
			receiverUser = receiverUser.trim();
			String userNo = null;
			if (receiverUser.contains("<") && receiverUser.contains(">")) {
				userNo = receiverUser.substring(receiverUser.indexOf("<") + 1, receiverUser.indexOf(">"));
			} else {
				userNo = receiverUser;
			}
			User user = dao().fetch(User.class, Cnd.where("NUMBER", "=", userNo));
			if (user != null) {
				idArr.add(user.getId());
			}
		}
		return idArr;
	}

	/**
	 * 按指定发件人、收件人、标题及内容新建消息
	 * @param senderUser
	 * @param receiverUsers
	 * @param title
	 * @param content
	 * @return
	 */
	@Aop(value = "log")
	private Message getNewMessage(User senderUser, String[] receiverUsers, String title, String content) {
		Set<Integer> idArr = numArr2IdArr(receiverUsers);
		Message message = new Message();
		message.setSenderUserId(senderUser.getId());
		List<User> receivers = new ArrayList<User>();
		for (int id : idArr) {
			User user = new User();
			user.setId(id);
			receivers.add(user);
		}
		message.setReceivers(receivers);
		message.setTitle(title);
		message.setContent(content);
		Timestamp dateTime = new Timestamp((new Date()).getTime());
		message.setDate(dateTime);
		return message;
	}
}
