package com.dolplay.dolpbase.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelHandler {
	private static Logger logger = LoggerFactory.getLogger(ExcelHandler.class);

	/**
	 * 将一个List中的元素写入一个EXCEL文档中指定sheet中
	 * @param wb
	 * @param sheetName
	 * @param list
	 */
	public static void creatSheet(HSSFWorkbook wb, String sheetName, List list) {
		if (list == null || list.size() == 0 || wb == null) {
			return;
		}
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow firstRow = sheet.createRow(0);
		Set<Field> fieldSet = new LinkedHashSet<Field>();
		for (Object obj : list) {
			Field[] fieldArr = obj.getClass().getDeclaredFields();
			int i = 0;
			for (Field filed : fieldArr) {
				fieldSet.add(filed);
				String filedName = filed.getName();
				firstRow.createCell(i).setCellValue(filedName);
				i++;
			}
			break;
		}

		int i = 1;
		for (Object obj : list) {
			HSSFRow row = sheet.createRow(i);
			int j = 0;
			for (Field filed : fieldSet) {
				try {
					filed.setAccessible(true);
					String fieldValueStr = filed.get(obj).toString();
					row.createCell(j).setCellValue(fieldValueStr);
				} catch (Exception e) {
					logger.error("取字段值发生异常", e);
				}
				j++;
			}
			i++;
		}
	}

	/**
	 * 读一个EXCEL文档的某个sheet,将数据放入到<pojoClass>类型的list中
	 * @param sheet
	 * @param pojoClass
	 * @return
	 */
	public static List<Object> readSheet(HSSFSheet sheet, Class<?> pojoClass) {
		List<Object> list = new ArrayList<Object>();
		try {
			Field[] fieldArr = pojoClass.getDeclaredFields();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				Object obj = pojoClass.newInstance();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					Field field = fieldArr[j];
					field.setAccessible(true);
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						double value = cell.getNumericCellValue();
						if (field.getType().equals(double.class)) {
							field.setDouble(obj, value);
						} else if (field.getType().equals(int.class)) {
							int intvalue = (int) value;
							field.setInt(obj, intvalue);
						} else if (field.getType().equals(float.class)) {
							float intvalue = (float) value;
							field.setFloat(obj, intvalue);
						} else {
							logger.error("未知类型");
						}
					} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						String value = cell.getRichStringCellValue().getString();
						field.set(obj, value);
					} else {
						logger.error("单元格类型未知");
					}
				}
				list.add(obj);
			}
		} catch (Exception e) {
			logger.error("字段读取或赋值异常", e);
		}
		return list;
	}
}