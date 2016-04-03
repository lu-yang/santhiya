package com.betalife.sushibuffet.templete;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;

@Component
public class WebOrdersTemplete extends AReceiptTemplete {
	@Value("${weborders.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public Map<String, Object> buildParam(Turnover turnover, List<Order> orders, String locale, Takeaway takeaway) {

		Map<String, Object> map = super.buildParam(turnover, orders, locale, takeaway);
		map.put("date", sdf.format(new Date()));

		Integer takeawayId = turnover.getTakeawayId();
		map.put("takeawayNo", takeawayId);
		map.put("memo", takeaway.getMemo());
		map.put("deliveryPayment", takeaway.getDeliveryPayment());
		map.put("deliveryTimestamp", takeaway.getDeliveryTimestamp());

		return map;
	}

}
