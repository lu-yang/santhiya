package com.betalife.sushibuffet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private int turnoverId;
	// private int productId;
	private int count;

	private Product product;
	private Turnover turnover;

	private String locale;

	private Date created;
	private Date updated;

	private List<OrderAttribution> orderAttributions;

	// public int getTurnoverId() {
	// return turnoverId;
	// }
	//
	// public void setTurnoverId(int turnoverId) {
	// this.turnoverId = turnoverId;
	// }
	//
	// public int getProductId() {
	// return productId;
	// }
	//
	// public void setProductId(int productId) {
	// this.productId = productId;
	// }

	public Date getCreated() {
		return created;
	}

	public List<OrderAttribution> getOrderAttributions() {
		return orderAttributions;
	}

	public void setOrderAttributions(List<OrderAttribution> orderAttributions) {
		this.orderAttributions = orderAttributions;
	}

	public void addOrderAttribution(OrderAttribution orderAttribution) {
		if (orderAttributions == null) {
			orderAttributions = new ArrayList<OrderAttribution>();
		}
		this.orderAttributions.add(orderAttribution);
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getCount() {
		return count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Turnover getTurnover() {
		return turnover;
	}

	public void setTurnover(Turnover turnover) {
		this.turnover = turnover;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Order copy() {
		Order copy = new Order();
		copy.count = count;
		copy.locale = locale;
		copy.product = product;
		copy.turnover = turnover;
		copy.created = created;
		copy.updated = updated;
		for (OrderAttribution one : orderAttributions) {
			copy.orderAttributions.add(one.copy());
		}
		return copy;
	}

	@Override
	public String toString() {
		return "Order [turnover=" + turnover + "], [product=" + product + "], count=" + count + ", id=" + id
				+ "]" + ", locale=" + locale;
	}

}
