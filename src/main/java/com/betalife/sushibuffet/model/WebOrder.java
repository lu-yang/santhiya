package com.betalife.sushibuffet.model;

import java.util.List;

public class WebOrder {
	private Takeaway takeaway;
	private List<Order> orders;

	public Takeaway getTakeaway() {
		return takeaway;
	}

	public void setTakeaway(Takeaway takeaway) {
		this.takeaway = takeaway;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
