package com.betalife.sushibuffet.print;

import java.util.List;

public interface Printer {

	void print(List<?> lines, boolean logo, int times) throws Exception;

	// void print(List<String> lines, int times) throws Exception;
	//
	// void print(byte[] img, int times) throws Exception;

	void setLogo(String logo);

	void setLogicName(String logicName);

	String getCutPaper();
}
