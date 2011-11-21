package com.dolplay.dolpbase.common.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.aop.Aop;
import org.nutz.mvc.upload.FieldMeta;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.dolplay.dolpbase.common.domain.DolpProperties;
import com.dolplay.dolpbase.common.util.StringUtils;
import com.dolplay.dolpbase.system.domain.PoolFile;
import com.dolplay.dolpbase.system.domain.SysPara;
import com.dolplay.dolpbase.system.domain.User;

/**
 * @author Administrator
 * 共通Service类， 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public abstract class DolpBaseService<T> extends JqgridService<T> {

	private DolpProperties prop;

	public DolpBaseService(Dao dao) {
		super(dao);
	}

	public DolpProperties getProp() {
		return prop;
	}

	public void setProp(DolpProperties prop) {
		this.prop = prop;
	}

	/**
	 * 获取指定名称的系统参数的值
	 * @param name
	 * @return
	 */
	@Aop(value = "log")
	public String getSysParaValue(String name) {
		SysPara sysPara = dao().fetch(SysPara.class, Cnd.where("NAME", "=", name));
		if (sysPara == null) {
			StringBuilder message = new StringBuilder("系统参数:\"");
			message.append(name);
			message.append("\"不存在!");
			throw new RuntimeException(message.toString());
		}
		return sysPara.getValue();
	}

	@Aop(value = "log")
	public PoolFile saveUploadFileInfo(TempFile tf, Ioc ioc, User owner) {
		if (tf == null) {
			return null;
		}
		File f = tf.getFile();
		long fileId = ioc.get(UploadAdaptor.class, "attachmentUpload").getContext().getFilePool().getFileId(f);
		FieldMeta meta = tf.getMeta();
		PoolFile uploadTempFile = new PoolFile();
		uploadTempFile.setIdInPool(fileId);
		uploadTempFile.setName(meta.getFileLocalName());
		uploadTempFile.setClientLocalPath(meta.getFileLocalPath());
		uploadTempFile.setContentType(meta.getContentType());
		uploadTempFile.setSuffix(StringUtils.getFileSuffix(meta.getFileLocalName()));
		uploadTempFile.setPoolIocName("attachmentPool");
		uploadTempFile.setUploadDate(new Timestamp((new Date()).getTime()));
		uploadTempFile.setOwnerUserId(owner.getId());
		dao().insert(uploadTempFile);

		return uploadTempFile;
	}
}