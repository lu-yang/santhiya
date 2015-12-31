package com.betalife.sushibuffet.templete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

class HtmlPOSTemplete extends POSTemplete<byte[]> {

	private Html2ImageBytes html2ImageBytes = new Html2ImageBytes();

	public HtmlPOSTemplete(String templateFile) throws IOException {
		super(templateFile);
	}

	public void init() throws IOException {
		File file = getFile();
		template = new Template(null, new InputStreamReader(new FileInputStream(file), "UTF-8"), null);
		template.setEncoding("UTF-8");
	}

	public byte[] format(Map<String, Object> map) throws TemplateException, IOException {
		StringWriter out = new StringWriter();
		template.process(map, out);
		String html = out.toString();
		logger.debug("print order list:");
		logger.debug(html);
		html2ImageBytes.loadHtml(html);
		byte[] bytes = html2ImageBytes.getBytes();
		return bytes;
	}
}
