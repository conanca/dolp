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
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.domain.ResponseData;
import com.dolplay.dolpbase.common.domain.UploadTempFile;
import com.dolplay.dolpbase.common.domain.jqgrid.JqgridReqData;
import com.dolplay.dolpbase.common.util.FileUtils;
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

	@At("/getAttachments/*")
	public ResponseData getAttachments(int messageId) {
		return messageService.getAttachments(messageId);
	}

	@At
	public ResponseData sendMessage(@Param("messageId") int messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content,
			@Param("attachments[]") String[] attachments, Ioc ioc, HttpSession session) throws IOException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.saveOrSendMessage(1, messageId, currentUser, receiverUsers, title, content, attachments);
	}

	@At
	public ResponseData saveMessage(@Param("messageId") int messageId, @Param("receiverUsers") String[] receiverUsers,
			@Param("title") String title, @Param("content") String content,
			@Param("attachments[]") String[] attachments, Ioc ioc, HttpSession session) throws IOException {
		User currentUser = (User) session.getAttribute("logonUser");
		return messageService.saveOrSendMessage(0, messageId, currentUser, receiverUsers, title, content, attachments);
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

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:attachmentUpload" })
	public ResponseData uploadAttachment(@Param("messageAttachment") TempFile tf, Ioc ioc) throws IOException {
		AjaxResData respData = new AjaxResData();
		UploadTempFile tmpFile = FileUtils.getUploadTempFile(tf, ioc, "attachmentUpload");
		respData.setReturnData(tmpFile);
		respData.setSystemMessage("上传完成!", null, null);
		return respData;
	}

	@At
	public ResponseData removeAttachment(@Param("id") Long id, @Param("name") String name, Ioc ioc) {
		FilePool pool = ioc.get(NutFilePool.class, "attachmentPool");
		pool.removeFile(id, name.substring(name.lastIndexOf(".")));
		//respData.setSystemMessage("删除文件成功!", null, null);
		return new AjaxResData();
	}

	@At
	@Ok("raw:stream")
	public InputStream downloadAttachment(@Param("id") long id, @Param("name") String name, Ioc ioc,
			HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
		FilePool attachmentPool = ioc.get(NutFilePool.class, "attachmentPool");
		File f = attachmentPool.getFile(id, name.substring(name.lastIndexOf(".")));
		InputStream in = new FileInputStream(f);
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "utf-8"));
		response.setHeader("Content-Length", "" + f.length());
		return in;
	}
}