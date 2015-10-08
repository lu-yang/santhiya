package com.betalife.sushibuffet.util;

import static com.betalife.sushibuffet.util.DodoroUtil.HUNDRED;
import static com.betalife.sushibuffet.util.DodoroUtil.TEN_THOUSAND;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Taxgroups;
import com.betalife.sushibuffet.model.Turnover;

import freemarker.template.TemplateException;

@Component
public class LedgerTempletePOSUtil extends TempletePOSUtil {

	@Value("${ledger.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public Map<String, Object> buildParam(List<Order> orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		int total = 0;
		Map<String, Integer> kindTotalMap = new HashMap<String, Integer>();
		for (Order order : orders) {
			Turnover turnover = order.getTurnover();
			boolean takeaway = DodoroUtil.isTakeaway(turnover);

			Integer discount = turnover.getDiscount();
			// 0 表示免单，空表示无折扣
			discount = discount == null ? 100 : discount;

			Product product = order.getProduct();
			int count = order.getCount();
			int productPrice = product.getProductPrice();
			int subTotal = productPrice * count * (100 - discount);

			total += subTotal;
			String taxgroupId = product.getTaxgroupId() + "_" + takeaway;
			if (kindTotalMap.containsKey(taxgroupId)) {
				Integer kindTotal = kindTotalMap.get(taxgroupId);
				kindTotalMap.put(taxgroupId, kindTotal + subTotal);
			} else {
				kindTotalMap.put(taxgroupId, subTotal);
			}
		}

		map.put("total", DodoroUtil.getDisplayPrice(DodoroUtil.divide(total, TEN_THOUSAND)));

		Map<String, Taxgroups> taxgroupsMap = getTaxgroupMap();
		Taxgroups foodTax = taxgroupsMap.get(FOOD);
		Taxgroups alcoholTax = taxgroupsMap.get(ALCOHOL);

		putTotal(foodTax.getValue(), FOOD, getKindTotal(kindTotalMap, foodTax.getId() + "_" + false), map);

		putTotal(alcoholTax.getValue(), ALCOHOL,
				getKindTotal(kindTotalMap, alcoholTax.getId() + "_" + false), map);

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

	public String format_receipt_lines(List<Order> orders) throws TemplateException, IOException {
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}
		Map<String, Object> map = buildParam(orders);
		String html = format(map);

		return html;
	}

}
