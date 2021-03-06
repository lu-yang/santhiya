package com.betalife.sushibuffet.templete;

import static com.betalife.sushibuffet.util.DodoroUtil.TEN_THOUSAND;
import static com.betalife.sushibuffet.util.DodoroUtil.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.betalife.sushibuffet.model.Attribution;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Taxgroups;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroUtil;

public abstract class AReceiptTemplete extends ContentTemplete {

	public Map<String, Object> buildParam(Turnover turnover, List<Order> orders, String locale, Takeaway takeaway) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int total = 0;
		Map<String, Integer> kindTotalMap = new HashMap<String, Integer>();
		Map<Integer, Category> categoryMap = getCategoryMap(locale);
		Map<Integer, Product> productMap = getProductMap(locale);
		for (Order order : orders) {
			Product product = order.getProduct();
			Category category = categoryMap.get(product.getCategoryId());
			String cateName = category == null ? "" : category.getName();

			Map<String, Object> one = new HashMap<String, Object>();
			one.put("pname", productMap.get(product.getId()).getProductName());
			int count = order.getCount();
			one.put("count", count + "");
			one.put("pnum", product.getProductNum());
			one.put("cname", cateName);
			int productPrice = product.getProductPrice();
			one.put("price", DodoroUtil.getDisplayPrice(productPrice));

			int attSum = 0;

			List<OrderAttribution> orderAttributions = order.getOrderAttributions();
			if (CollectionUtils.isNotEmpty(orderAttributions)) {
				List<Map<String, String>> attrList = new ArrayList<Map<String, String>>();
				one.put("attrList", attrList);
				for (OrderAttribution oa : orderAttributions) {
					Attribution attr = oa.getAttribution();
					Map<String, String> attrMap = new HashMap<String, String>();
					attrList.add(attrMap);
					attrMap.put("attrName", attr.getAttributionName());
					int aCount = oa.getCount();
					attrMap.put("attrCount", aCount + "");
					int attrPrice = attr.getAttributionPrice();
					attrMap.put("attrPrice", DodoroUtil.getDisplayPrice(attrPrice));
					attSum += aCount * attrPrice;
				}
			}
			one.put("attrSumPrice", DodoroUtil.getDisplayPrice(attSum));

			int subTotal = productPrice * count + attSum;
			one.put("subtotal", DodoroUtil.getDisplayPrice(subTotal));

			list.add(one);

			total += subTotal;
			String taxgroupId = product.getTaxgroupId() + "";
			if (kindTotalMap.containsKey(taxgroupId)) {
				Integer kindTotal = kindTotalMap.get(taxgroupId);
				kindTotalMap.put(taxgroupId, kindTotal + subTotal);
			} else {
				kindTotalMap.put(taxgroupId, subTotal);
			}
		}

		map.put("list", list);

		map.put("total", DodoroUtil.getDisplayPrice(total));

		Map<Object, Taxgroups> taxgroupsMap = getTaxgroupMap();
		Taxgroups foodTax = taxgroupsMap.get(FOOD);
		Taxgroups alcoholTax = taxgroupsMap.get(ALCOHOL);

		boolean isTakeaway = DodoroUtil.isTakeaway(turnover);
		if (isTakeaway) {
			putTotal(foodTax.getTakeaway(), FOOD, getKindTotal(kindTotalMap, foodTax.getId() + ""), map);

			putTotal(alcoholTax.getTakeaway(), ALCOHOL, getKindTotal(kindTotalMap, alcoholTax.getId() + ""), map);
		} else {
			putTotal(foodTax.getValue(), FOOD, getKindTotal(kindTotalMap, foodTax.getId() + ""), map);

			putTotal(alcoholTax.getValue(), ALCOHOL, getKindTotal(kindTotalMap, alcoholTax.getId() + ""), map);
		}

		Integer percent = turnover.getDiscount();
		if (percent == null) {
			map.put("discount", "No Discount");
			percent = 100;
		} else if (percent == 0) {
			map.put("discount", "Free");
		} else {
			map.put("discount", "-" + percent + "%");
			percent = 100 - percent;
		}

		map.put("discountPrice", DodoroUtil.divide(total * percent, TEN_THOUSAND));

		return map;
	}

	private BigDecimal getKindTotal(Map<String, Integer> kindTotalMap, String key) {
		Integer integer = kindTotalMap.get(key);
		return integer == null || integer == 0 ? ZERO : new BigDecimal(integer);
	}

}
