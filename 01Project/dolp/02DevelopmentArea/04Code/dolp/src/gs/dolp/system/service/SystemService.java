package gs.dolp.system.service;

import gs.dolp.common.domain.AjaxResData;
import gs.dolp.common.service.DolpBaseService;
import gs.dolp.system.domain.Client;
import gs.dolp.system.domain.Menu;
import gs.dolp.system.domain.Message;
import gs.dolp.system.domain.Organization;
import gs.dolp.system.domain.Privilege;
import gs.dolp.system.domain.Role;
import gs.dolp.system.domain.SysEnum;
import gs.dolp.system.domain.SysEnumItem;
import gs.dolp.system.domain.SysPara;
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
import org.nutz.dao.sql.Sql;
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
		// 建实体类的表
		Dao dao = dao();
		dao().create(Client.class, true);
		dao.create(Menu.class, true);
		dao.create(Message.class, true);
		dao.create(Organization.class, true);
		dao.create(Privilege.class, true);
		dao.create(Role.class, true);
		dao.create(SysEnum.class, true);
		dao.create(SysEnumItem.class, true);
		dao.create(SysPara.class, true);
		dao.create(User.class, true);
		// 添加默认记录
		FileSqlManager fm = new FileSqlManager("init_system.sql");
		List<Sql> sqlList = fm.createCombo(fm.keys());
		dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		// 初始化quartz的数据表
		fm = new FileSqlManager("tables_quartz.sql");
		sqlList = fm.createCombo(fm.keys());
		dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
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
