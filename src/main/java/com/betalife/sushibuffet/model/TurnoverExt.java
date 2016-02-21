package com.betalife.sushibuffet.model;

import java.util.ArrayList;
import java.util.List;

public class TurnoverExt extends Turnover {

	private static final long serialVersionUID = 1L;

	private List<TurnoverAttribute> attributes;

	public List<TurnoverAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<TurnoverAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return super.toString() + ", Turnover Attributes [" + attributes + "]";
	}

	public void addAttribute(TurnoverAttribute turnoverAttribute) {
		if (attributes == null) {
			attributes = new ArrayList<TurnoverAttribute>();
		}
		attributes.add(turnoverAttribute);
	}

}
