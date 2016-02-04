package com.betalife.sushibuffet.templete;

import static com.betalife.sushibuffet.util.DodoroUtil.HUNDRED;
import static com.betalife.sushibuffet.util.DodoroUtil.ONE;
import static com.betalife.sushibuffet.util.DodoroUtil.ZERO;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.dao.ProductMapper;
import com.betalife.sushibuffet.dao.TaxgroupsMapper;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Taxgroups;
import com.betalife.sushibuffet.model.Turnover;

public abstract class ContentTemplete {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private TaxgroupsMapper taxgroupsMapper;

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

	protected String templateFile;

	private static NumberFormat percentInstance;

	protected static final String TAKEAWAY_PREFIX = "takeaway_";
	protected static final String FOOD = "food";
	protected static final String ALCOHOL = "alcool";

	protected POSTemplete<?> posTemplete;

	public ContentTemplete() {
		percentInstance = NumberFormat.getPercentInstance();
		percentInstance.setMaximumFractionDigits(4);
	}

	protected abstract void setTemplateFile(String templateFile);

	@PostConstruct
	public void init() throws IOException {
		posTemplete = POSTempleteFactory.createPOSTemplete(templateFile);
	}

	public abstract Map<String, Object> buildParam(Turnover turnover, List<Order> orders, String locale,
			Takeaway takeaway) throws Exception;

	public Map<String, Map<String, Object>> buildBarnameParam(Turnover turnover, List<Order> orders,
			String locale, Takeaway takeaway) throws Exception {
		Map<String, Object> map = buildParam(turnover, orders, locale, takeaway);

		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		result.put("default", map);
		return result;
	}

	protected Map<Integer, Category> getCategoryMap(String locale) {
		Map<Integer, Category> categories = null;
		List<Category> list = categoryMapper.selectAll(locale);
		categories = new HashMap<Integer, Category>();
		for (Category category : list) {
			categories.put(category.getId(), category);
		}

		return categories;
	}

	protected Map<Integer, Product> getProductMap(String locale) {
		Map<Integer, Product> products = null;
		List<Product> list = productMapper.selectAll(locale);
		products = new HashMap<Integer, Product>();
		for (Product one : list) {
			products.put(one.getId(), one);
		}

		return products;
	}

	protected void putTotal(float tax, String kind, BigDecimal kindTotal, Map<String, Object> map) {
		String percent = percentInstance.format(tax);
		map.put(kind + "_tax", percent);
		if (kindTotal == null || kindTotal.equals(ZERO)) {
			map.put(kind + "_tax_paid", "0");
			map.put(kind + "_total_paid", "0");
			map.put(kind + "_paid", "0");
		} else {
			BigDecimal kindTaxRate = new BigDecimal(tax);
			BigDecimal kindTotalTax = kindTotal.multiply(kindTaxRate)
					.divide((kindTaxRate.add(ONE)).multiply(HUNDRED), 2, BigDecimal.ROUND_DOWN);
			map.put(kind + "_tax_paid", kindTotalTax.floatValue());
			BigDecimal kindTotalF = kindTotal.divide(HUNDRED, 2, BigDecimal.ROUND_DOWN);
			map.put(kind + "_total_paid", kindTotalF.floatValue());
			map.put(kind + "_paid", "" + kindTotalF.subtract(kindTotalTax).floatValue());
		}
	}

	protected Map<Object, Taxgroups> getTaxgroupMap() {
		List<Taxgroups> taxgroups = taxgroupsMapper.selectAll();
		Map<Object, Taxgroups> taxgroupMap = new HashMap<Object, Taxgroups>();
		for (Taxgroups taxgroup : taxgroups) {
			taxgroupMap.put(taxgroup.getName(), taxgroup);
			taxgroupMap.put(taxgroup.getId(), taxgroup);
		}
		return taxgroupMap;
	}

	public POSTemplete<?> getPosTemplete() {
		return posTemplete;
	}

}
