package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.TakeawayExt;

public class TakeawayListExchange extends BaseExchange {
	private TakeawayExt[] list;

	public TakeawayExt[] getList() {
		return list;
	}

	public void setList(TakeawayExt[] list) {
		this.list = list;
	}

}
