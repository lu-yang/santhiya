package com.betalife.sushibuffet.exchange;

import java.util.List;

public class ListExchange<T> extends BaseExchange {
	private List<T> model;

	public List<T> getModel() {
		return model;
	}

	public void setModel(List<T> model) {
		this.model = model;
	}

}
