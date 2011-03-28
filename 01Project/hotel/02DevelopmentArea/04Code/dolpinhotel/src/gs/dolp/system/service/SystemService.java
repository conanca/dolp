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

	@Aop(value = "log")
	public AjaxResData initDatabase() {
		AjaxResData reData = new AjaxResData();
		Tables.define(dao(), Tables.loadFrom("tables.dod"));
		FileSqlManager fm = new FileSqlManager("initData.sql");
		dao().execute(fm.createCombo(fm.keys()));
		reData.setUserdata(new SystemMessage("初始化数据库完成！", null, null));
		return reData;
	}

	@Aop(value = "log")
	public InputStream genExcel(Map<String, String>[] rowDatas) throws IOException {
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		InputStream is;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sh = wb.createSheet();
		HSSFRow firstRow = sh.createRow(0);
		int i = 0;
		for (String key : rowDatas[0].keySet()) {
			firstRow.createCell(i).setCellValue(key);
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
		wb.write(fos);
		is = new ByteArrayInputStream(fos.toByteArray());

		return is;
	}
}
