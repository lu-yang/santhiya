package com.betalife.sushibuffet.templete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.model.Attribution;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroUtil;

@Component
public class OrderTemplete extends ContentTemplete {

	public static void main(String[] args) throws IOException {

		List<Order> orders = new ArrayList<Order>();
		Order o = new Order();
		o.setCount(1);
		Product product = new Product();
		product.setProductName("shui");
		product.setProductPrice(2322);
		o.setProduct(product);
		orders.add(o);
		orders.add(o);
		orders.add(o);

		OrderTemplete name = new OrderTemplete();
		name.init();
	}

	public Map<String, Object> buildParam(Turnover turnover, List<Order> orders, String locale,
			Takeaway nouse) throws Exception {
		return null;
	}

	// <barname, paramMap>
	public Map<String, Map<String, Object>> buildBarnameParam(Turnover turnover, List<Order> orders,
			String locale, Takeaway nouse) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}

		Map<Integer, Category> categoryMap = getCategoryMap(locale);
		Map<Integer, Product> productMap = getProductMap(locale);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		boolean takeaway = DodoroUtil.isTakeaway(turnover);
		if (takeaway) {
			Integer takeawayId = turnover.getTakeawayId();
			map.put("takeawayNo", takeawayId);
		} else {
			int tableId = turnover.getTableId();
			map.put("tableNo", tableId);
		}

		Map<String, List<Map<String, Object>>> barNameMap = new HashMap<String, List<Map<String, Object>>>();

		for (Order order : orders) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Product product = order.getProduct();
			Category category = categoryMap.get(product.getCategoryId());
			if (category == null) {
				logger.error("没有CategoryId是" + product.getCategoryId() + "的Category");
				break;
			}
			String cateName = category.getName();
			Map<String, Object> one = new HashMap<String, Object>();
			one.put("pname", productMap.get(product.getId()).getProductName());
			one.put("count", order.getCount() > 0 ? order.getCount() : -order.getCount());
			one.put("pnum", product.getProductNum());
			one.put("cname", cateName);
			one.put("modified", order.getModified());

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
				}
			}

			list.add(one);

			String barName = category.getBarName();
			// String barName = product.getBarName();
			if (StringUtils.isEmpty(barName)) {
				barName = "empty";
			}
			String[] barnames = barName.split(",");
			for (String bn : barnames) {
				bn = bn.trim();
				if (barNameMap.containsKey(bn)) {
					List<Map<String, Object>> values = barNameMap.get(bn);
					values.addAll(list);
				} else {
					barNameMap.put(bn, list);
				}
			}
		}

		Map<String, Map<String, Object>> printerMap = new HashMap<String, Map<String, Object>>();
		Set<String> keySet = barNameMap.keySet();
		for (String barname : keySet) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("barname", barname);
			param.put("list", barNameMap.get(barname));
			param.putAll(map);

			// byte[] bytes = posTemplete.format(map);
			printerMap.put(barname, param);
		}

		return printerMap;
	}

	@Value("${order.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

}
