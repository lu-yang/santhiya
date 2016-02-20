package com.betalife.sushibuffet.templete;

import static com.betalife.sushibuffet.util.DodoroUtil.HUNDRED;
import static com.betalife.sushibuffet.util.DodoroUtil.TEN_THOUSAND;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.model.KeyValue;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Taxgroups;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroUtil;

@Component
public class LedgerTemplete extends ContentTemplete {

	@Value("${ledger.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public Map<String, Map<String, Object>> buildBarnameParam(Map<String, Object> map) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		result.put("default", map);
		return result;
	}

	public Map<String, Object> buildParam(Turnover nouse, List<Order> orders, String nouse2,
			Takeaway nouse3) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		int total = 0;
		Map<String, Integer> kindTotalMap = new HashMap<String, Integer>();
		Map<Integer, KeyValue<Turnover, Integer>> turnoverTotalMap = new HashMap<Integer, KeyValue<Turnover, Integer>>();
		Map<Object, Taxgroups> taxgroupsMap = getTaxgroupMap();
		if (!CollectionUtils.isEmpty(orders)) {
			for (Order order : orders) {
				Turnover turnover = order.getTurnover();
				Integer discount = turnover.getDiscount();
				// 0 表示免单，空表示无折扣
				if (discount == null) {
					discount = 100;
				} else if (discount > 0) {
					discount = 100 - discount;
				}

				Product product = order.getProduct();
				int count = order.getCount();
				int productPrice = product.getProductPrice();
				int attSum = 0;
				List<OrderAttribution> orderAttributions = order.getOrderAttributions();
				if (!CollectionUtils.isEmpty(orderAttributions)) {
					for (OrderAttribution oa : orderAttributions) {
						attSum += oa.getCount() * oa.getAttribution().getAttributionPrice();
					}
				}
				int subTotal = (productPrice * count + attSum) * discount;
				total += subTotal;

				boolean takeaway = DodoroUtil.isTakeaway(turnover);
				String taxgroupId = product.getTaxgroupId() + "_" + takeaway;

				if (kindTotalMap.containsKey(taxgroupId)) {
					Integer kindTotal = kindTotalMap.get(taxgroupId);
					kindTotalMap.put(taxgroupId, kindTotal + subTotal);
				} else {
					kindTotalMap.put(taxgroupId, subTotal);
				}

				KeyValue<Turnover, Integer> keyValue = null;
				if (!turnoverTotalMap.containsKey(turnover.getId())) {
					keyValue = new KeyValue<Turnover, Integer>(turnover, 0);
					turnoverTotalMap.put(turnover.getId(), keyValue);
				} else {
					keyValue = turnoverTotalMap.get(turnover.getId());
				}
				keyValue.setValue(keyValue.getValue() + subTotal);

			}
		}
		List<Map<String, Object>> turnovers = new ArrayList<Map<String, Object>>();
		map.put("turnovers", turnovers);
		for (KeyValue<Turnover, Integer> keyVaule : turnoverTotalMap.values()) {
			Turnover turnover = keyVaule.getKey();
			Integer value = keyVaule.getValue();
			Map<String, Object> one = new HashMap<String, Object>();
			one.put("paid", DodoroUtil.divide(value, TEN_THOUSAND));
			boolean takeaway = DodoroUtil.isTakeaway(turnover);
			one.put("takeaway", takeaway);
			if (takeaway) {
				one.put("no", turnover.getTakeawayId());
			} else {
				one.put("no", turnover.getTableId());
			}
			turnovers.add(one);
		}

		map.put("total", DodoroUtil.getDisplayPrice(DodoroUtil.divide(total, TEN_THOUSAND)));

		Taxgroups foodTax = taxgroupsMap.get(FOOD);
		Taxgroups alcoholTax = taxgroupsMap.get(ALCOHOL);

		putTotal(foodTax.getValue(), FOOD, getKindTotal(kindTotalMap, foodTax.getId() + "_" + false), map);

		putTotal(alcoholTax.getValue(), ALCOHOL, getKindTotal(kindTotalMap, alcoholTax.getId() + "_" + false),
				map);

		putTotal(foodTax.getTakeaway(), TAKEAWAY_PREFIX + FOOD,
				getKindTotal(kindTotalMap, foodTax.getId() + "_" + true), map);

		putTotal(alcoholTax.getTakeaway(), TAKEAWAY_PREFIX + ALCOHOL,
				getKindTotal(kindTotalMap, alcoholTax.getId() + "_" + true), map);

		return map;
	}

	private BigDecimal getKindTotal(Map<String, Integer> kindTotalMap, String key) {
		Integer integer = kindTotalMap.get(key);
		return DodoroUtil.divide(integer, HUNDRED);
	}

}
