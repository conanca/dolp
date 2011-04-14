package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.domain.SystemMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.tools.Tables;
import org.nutz.ioc.aop.Aop;
import org.nutz.service.Service;

public class SystemService extends Service {

	public SystemService(Dao dao) {
		super(dao);
	}

	public AjaxResData getSystemName() {
		AjaxResData reData = new AjaxResData();
		reData.setReturnData(SysParaService.getSysParaValue("SystemName", dao()));
		return reData;
	}

	/**
	 * 根据数据库脚本，初始化数据库
	 * @return
	 */
	@Aop(value = "log")
	public AjaxResData initDatabase() {
		AjaxResData reData = new AjaxResData();
		Tables.define(dao(), Tables.loadFrom("tables.dod"));
		FileSqlManager fm = new FileSqlManager("initData.sql");
		dao().execute(fm.createCombo(fm.keys()));
		reData.setSystemMessage(new SystemMessage("初始化数据库完成！", null, null));
		return reData;
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
