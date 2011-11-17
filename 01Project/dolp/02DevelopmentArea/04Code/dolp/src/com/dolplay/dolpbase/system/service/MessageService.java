package com.dolplay.dolpbase.system.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.jqgrid.AdvancedJqgridResData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.system.domain.Message;
import com.dolplay.dolpbase.system.domain.PoolFile;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" }, fields = { "prop" })
public class MessageService extends DolpBaseService<Message> {

	public MessageService(Dao dao) {
		super(dao);
	}

	/**
	 * 按指定发件人、收件人、标题、内容及附件新建消息，并将消息保存为草稿或直接发送消息
	 * @param senderUser
	 * @param receiverUsers
	 * @param title
	 * @param content
	 * @return
	 * @throws IOException 
	 */
	@Aop(value = "log")
	public AjaxResData saveOrSendMessage(int state, final Long messageId, User senderUser, String[] receiverUsers,
			String title, String content, String[] attachments) throws IOException {
		// 清除该message的一切相关
		clearMessageRelation(messageId);
		// 获取附件文件列表
		final List<PoolFile> poolFiles = getAttachments(attachments);
		// 获取message对象
		final Message message = getNewMessage(senderUser, receiverUsers, title, content);
		// 设置状态为已保存/已发送
		message.setState(state);
		// 插入附件文件信息，插入/更新消息，插入(收件人、附件文件)关系数据
		Trans.exec(new Atom() {
			public void run() {
				dao().insert(poolFiles);
				message.setAttachments(poolFiles);
				// 如果指定messageId,则只是更新该消息的内容；否则新增记录
				if (messageId != null) {
					message.setId(messageId);
					dao().update(message);
				} else {
					dao().insert(message);
				}
				// 插入相应的中间表数据
				dao().insertRelation(message, "receivers");
				dao().insertRelation(message, "attachments");
			}
		});
		String successMessage = null;
		switch (state) {
		case 0:
			successMessage = "保存草稿成功!";
			break;
		case 1:
			successMessage = "发送成功!";
			break;
		default:
			successMessage = "未知操作!";
		}
		AjaxResData respData = new AjaxResData();
		respData.setSystemMessage(successMessage, null, null);
		return respData;
	}

	/**
	 * 删除已接收消息
	 * @param messageId
	 * @param user
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData deleteReceivedMessage(Long messageId, User user) {
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
	public AjaxResData deleteSentMessage(Long messageId) {
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
	public AjaxResData deleteDraftMessage(Long messageId) {
		AjaxResData respData = new AjaxResData();
		if (messageId <= 0) {
			respData.setSystemMessage(null, "未选择消息!", null);
			return respData;
		}
		delete(messageId);
		// 清除该message的一切相关
		clearMessageRelation(messageId);

		respData.setSystemMessage("删除成功!", null, null);
		return respData;
	}

	/**
	 * 列表显示收到的消息
	 * @param jqReq
	 * @param readerUser
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getReceivedMessageGridData(JqgridReqData jqReq, User readerUser) {
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
	 * 列表显示已发送/草稿箱的消息
	 * @param jqReq
	 * @param senderUser
	 * @param state
	 * @return
	 */
	@Aop(value = "log")
	public AdvancedJqgridResData<Message> getSentMessageGridData(JqgridReqData jqReq, User senderUser, int state) {
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
	public AjaxResData getReceiverUserNum(Long messageId) {
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
	 * 获取指定消息的附件文件的 fileIdInPoll-fileName 的Map
	 * @param messageId
	 * @return
	 */
	public AjaxResData getAttachments(Long messageId) {
		AjaxResData respData = new AjaxResData();
		Message m = dao().fetchLinks(fetch(messageId), "attachments");
		if (null != m) {
			List<PoolFile> attachments = m.getAttachments();
			Map<Long, String> attachmentMap = new HashMap<Long, String>();
			for (PoolFile file : attachments) {
				attachmentMap.put(file.getIdInPool(), file.getName());
			}
			respData.setReturnData(attachmentMap);
		}
		return respData;
	}

	/**
	 * 将收件人列表信息整理成其id的数组
	 * @param userNumArr
	 * @return
	 */
	private Set<Long> numArr2IdArr(String[] userNumArr) {
		// 将形如 AA<1234>这样的元素转换成1234
		Set<Long> idArr = new HashSet<Long>();
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
		Set<Long> idArr = numArr2IdArr(receiverUsers);
		Message message = new Message();
		message.setSenderUserId(senderUser.getId());
		List<User> receivers = new ArrayList<User>();
		for (Long id : idArr) {
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

	/**
	 * 根据临时文件的在临时文件池的ID及文件名，获取该文件，并存入附件文件池中；
	 * 并将各个附件文件的信息组成列表。
	 * @param attachments
	 * @param ioc
	 * @return
	 * @throws IOException
	 */
	private List<PoolFile> getAttachments(String[] attachments) throws IOException {
		if (null == attachments) {
			return null;
		}
		List<PoolFile> poolFiles = new ArrayList<PoolFile>();
		for (String attachment : attachments) {
			String[] arr = attachment.split("=");
			long fileId = Long.parseLong(arr[0]);
			String fileName = arr[1];
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			PoolFile attachmentPoolFile = new PoolFile();
			attachmentPoolFile.setIdInPool(fileId);
			attachmentPoolFile.setName(fileName);
			attachmentPoolFile.setSuffix(suffix);
			attachmentPoolFile.setPoolIocName("attachmentPool");
			poolFiles.add(attachmentPoolFile);
		}
		return poolFiles;
	}

	/**
	 * 清除旧的(收件人、附件文件)关系数据，附件文件信息，删除文件池中旧的附件文件
	 * @param messageId
	 * @param ioc
	 */
	private void clearMessageRelation(Long messageId) {
		messageId = null == messageId ? 0 : messageId;
		Message m = dao().fetchLinks(fetch(messageId), "attachments");
		if (null != m) {
			List<PoolFile> oldAttachments = m.getAttachments();
			dao().delete(oldAttachments);
			dao().clearLinks(m, "receivers");
			dao().clearLinks(m, "attachments");
		}
	}
}