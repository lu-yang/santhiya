package com.betalife.sushibuffet.model;

import java.util.ArrayList;
import java.util.List;

public class AttributionGroup extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String locale;

	private int productId;

	private List<Attribution> attributions;

	private int attributionGroupType;

	private int attributionGroupStatus;

	private String attributionGroupName;

	public String getAttributionGroupName() {
		return attributionGroupName;
	}

	public void setAttributionGroupName(String attributionGroupName) {
		this.attributionGroupName = attributionGroupName;
	}

	public int getAttributionGroupType() {
		return attributionGroupType;
	}

	public void setAttributionGroupType(int attributionGroupType) {
		this.attributionGroupType = attributionGroupType;
	}

	public int getAttributionGroupStatus() {
		return attributionGroupStatus;
	}

	public void setAttributionGroupStatus(int attributionGroupStatus) {
		this.attributionGroupStatus = attributionGroupStatus;
	}

	public List<Attribution> getAttributions() {
		return attributions;
	}

	public void setAttributions(List<Attribution> attributions) {
		this.attributions = attributions;
	}

	public void addAttribution(Attribution att) {
		if (attributions == null) {
			attributions = new ArrayList<Attribution>();
		}
		attributions.add(att);
	}

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

	@Override
	public String toString() {
		return "AttributionGroup [productId=" + productId + "]" + ", id=" + id + "]" + ", locale=" + locale;
	}
}
