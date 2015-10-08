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
	private boolean takeaway;

	private Turnover turnover;

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

	public boolean isTakeaway() {
		return takeaway;
	}

	public void setTakeaway(boolean takeaway) {
		this.takeaway = takeaway;
	}

	@Override
	public String toString() {
		return "Takeaway [takeaway=" + takeaway + ", memo=" + memo + ", id=" + id + "]";
	}
}
