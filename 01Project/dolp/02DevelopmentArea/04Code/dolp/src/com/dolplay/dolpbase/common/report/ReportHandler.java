package com.dolplay.dolpbase.common.report;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * jasperreport的操作帮助类
 * @author Administrator
 */
public class ReportHandler {

	/**
	 * 编译报表设计文件
	 * @param srcReportFilePath
	 * @throws JRException
	 */
	public static void compile(String srcReportFilePath) throws JRException {
		File srcReportFile = new File(srcReportFilePath);
		if (srcReportFile.exists()) {
			JasperCompileManager.compileReportToFile(srcReportFilePath);
		} else {
			throw new JRException("报表模板不存在!");
		}
	}

	/**
	 * 填充报表数据——设置参数和数据源
	 * @param reportFilePath
	 * @param parameters
	 * @param dataSource
	 * @return
	 * @throws JRException
	 */
	public static JasperPrint fill(String reportFilePath, Map<String, Object> parameters, JRDataSource dataSource)
			throws JRException {
		// 获取报表文件，若不存在便先编译报表设计文件
		File reportFile = new File(reportFilePath);
		if (!reportFile.exists()) {
			String srcReportFilePath = reportFilePath.replace(".jasper", ".jrxml");
			ReportHandler.compile(srcReportFilePath);
		}
		// 填充报表数据
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		return jasperPrint;
	}

	/**
	 * 填充报表数据,获取JRHtmlExporter对象
	 * @param reportFilePath
	 * @param parameters
	 * @param connection
	 * @return
	 * @throws IOException
	 * @throws JRException
	 */
	public static JRHtmlExporter export(String reportFilePath, Map<String, Object> parameters, JRDataSource connection)
			throws IOException, JRException {
		JRHtmlExporter exporter = new JRHtmlExporter();
		JasperPrint jasperPrint = ReportHandler.fill(reportFilePath, parameters, connection);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		return exporter;
	}
}
