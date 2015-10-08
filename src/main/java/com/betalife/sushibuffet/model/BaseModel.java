package com.betalife.sushibuffet.model;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "id: " + id;
	}

}
