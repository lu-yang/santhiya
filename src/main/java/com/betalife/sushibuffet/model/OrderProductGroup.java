package com.betalife.sushibuffet.model;

import java.util.Date;

public class OrderProductGroup extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Product product;

	private Order order;

	// private Integer productId;
	//
	// private Integer orderId;

	private Date updated;

	private Integer status;

	private Date served;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getServed() {
		return served;
	}

	public void setServed(Date served) {
		this.served = served;
	}

	@Override
	public String toString() {
		return "OrderProductGroup [product=" + product + "], [order=" + order + "], status=" + status
				+ ", updated=" + updated + "]" + ", served=" + served;
	}

}
