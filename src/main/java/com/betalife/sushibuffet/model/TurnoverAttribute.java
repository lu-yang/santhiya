package com.betalife.sushibuffet.model;

public class TurnoverAttribute {

	public enum TurnoverAttributeName {
		MARK;
	}

	private static final long serialVersionUID = 1L;
	private Integer turnoverId;
	private String attributeName;
	private String attributeValue;

	public Integer getTurnoverId() {
		return turnoverId;
	}

	public void setTurnoverId(Integer turnoverId) {
		this.turnoverId = turnoverId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		return "Turnover Attributes [turnoverId: " + turnoverId + ", attributeName: " + attributeName
				+ ", attributeValue: " + attributeValue + "]";
	}

}
