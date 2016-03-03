package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.OrderProductGroup;

public class OrderProductGroupListExchange extends BaseExchange {
	private OrderProductGroup[] list;

	public OrderProductGroup[] getList() {
		return list;
	}

	public void setList(OrderProductGroup[] list) {
		this.list = list;
	}

}
