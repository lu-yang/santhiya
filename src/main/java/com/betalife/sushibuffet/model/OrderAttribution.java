package com.betalife.sushibuffet.model;

import java.util.Date;

public class OrderAttribution extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;

	private int orderId;
	private Attribution attribution;

	private Date created;
	private Date updated;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	public Date getCreated() {
		return created;
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

	public OrderAttribution copy() {
		OrderAttribution copy = new OrderAttribution();
		copy.count = count;
		copy.attribution = attribution;
		copy.orderId = orderId;
		copy.created = created;
		copy.updated = updated;

		return copy;
	}

	@Override
	public String toString() {
		return "Order [attribution=" + attribution + "], [orderId=" + orderId + "], count=" + count + ", id="
				+ id + "]";
	}

}
