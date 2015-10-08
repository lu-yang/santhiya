package com.betalife.sushibuffet.model;

public class Attribution extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String locale;

	private int attributionPrice;

	private String attributionName;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getAttributionPrice() {
		return attributionPrice;
	}

	public void setAttributionPrice(int attributionPrice) {
		this.attributionPrice = attributionPrice;
	}

	public String getAttributionName() {
		return attributionName;
	}

	public void setAttributionName(String attributionName) {
		this.attributionName = attributionName;
	}

	@Override
	public String toString() {
		return "Attribution [attributionPrice=" + attributionPrice + "], [attributionName=" + attributionName
				+ "]" + ", id=" + id + "]" + ", locale=" + locale;
	}
}
