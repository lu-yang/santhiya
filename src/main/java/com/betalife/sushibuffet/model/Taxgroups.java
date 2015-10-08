package com.betalife.sushibuffet.model;

public class Taxgroups extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private float value;
	private float takeaway;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Taxgroups [name=" + name + ", value=" + value + ", id=" + id + "]";
	}

	public float getTakeaway() {
		return takeaway;
	}

	public void setTakeaway(float takeaway) {
		this.takeaway = takeaway;
	}

}
