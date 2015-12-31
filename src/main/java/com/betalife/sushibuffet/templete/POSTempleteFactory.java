package com.betalife.sushibuffet.templete;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class POSTempleteFactory {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static POSTemplete<?> createPOSTemplete(String templateFile) throws IOException {
		if (templateFile.endsWith("txt")) {
			return new TxtPOSTemplete(templateFile);
		} else if (templateFile.endsWith("html")) {
			return new HtmlPOSTemplete(templateFile);
		}

		throw new IllegalArgumentException("Unsupported templete type: " + templateFile);
	}
}
