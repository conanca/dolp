package com.dolplay.dolpbase.common.view;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.nutz.mvc.View;

/**
 * jasper报表(HTML)的视图
 * @author Administrator
 */
public class HtmlReportView implements View {

	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Throwable {
		if (obj == null || !JRHtmlExporter.class.isAssignableFrom(obj.getClass())) {
			resp.getWriter().print("报表导出失败!");
			return;
		}
		JRHtmlExporter exporter = (JRHtmlExporter) obj;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, req.getContextPath() + "/image?image=");
		exporter.exportReport();
	}

}
