package com.dolplay.dolpbase.common.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.view.AbstractPathView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.dolpbase.common.report.ReportHandler;
import com.dolplay.dolpbase.common.report.WebappDataSource;

/**
 * jasper报表(HTML)的视图:可以在注解中指定报表文件
 * @author Administrator
 */
public class HtmlReport2View extends AbstractPathView {
	final static Logger logger = LoggerFactory.getLogger(HtmlReport2View.class);

	public HtmlReport2View(String dest) {
		super(dest);
	}

	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Throwable {
		if (obj == null) {
			resp.getWriter().print("报表参数和数据源为空");
			return;
		}
		// 获取报表文件的实际路径
		String path = evalPath(req, obj);
		if (Strings.isBlank(path)) {
			path = Mvcs.getRequestPath(req);
			path = "/WEB-INF" + (path.startsWith("/") ? "" : "/") + Files.renameSuffix(path, getExt());
		} else if (path.charAt(0) == '/') {
			String ext = getExt();
			if (!path.toLowerCase().endsWith(ext))
				path += ext;
		} else {
			path = "/WEB-INF/" + path.replace('.', '/') + getExt();
		}
		path = req.getSession().getServletContext().getRealPath(path);
		// 设置报表的参数和字段
		Object[] reportContent = (Object[]) obj;
		@SuppressWarnings("unchecked")
		Map<String, Object> parameters = (Map<String, Object>) reportContent[0];
		WebappDataSource dataSource = (WebappDataSource) reportContent[1];
		JRHtmlExporter exporter = null;
		try {
			exporter = ReportHandler.export(path, parameters, dataSource);
		} catch (Exception e) {
			logger.error("报表导出异常", e);
			resp.getWriter().print(e.getMessage());
			return;
		}
		// 将报表输出为HTML页面
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, req.getContextPath() + "/image?image=");
		exporter.exportReport();
	}

	protected String getExt() {
		return ".jasper";
	}
}