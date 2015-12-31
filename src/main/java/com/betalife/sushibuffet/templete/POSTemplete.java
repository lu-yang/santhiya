package com.betalife.sushibuffet.templete;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class POSTemplete<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected Template template;

	protected String templateFile;

	public POSTemplete(String templateFile) throws IOException {
		this.templateFile = templateFile;
		init();
	}

	public abstract void init() throws IOException;

	protected File getFile() throws IOException {
		File file;
		if (templateFile.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
			ClassPathResource resource = new ClassPathResource(
					templateFile.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length()));
			file = resource.getFile();
		} else {
			file = new File(templateFile);
		}
		return file;
	}

	public abstract T format(Map<String, Object> map) throws TemplateException, IOException;
}
