package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.Product;

public class ProductListExchange extends BaseExchange {
	private Product[] list;

	public Product[] getList() {
		return list;
	}

	public void setList(Product[] list) {
		this.list = list;
	}

}
