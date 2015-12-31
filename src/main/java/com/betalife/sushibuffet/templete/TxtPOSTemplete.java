package com.betalife.sushibuffet.templete;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

class TxtPOSTemplete extends POSTemplete<String> {

	private final static String ESC_STR = "\\u001b";
	private final static String ESC = "\u001b";

	private final static String NL_STR = "\\n";
	private final static String NL = "\n";

	public TxtPOSTemplete(String templateFile) throws IOException {
		super(templateFile);
	}

	public void init() throws IOException {
		File file = getFile();
		String content = FileUtils.readFileToString(file);
		content = content.replace(ESC_STR, ESC);
		content = content.replace(NL_STR, NL);

		template = new Template(null, new StringReader(content), null);
		template.setEncoding("UTF-8");

	}

	public String format(Map<String, Object> map) throws TemplateException, IOException {
		StringWriter out = new StringWriter();
		template.process(map, out);
		String html = out.toString();
		logger.debug(html);
		return html;
	}
}
