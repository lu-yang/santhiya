package com.betalife.sushibuffet.model;

public class Category extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean active;
	private int parentId;
	private String name;
	private int levelDepth;
	private int position;
	private String description;
	private String thumb;
	private int view;
	private String locale;
	private String barName;

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevelDepth() {
		return levelDepth;
	}

	public void setLevelDepth(int levelDepth) {
		this.levelDepth = levelDepth;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "Category [active=" + active + ", parentId=" + parentId + ", name=" + name + ", levelDepth="
				+ levelDepth + ", position=" + position + ", description=" + description + ", thumb=" + thumb
				+ ", view=" + view + ", id=" + id + "]";
	}

}
