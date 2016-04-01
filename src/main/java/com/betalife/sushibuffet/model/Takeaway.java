package com.betalife.sushibuffet.model;

import java.util.Date;

public class Takeaway extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date created;
	private Date updated;
	private String memo;
	private Boolean takeaway;
	private Boolean vaild;
	private Boolean printed;
	private Integer source;
	private Boolean delivery;
	private Turnover turnover;
	// @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private Date deliveryTimestamp;
	private Integer deliveryPayment;

	public Integer getDeliveryPayment() {
		return deliveryPayment;
	}

	public void setDeliveryPayment(Integer deliveryPayment) {
		this.deliveryPayment = deliveryPayment;
	}

	public Date getDeliveryTimestamp() {
		return deliveryTimestamp;
	}

	public void setDeliveryTimestamp(Date deliveryTimestamp) {
		this.deliveryTimestamp = deliveryTimestamp;
	}

	public Boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(Boolean delivery) {
		this.delivery = delivery;
	}

	public Boolean isVaild() {
		return vaild;
	}

	public void setVaild(Boolean vaild) {
		this.vaild = vaild;
	}

	public Boolean isPrinted() {
		return printed;
	}

	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Turnover getTurnover() {
		return turnover;
	}

	public void setTurnover(Turnover turnover) {
		this.turnover = turnover;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean isTakeaway() {
		return takeaway;
	}

	public void setTakeaway(Boolean takeaway) {
		this.takeaway = takeaway;
	}

	@Override
	public String toString() {
		return "Takeaway [takeaway=" + takeaway + ", memo=" + memo + ", id=" + id + "]";
	}
}
