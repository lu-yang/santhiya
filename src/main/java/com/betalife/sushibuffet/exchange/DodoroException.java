package com.betalife.sushibuffet.exchange;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DodoroException {

	private String type;

	private String message;

	private String stackTracePart;

	public DodoroException() {
	}

	public DodoroException(Throwable e) {
		message = e.getMessage();
		type = e.getClass().getName();

		StringWriter wr = new StringWriter();
		PrintWriter pw = new PrintWriter(wr);
		e.printStackTrace(pw);

		stackTracePart = wr.getBuffer().substring(0, 200);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTracePart() {
		return stackTracePart;
	}

	public void setStackTracePart(String stackTracePart) {
		this.stackTracePart = stackTracePart;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
