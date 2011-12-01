package com.dolplay.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Files;
import org.nutz.lang.stream.StringOutputStream;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CodeGenerator {

	final static private String PACKAGE = "com.dolplay.dolpbase.qrtz";
	final static private String REQUESTPATH = "qrtz";
	final static private String HTMLPATH = "qrtz";

	final static private String TEMPFILEDIR = "temp";
	private Configuration cfg;
	private Map<String, Object> root;

	private StringBuilder others1;
	private StringBuilder others2;
	private StringBuilder others3;

	public static void main(String[] args) throws Exception {
		CodeGenerator t = new CodeGenerator();
		t.genAllCode();
	}

	public CodeGenerator() {
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(TEMPFILEDIR));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		others1 = new StringBuilder();
		others2 = new StringBuilder();
		others3 = new StringBuilder();
	}

	private void genAllCode() throws ClassNotFoundException, IOException, TemplateException {
		String[] nameArr = getDomainClassNameArr();
		for (int i = 0; i < nameArr.length; i++) {
			Class<?> classf = Class
					.forName(PACKAGE + ".domain." + nameArr[i].substring(0, nameArr[i].lastIndexOf(".")));
			genEntityCode(classf);
		}
		System.out.println("\n请将以下内容拷贝到相应的文件中：\n");
		System.out.println(others1.append("\n").append(others2).append("\n").append(others3));
	}

	private void genEntityCode(Class<?> domainClass) throws IOException, TemplateException {

		/* 创建数据模型 */
		root = new HashMap<String, Object>();
		root.put("package", PACKAGE);
		root.put("domainPackage", domainClass.getPackage().getName());
		root.put("Domain", domainClass.getSimpleName());
		root.put("requestPath", REQUESTPATH);
		root.put("domainFields", getDomainFields(domainClass));
		root.put("htmlPath", HTMLPATH);

		/* 分别根据模板，写入文件或输出 */
		tempToFile("${Domain}Service.java", getServiceSrcPath(domainClass));
		tempToFile("${Domain}Module.java", getModuleSrcPath(domainClass));
		tempToFile("${Domain}_manager.html", getHtmlPath(domainClass));
		tempPrint("others1.ftl", others1);
		tempPrint("others2.ftl", others2);
		tempPrint("others3.ftl", others3);
	}

	private void tempToFile(String tempFileName, String filePath) throws IOException, TemplateException {
		boolean isCreat = Files.createNewFile(new File(filePath));
		if (isCreat) {
			File f = new File(filePath);
			Writer out = new BufferedWriter(new FileWriter(f, true));
			Template temp = cfg.getTemplate(tempFileName);
			temp.process(root, out);
			out.flush();
			System.out.println("文件写入完成：" + filePath);
		} else {
			System.out.println("文件写入失败，文件已存在：" + filePath);
		}
	}

	private void tempPrint(String tempFileName, StringBuilder sb) throws IOException, TemplateException {
		OutputStream os = new StringOutputStream(sb);
		Writer out = new OutputStreamWriter(os);
		Template temp = cfg.getTemplate(tempFileName);
		temp.process(root, out);
		out.flush();
		sb.append("\n");
	}

	private List<String> getDomainFields(Class<?> domainClass) {
		List<String> fields = new ArrayList<String>();
		Field[] fieldArr = domainClass.getDeclaredFields();
		for (Field filed : fieldArr) {
			String filedName = filed.getName();
			fields.add(filedName);
		}
		return fields;
	}

	private String[] getDomainClassNameArr() {
		File f = new File("src\\" + PACKAGE.replace('.', '\\') + "\\domain\\");
		if (f.isDirectory()) {
			return f.list();
		} else {
			return null;
		}
	}

	private String getServiceSrcPath(Class<?> domainClass) {
		return "src\\" + PACKAGE.replace('.', '\\') + "\\service\\" + domainClass.getSimpleName() + "Service.java";
	}

	private String getModuleSrcPath(Class<?> domainClass) {
		return "src\\" + PACKAGE.replace('.', '\\') + "\\module\\" + domainClass.getSimpleName() + "Module.java";
	}

	private String getHtmlPath(Class<?> domainClass) {
		return "WebContent\\" + HTMLPATH + "\\" + domainClass.getSimpleName().toLowerCase() + "_manager.html";
	}

	public static void str2File(File f, String str) {
		BufferedWriter bw = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(f, true));
			bw.write(str);
			bw.flush();
			System.out.println("文件写入完成！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}