package com.dolplay.dolpbase.system.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.Message;
import com.dolplay.dolpbase.system.domain.User;
import com.dolplay.dolpbase.system.filter.CheckAttachmentOperation;
import com.dolplay.dolpbase.system.filter.CheckLogon;
import com.dolplay.dolpbase.system.filter.CheckPrivilege;
import com.dolplay.dolpbase.system.service.MessageService;

@IocBean
@At("/system/message")
public class MessageModule {

	@Inject
	private MessageService messageService;

	@At
	public ResponseData getInboxGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Message messageSearch, @Param("senderName") String senderName, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getReceivedMessageGridData(jqReq, isSearch, messageSearch, senderName, currentUser);
	}

	@At
	public ResponseData getSentboxGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Message messageSearch, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getSentMessageGridData(jqReq, currentUser, isSearch, messageSearch, 1);
	}

	@At
	public ResponseData getDraftboxGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Message messageSearch, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.getSentMessageGridData(jqReq, currentUser, isSearch, messageSearch, 0);
	}

	@At("/getAttachments/*")
	public ResponseData getAttachments(Long messageId) {
		return messageService.getAttachments(messageId);
	}

	@At
	public ResponseData sendMessage(@Param("messageId") Long messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content,
			@Param("attachments[]") String[] attachments, Ioc ioc, HttpSession session) throws IOException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.saveOrSendMessage(1, messageId, currentUser, receiverUsers, title, content, attachments);
	}

	@At
	public ResponseData saveMessage(@Param("messageId") Long messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content,
			@Param("attachments[]") String[] attachments, Ioc ioc, HttpSession session) throws IOException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.saveOrSendMessage(0, messageId, currentUser, receiverUsers, title, content, attachments);
	}

	@At
	public ResponseData deleteReceivedMessage(@Param("messageId") Long messageId, HttpSession session) {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.deleteReceivedMessage(messageId, currentUser);
	}

	@At
	public ResponseData deleteSentMessage(@Param("messageId") Long messageId) {
		return messageService.deleteSentMessage(messageId);
	}

	@At
	public ResponseData deleteDraftMessage(@Param("messageId") Long messageId, Ioc ioc) {
		return messageService.deleteDraftMessage(messageId, ioc);
	}

	@At("/getReceiver/*")
	public ResponseData getReceiver(Long messageId) {
		return messageService.getReceiverUserNameNum(messageId);
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:attachmentUpload" })
	public ResponseData uploadAttachment(@Param("messageAttachment") TempFile tf, Ioc ioc, HttpSession session)
			throws IOException {
		User cUser = (User) session.getAttribute("logonUser");
		return messageService.saveAttachment(tf, ioc, cUser);
	}

	@At
	@Ok("raw:stream")
	@Filters({ @By(type = CheckLogon.class), @By(type = CheckPrivilege.class),
			@By(type = CheckAttachmentOperation.class) })
	public InputStream downloadAttachment(@Param("id") Long id, @Param("name") String name, Ioc ioc,
			HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
		FilePool attachmentPool = ioc.get(NutFilePool.class, "attachmentPool");
		File f = attachmentPool.getFile(id, StringUtils.getFileSuffix(name));
		InputStream in = new FileInputStream(f);
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "utf-8"));
		response.setHeader("Content-Length", "" + f.length());
		return in;
	}
}