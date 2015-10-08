package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.Category;

public class CategoryListExchange extends BaseExchange {
	private Category[] list;

	public Category[] getList() {
		return list;
	}

	public void setList(Category[] list) {
		this.list = list;
	}

}
