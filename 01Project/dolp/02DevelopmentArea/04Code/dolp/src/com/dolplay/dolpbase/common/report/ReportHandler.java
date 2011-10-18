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

public class ReportHandler {

	public static void compile(String srcReportFilePath) throws JRException {
		File srcReportFile = new File(srcReportFilePath);
		if (srcReportFile.exists()) {
			JasperCompileManager.compileReportToFile(srcReportFilePath);
		} else {
			throw new RuntimeException("报表模板不存在!");
		}
	}

	public static JasperPrint fill(String reportFilePath, Map<String, Object> parameters, JRDataSource dataSource)
			throws JRException {
		File reportFile = new File(reportFilePath);
		if (!reportFile.exists()) {
			String srcReportFilePath = reportFilePath.replace(".jasper", ".jrxml");
			ReportHandler.compile(srcReportFilePath);
		}
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		return jasperPrint;
	}

	public static JRHtmlExporter export(String reportFilePath, Map<String, Object> parameters, JRDataSource connection)
			throws IOException, JRException {
		JRHtmlExporter exporter = new JRHtmlExporter();

		JasperPrint jasperPrint = ReportHandler.fill(reportFilePath, parameters, connection);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		return exporter;
	}
}
