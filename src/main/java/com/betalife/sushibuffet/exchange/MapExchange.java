package com.betalife.sushibuffet.exchange;

import java.util.Map;

public class MapExchange<T, V> extends BaseExchange {
	private Map<T, V> model;

	public Map<T, V> getModel() {
		return model;
	}

	public void setModel(Map<T, V> model) {
		this.model = model;
	}

}
