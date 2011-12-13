package com.dolplay.dolpbase.system.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;

import com.dolplay.dolpbase.common.domain.AjaxResData;
import com.dolplay.dolpbase.common.service.DolpBaseService;
import com.dolplay.dolpbase.common.util.PropProvider;
import com.dolplay.dolpbase.system.domain.SysEnum;
import com.dolplay.dolpbase.system.domain.SysEnumItem;
import com.dolplay.dolpbase.system.domain.User;

@IocBean(args = { "refer:dao" })
public class SystemService extends DolpBaseService<Object> {

	public static final String SYSTEM_SYSTEMNAME = "SYSTEM_SYSTEMNAME";

	public SystemService(Dao dao) {
		super(dao);
	}

	@Aop(value = "log")
	public AjaxResData getSystemName() {
		AjaxResData respData = new AjaxResData();
		String systemName = PropProvider.getProp().getString(SYSTEM_SYSTEMNAME);
		if (Strings.isEmpty(systemName)) {
			respData.setSystemMessage(null, "配置文件中SYSTEM_SYSTEMNAME未配置或为空", null);
		} else {
			respData.setReturnData(systemName);
		}
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getSysEnumItemMap(String sysEnumName) {
		AjaxResData respData = new AjaxResData();
		SysEnum sysEnum = dao().fetch(SysEnum.class, Cnd.where("NAME", "=", sysEnumName));
		if (sysEnum == null) {
			StringBuilder message = new StringBuilder("系统枚举:\"");
			message.append(sysEnumName);
			message.append("\"不存在!");
			throw new RuntimeException(message.toString());
		}
		List<SysEnumItem> items = dao().query(SysEnumItem.class, Cnd.where("SYSENUMID", "=", sysEnum.getId()), null);
		Map<String, String> options = new LinkedHashMap<String, String>();
		for (SysEnumItem item : items) {
			options.put(item.getText(), item.getValue());
		}
		respData.setReturnData(options);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getCurrentUserName(User cUser) {
		AjaxResData respData = new AjaxResData();
		if (cUser != null) {
			respData.setReturnData(cUser.getName());
			respData.setSystemMessage("用户" + cUser.getName() + "登录成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public InputStream genExcel(String[] colNames, Map<String, String>[] rowDatas) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sh = wb.createSheet();
		HSSFRow firstRow = sh.createRow(0);
		int i = 0;
		for (String colName : colNames) {
			if (colName != null && colName.contains("type='checkbox'")) {
				continue;
			}
			firstRow.createCell(i).setCellValue(colName);
			i++;
		}
		i = 1;
		for (Map<String, String> rowData : rowDatas) {
			HSSFRow row = sh.createRow(i);
			int j = 0;
			for (String key : rowData.keySet()) {
				row.createCell(j).setCellValue(rowData.get(key));
				j++;
			}
			i++;
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			throw new RuntimeException("写入工作薄时异常!", e);
		}
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}
}