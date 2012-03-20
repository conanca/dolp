package com.dolplay.dolpbase.system.secheduler.job;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.filepool.FilePool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dolplay.dolpbase.common.util.DaoProvider;
import com.dolplay.dolpbase.common.util.IocProvider;
import com.dolplay.dolpbase.system.domain.PoolFile;

/**
 * 移除无用的消息附件文件(包括文件本身和文件信息表中记录)的job
 * @author Administrator
 *
 */
public class RemoveInvalidAttachmentJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Dao dao = DaoProvider.getDao();
		FilePool attachmentPool = IocProvider.getIoc().get(FilePool.class, "attachmentPool");

		Sql sql = Sqls
				.create("SELECT * FROM SYSTEM_POOLFILE WHERE POOLIOCNAME = @poolIocName AND IDINPOOL NOT IN (SELECT POOLFILEID FROM SYSTEM_MESSAGE_POOLFILE)");
		sql.params().set("poolIocName", "attachmentPool");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(PoolFile.class));
		dao.execute(sql);
		List<PoolFile> rs = sql.getList(PoolFile.class);
		for (PoolFile poolFile : rs) {
			attachmentPool.removeFile(poolFile.getIdInPool(), poolFile.getSuffix());
		}
		dao.delete(rs);
	}
}
