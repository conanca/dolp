package gs.dolp.system.module;

import gs.dolp.common.domain.ResponseData;
import gs.dolp.common.jqgrid.domain.AdvancedJqgridResData;
import gs.dolp.common.jqgrid.domain.JqgridReqData;
import gs.dolp.system.domain.Message;
import gs.dolp.system.domain.User;
import gs.dolp.system.service.MessageService;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

@InjectName("messageModule")
@At("/system/message")
public class MessageModule {
	private MessageService messageService;

	@At
	public AdvancedJqgridResData<Message> getInboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getReceivedMessageGridData(jqReq, currentUser);
	}

	@At
	public AdvancedJqgridResData<Message> getSentboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getSentMessageGridData(jqReq, currentUser, 1);
	}

	@At
	public AdvancedJqgridResData<Message> getDraftboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session)
			throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getSentMessageGridData(jqReq, currentUser, 0);
	}

	@At
	public ResponseData sendMessage(@Param("messageId") int messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.sendMessage(messageId, currentUser, receiverUsers, title, content);
	}

	@At
	public ResponseData saveMessage(@Param("messageId") int messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.saveMessage(messageId, currentUser, receiverUsers, title, content);
	}

	@At
	public ResponseData deleteReceivedMessage(@Param("messageId") int messageId, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.deleteReceivedMessage(messageId, currentUser);
	}

	@At
	public ResponseData deleteSentMessage(@Param("messageId") int messageId) {
		return messageService.deleteSentMessage(messageId);
	}

	@At
	public ResponseData deleteDraftMessage(@Param("messageId") int messageId) {
		return messageService.deleteDraftMessage(messageId);
	}

	@At
	public ResponseData readMessade(@Param("messageId") int messageId, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.readMessade(currentUser, messageId);
	}

	@At
	public ResponseData getReceiverUserNum(@Param("messageId") int messageId) {
		return messageService.getReceiverUserNum(messageId);
	}
}
