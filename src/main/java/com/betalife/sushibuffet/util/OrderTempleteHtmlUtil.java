package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.model.Attribution;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Turnover;

import freemarker.template.Template;

@Component
public class OrderTempleteHtmlUtil extends TempleteUtil {

	private Html2ImageBytes html2ImageBytes = new Html2ImageBytes();

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

		OrderTempleteHtmlUtil name = new OrderTempleteHtmlUtil();
		name.init();
	}

	@PostConstruct
	public void init() throws IOException {
		File file = getFile();
		template = new Template(null, new InputStreamReader(new FileInputStream(file), "UTF-8"), null);
		template.setEncoding("UTF-8");
	}

	public Map<String, byte[]> buildParam(Turnover turnover, List<Order> orders, String locale)
			throws Exception {
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
			Product product = order.getProduct();
			Category category = categoryMap.get(product.getCategoryId());
			if (category == null) {
				logger.error("没有CategoryId是" + product.getCategoryId() + "的Category");
				break;
			}
			String cateName = category.getName();
			String barName = category.getBarName();
			List<Map<String, Object>> list = null;
			if (barNameMap.containsKey(barName)) {
				list = barNameMap.get(barName);
			} else {
				list = new ArrayList<Map<String, Object>>();
				barNameMap.put(barName, list);
			}

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
		}

		Map<String, byte[]> printerMap = new HashMap<String, byte[]>();
		Set<String> keySet = barNameMap.keySet();
		for (String barname : keySet) {
			map.put("barname", barname);
			map.put("list", barNameMap.get(barname));

			String html = format(map);
			System.out.println("print order list:");
			System.out.println(html);
			html2ImageBytes.loadHtml(html);
			byte[] bytes = html2ImageBytes.getBytes();
			printerMap.put(barname, bytes);
		}

		return printerMap;
	}

	@Value("${order.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

}
