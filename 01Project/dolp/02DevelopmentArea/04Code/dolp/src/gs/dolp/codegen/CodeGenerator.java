package gs.dolp.codegen;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import gs.dolp.system.domain.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

	final static private Class<?> domainClass = User.class;
	final static private String PACKAGE = "gs.dolp.hotel";
	final static private String REQUESTPATH = "hotel";
	final static private String HTMLPATH = "hotel";

	final static private String TEMPFILEDIR = "temp";
	private Configuration cfg;
	private Map<String, Object> root;

	public static void main(String[] args) throws Exception {
		CodeGenerator t = new CodeGenerator();
		t.genCode();
	}

	public CodeGenerator() {
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(new File(TEMPFILEDIR));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	private void genCode() throws IOException, TemplateException {

		/* 创建数据模型 */
		root = new HashMap<String, Object>();
		root.put("package", PACKAGE);
		root.put("Domain", domainClass.getSimpleName());
		root.put("requestPath", REQUESTPATH);
		root.put("domainFields", getDomainFields());
		root.put("htmlPath", HTMLPATH);

		/* 分别根据模板，写入文件或输出 */
		tempToFile("${Domain}Service.java", getServiceSrcPath());
		tempToFile("${Domain}Module.java", getModuleSrcPath());
		tempToFile("${Domain}_manger.html", getHtmlPath());
		tempPrint("ico.js.ftl");
		tempPrint("sql.ftl");
	}

	private void tempToFile(String tempFileName, String filePath) throws IOException, TemplateException {
		Template temp = cfg.getTemplate(tempFileName);
		File f = new File(filePath);
		File dir = new File(f.getParent());
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (f.exists()) {
			System.out.println(f.getPath() + "已存在！");
			return;
		} else {
			f.createNewFile();
		}
		Writer out = new BufferedWriter(new FileWriter(f, true));
		temp.process(root, out);
		out.flush();
		System.out.println("写入完成！");
	}

	private void tempPrint(String tempFileName) throws IOException, TemplateException {
		Writer out = new OutputStreamWriter(System.out);
		Template temp = cfg.getTemplate(tempFileName);
		System.out.println("请拷贝以下内容至合适的文件中：");
		temp.process(root, out);
		out.flush();
		System.out.println("\n");
	}

	private List<String> getDomainFields() {
		List<String> fields = new ArrayList<String>();
		Field[] fieldArr = domainClass.getDeclaredFields();
		for (Field filed : fieldArr) {
			String filedName = filed.getName();
			fields.add(filedName);
		}
		return fields;
	}

	private String getServiceSrcPath() {
		return "src\\" + PACKAGE.replace('.', '\\') + "\\service\\" + domainClass.getSimpleName() + "Service.java";
	}

	private String getModuleSrcPath() {
		return "src\\" + PACKAGE.replace('.', '\\') + "\\module\\" + domainClass.getSimpleName() + "Module.java";
	}

	private String getHtmlPath() {
		return "WebContent\\" + HTMLPATH + "\\" + domainClass.getSimpleName() + "_manager.html";
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
