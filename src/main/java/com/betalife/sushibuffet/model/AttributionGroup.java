package com.betalife.sushibuffet.model;

public class AttributionGroup extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String locale;

	private int productId;

	private Attribution attribution;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	@Override
	public String toString() {
		return "AttributionGroup [attribution=" + attribution + "], [productId=" + productId + "]" + ", id="
				+ id + "]" + ", locale=" + locale;
	}
}
