package com.dolplay.dolpbase.system.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.system.domain.User;
import com.dolplay.dolpbase.system.service.MessageService;

@IocBean
@At("/system/message")
public class MessageModule {

	@Inject
	private MessageService messageService;

	@At
	public ResponseData getInboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getReceivedMessageGridData(jqReq, currentUser);
	}

	@At
	public ResponseData getSentboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getSentMessageGridData(jqReq, currentUser, 1);
	}

	@At
	public ResponseData getDraftboxGridData(@Param("..") JqgridReqData jqReq, HttpSession session) {
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

	@At("/getReceiverUserNum/*")
	public ResponseData getReceiverUserNum(int messageId) {
		return messageService.getReceiverUserNum(messageId);
	}
}