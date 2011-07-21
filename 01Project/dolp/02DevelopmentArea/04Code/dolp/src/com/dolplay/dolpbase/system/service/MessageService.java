package com.dolplay.dolpbase.system.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.aop.Aop;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.system.domain.Message;
import com.dolplay.dolpbase.system.domain.User;

public class MessageService extends DolpBaseService<Message> {

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
	public AjaxResData saveMessage(final int messageId, User senderUser, String[] receiverUsers, String title,
			String content) {
		AjaxResData respData = new AjaxResData();
		dao().clearLinks(fetch(messageId), "receivers");
		final Message message = getNewMessage(senderUser, receiverUsers, title, content);
		message.setState(0);
		Trans.exec(new Atom() {
			public void run() {
				// 如果指定messageId,则只是更新该消息的内容；否则新增记录
				if (messageId > 0) {
					message.setId(messageId);
					dao().update(message);
				} else {
					dao().insert(message);
				}
				dao().insertRelation(message, "receivers");
			}
		});
		respData.setSystemMessage("保存草稿成功!", null, null);
		return respData;
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
	public AjaxResData sendMessage(final int messageId, User senderUser, String[] receiverUsers, String title,
			String content) {
		AjaxResData respData = new AjaxResData();
		dao().clearLinks(fetch(messageId), "receivers");
		final Message message = getNewMessage(senderUser, receiverUsers, title, content);
		message.setState(1);
		Trans.exec(new Atom() {
			public void run() {
				// 如果指定messageId,则只是更新该消息的内容；否则新增记录
				if (messageId > 0) {
					message.setId(messageId);
					dao().update(message);
				} else {
					dao().insert(message);
				}
				dao().insertRelation(message, "receivers");
			}
		});
		respData.setSystemMessage("发送成功!", null, null);
		return respData;
	}

	/**
	 * 删除已接收消息
	 * @param messageId
	 * @param user
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteReceivedMessage(int messageId, User user) {
		AjaxResData respData = new AjaxResData();
		if (messageId <= 0) {
			respData.setSystemMessage(null, "未选择消息!", null);
			return respData;
		}
		dao().clear("SYSTEM_MESSAGE_RECEIVERUSER",
				Cnd.where("USERID", "=", user.getId()).and("MESSAGEID", "=", messageId));
		respData.setSystemMessage("删除成功!", null, null);
		return respData;
	}

	/**
	 * 删除已发送消息
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteSentMessage(int messageId) {
		AjaxResData respData = new AjaxResData();
		if (messageId <= 0) {
			respData.setSystemMessage(null, "未选择消息!", null);
			return respData;
		}
		Message message = fetch(messageId);
		message.setState(2);
		dao().update(message);
		respData.setSystemMessage("删除成功!", null, null);
		return respData;
	}

	/**
	 * 删除草稿消息
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteDraftMessage(int messageId) {
		AjaxResData respData = new AjaxResData();
		if (messageId <= 0) {
			respData.setSystemMessage(null, "未选择消息!", null);
			return respData;
		}
		delete(Long.valueOf(messageId));
		respData.setSystemMessage("删除成功!", null, null);
		return respData;
	}

	/**
	 * 列表显示收到的消息
	 * @param jqReq
	 * @param readerUser
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getReceivedMessageGridData(JqgridReqData jqReq, User readerUser)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Condition cnd = null;
		List<Record> recList = dao().query("SYSTEM_MESSAGE_RECEIVERUSER", Cnd.where("USERID", "=", readerUser.getId()),
				null);
		if (recList != null && recList.size() > 0) {
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
		List<Message> messages = jq.getRows();
		for (Message message : messages) {
			dao().fetchLinks(message, "sender");
		}
		return jq;
	}

	/**
	 * 列表显示发送的消息
	 * @param jqReq
	 * @param senderUser
	 * @param state
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getSentMessageGridData(JqgridReqData jqReq, User senderUser, int state)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		Condition cnd = Cnd.where("SENDERUSERID", "=", senderUser.getId()).and("STATE", "=", state);
		AdvancedJqgridResData<Message> jq = getAdvancedJqgridRespData(cnd, jqReq);
		List<Message> messages = jq.getRows();
		for (Message message : messages) {
			dao().fetchLinks(message, "sender");
		}
		return jq;
	}

	/**
	 * 根据指定消息Id，获取收件人的number，以“,”分隔
	 * @param messageId
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData getReceiverUserNum(int messageId) {
		AjaxResData respData = new AjaxResData();
		Message message = dao().fetchLinks(fetch(messageId), "receivers");
		List<User> receivers = message.getReceivers();
		StringBuilder sb = new StringBuilder();
		for (User user : receivers) {
			sb.append(user.getNumber());
			sb.append(",");
		}
		String receiverUserNums = sb.toString();
		if (Strings.isEmpty(receiverUserNums)) {
			receiverUserNums = " ";
		}
		respData.setReturnData(receiverUserNums);
		return respData;
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