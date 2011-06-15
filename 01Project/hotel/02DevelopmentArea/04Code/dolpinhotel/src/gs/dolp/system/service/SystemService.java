package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.system.domain.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.DTable;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.aop.Aop;

public class SystemService extends DolpBaseService<Object> {

	public SystemService(Dao dao) {
		super(dao);
	}

	public AjaxResData getSystemName() {
		AjaxResData respData = new AjaxResData();
		respData.setReturnData(getSysParaValue("SystemName"));
		return respData;
	}

	/**
	 * 根据数据库脚本，初始化数据库
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData initDatabase() {
		AjaxResData respData = new AjaxResData();
		// 初始化表结构
		List<DTable> dts = Tables.loadFrom("tables_system.dod");
		dts.addAll(Tables.loadFrom("tables_hotel.dod"));
		Tables.define(dao(), dts);
		// 初始化记录
		FileSqlManager fm = new FileSqlManager("init_system.sql");
		dao().execute(fm.createCombo(fm.keys()));
		fm = new FileSqlManager("init_hotel.sql");
		dao().execute(fm.createCombo(fm.keys()));
		respData.setSystemMessage("初始化数据库完成！", null, null);
		return respData;
	}

	@Aop(value = "log")
	public AjaxResData getCurrentUserName(User cUser) {
		AjaxResData respData = new AjaxResData();
		if (cUser != null) {
			respData.setReturnData(cUser.getName());
			respData.setSystemMessage("登录成功!", null, null);
		}
		return respData;
	}

	@Aop(value = "log")
	public InputStream genExcel(String[] colNames, Map<String, String>[] rowDatas) throws IOException {
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
		wb.write(os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}
}
