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

	//TODO 改名为currentUser
	@At
	public AdvancedJqgridResData<Message> getInboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
		User user = (User) session.getAttribute("logonUser");
		return messageService.getReceivedMessageGridData(jqReq, user);
	}

	@At
	public AdvancedJqgridResData<Message> getSentGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
		Object obj = session.getAttribute("logonUser");
		User user = (User) obj;
		return messageService.getSentMessageGridData(jqReq, user, 1);
	}

	@At
	public AdvancedJqgridResData<Message> getDraftGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
		Object obj = session.getAttribute("logonUser");
		User user = (User) obj;
		return messageService.getSentMessageGridData(jqReq, user, 0);
	}

	@At
	public ResponseData sendMessage(@Param("receiverUserIds") int[] receiverUserIds, @Param("title") String title,
			@Param("content") String content, HttpSession session) {
		User user = (User) session.getAttribute("logonUser");
		return messageService.sendMessage(user, receiverUserIds, title, content);
	}

	@At
	public ResponseData saveMessage(@Param("receiverUserIds") int[] receiverUserIds, @Param("title") String title,
			@Param("content") String content, HttpSession session) {
		User user = (User) session.getAttribute("logonUser");
		return messageService.saveMessage(user, receiverUserIds, title, content);
	}

	@At
	public ResponseData deleteMessage(@Param("messageId") String messageId) {
		return messageService.deleteMessage(messageId);
	}

	@At
	public ResponseData readMessade(@Param("messageId") String messageId, HttpSession session) {
		User user = (User) session.getAttribute("logonUser");
		return messageService.readMessade(user, messageId);
	}
}
