package com.dolplay.dolpbase.common.util;

import java.io.File;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.dolplay.dolpbase.common.domain.UploadTempFile;

public class FileUtils {

	public static UploadTempFile getUploadTempFile(TempFile tf, Ioc ioc, String uploadAdaptor) {
		File f = tf.getFile();
		long fileId = ioc.get(UploadAdaptor.class, uploadAdaptor).getContext().getFilePool().getFileId(f);
		FieldMeta meta = tf.getMeta();
		UploadTempFile uploadTempFile = new UploadTempFile();
		uploadTempFile.setId(fileId);
		uploadTempFile.setName(meta.getFileLocalName());
		uploadTempFile.setClientLocalPath(meta.getFileLocalPath());
		uploadTempFile.setContentType(meta.getContentType());
		return uploadTempFile;
	}
}
