package com.betalife.sushibuffet.model;

public class OrderExt extends Order {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
