package com.betalife.sushibuffet.model;

import java.util.ArrayList;
import java.util.List;

public class Product extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int categoryId;
	private String description;
	private int active;
	private String thumb;
	private int position;
	private String productName;
	private String productNum;
	private int minNum;
	private Integer num;
	private int taxgroupId;
	private int productPrice;
	private String locale;

	private List<AttributionGroup> attributionGroups;

	private String barName;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public List<AttributionGroup> getAttributionGroups() {
		return attributionGroups;
	}

	public void setAttributionGroups(List<AttributionGroup> attributionGroups) {
		this.attributionGroups = attributionGroups;
	}

	public void addAttributionGroup(AttributionGroup group) {
		if (attributionGroups == null) {
			attributionGroups = new ArrayList<AttributionGroup>();
		}
		attributionGroups.add(group);
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public int getTaxgroupId() {
		return taxgroupId;
	}

	public void setTaxgroupId(int taxgroupId) {
		this.taxgroupId = taxgroupId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	// public String getDisplayPrice() {
	// return DodoroUtil.getDisplayPrice(productPrice);
	// }

	@Override
	public String toString() {
		return "Product [categoryId=" + categoryId + ", description=" + description + ", active=" + active
				+ ", thumb=" + thumb + ", position=" + position + ", productName=" + productName
				+ ", productNum=" + productNum + ", minNum=" + minNum + ", num=" + num + ", taxgroupId="
				+ taxgroupId + ", productPrice=" + productPrice + ", id=" + id + "]";
	}

}
