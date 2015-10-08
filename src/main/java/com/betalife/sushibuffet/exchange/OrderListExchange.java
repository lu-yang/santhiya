package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.Order;

public class OrderListExchange extends BaseExchange {
	private Order[] list;

	public Order[] getList() {
		return list;
	}

	public void setList(Order[] list) {
		this.list = list;
	}

}
