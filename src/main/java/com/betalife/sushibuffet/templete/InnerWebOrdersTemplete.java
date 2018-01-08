package com.betalife.sushibuffet.templete;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InnerWebOrdersTemplete extends WebOrdersTemplete {
	@Value("${inner.weborders.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

}
