package com.betalife.sushibuffet.templete;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroUtil;

@Component
public class ReceiptTemplete extends AReceiptTemplete {
	@Value("${receipt.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public Map<String, Object> buildParam(Turnover turnover, List<Order> orders, String locale, Takeaway takeaway) {

		Map<String, Object> map = super.buildParam(turnover, orders, locale, takeaway);
		map.put("date", sdf.format(new Date()));

		boolean isTakeaway = DodoroUtil.isTakeaway(turnover);
		if (isTakeaway) {
			Integer takeawayId = turnover.getTakeawayId();
			map.put("takeawayNo", takeawayId);
			map.put("memo", takeaway.getMemo());

			Integer source = takeaway.getSource();
			map.put("source", source);
			if (source == 1) {
				map.put("deliveryPayment", takeaway.getDeliveryPayment());
				map.put("deliveryTimestamp", takeaway.getDeliveryTimestamp());
			}

		} else {
			int tableId = turnover.getTableId();
			map.put("tableNo", tableId);
		}

		return map;
	}

}
