package com.betalife.sushibuffet.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.dao.AttributionGroupMapper;
import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.dao.DiningtableMapper;
import com.betalife.sushibuffet.dao.OrderAttributionMapper;
import com.betalife.sushibuffet.dao.OrderMapper;
import com.betalife.sushibuffet.dao.ProductMapper;
import com.betalife.sushibuffet.dao.SettingsMapper;
import com.betalife.sushibuffet.dao.TakeawayMapper;
import com.betalife.sushibuffet.dao.TurnoverMapper;
import com.betalife.sushibuffet.model.AttributionGroup;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.TakeawayExt;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.Constant;
import com.betalife.sushibuffet.util.LedgerTempletePOSUtil;
import com.betalife.sushibuffet.util.OrderTempleteHtmlUtil;
import com.betalife.sushibuffet.util.Printer;
import com.betalife.sushibuffet.util.ReceiptTempletePOSUtil;

@Service
public class CustomerManager {

	private static final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

	@Autowired
	private SettingsMapper settingsMapper;

	@Autowired
	private DiningtableMapper tableMapper;

	@Autowired
	private TurnoverMapper turnoverMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private TakeawayMapper takeawayMapper;

	@Autowired
	private AttributionGroupMapper attributionGroupMapper;

	@Autowired
	private OrderAttributionMapper orderAttributionMapper;

	@Resource(name = "printer")
	private Printer printer;

	@Autowired
	private ReceiptTempletePOSUtil receiptTempletePOSUtil;

	@Autowired
	private OrderTempleteHtmlUtil orderTempleteHtmlUtil;

	@Autowired
	private LedgerTempletePOSUtil ledgerTempletePOSUtil;

	@Value("${order.locale}")
	private String locale;

	@Value("${print.times}")
	private int times;

	private Constant constant;

	@Transactional(rollbackFor = Exception.class)
	public Turnover openTable(Turnover turnover) {
		turnover.setFirstTableId(turnover.getTableId());
		turnoverMapper.insert(turnover);

		return turnoverMapper.select(turnover);
	}

	public List<Category> getCategoriesByParentId(Category category) {
		return categoryMapper.selectByParentId(category);
	}

	public List<Diningtable> getTables() {
		return tableMapper.selectTables();
	}

	@Autowired
	public void setConstant(Constant constant) {
		this.constant = constant;
		Map<String, Object> settings = settingsMapper.select();
		constant.setCategoryRootUrl((String) settings.get("category_url"));
		constant.setProductRootUrl((String) settings.get("product_url"));
	}

	public Constant getConstant() {
		return constant;
	}

	public List<Product> getProductsByCategoryId(Product product) {
		List<Product> products = productMapper.selectByCategoryId(product);

		Map<Integer, Product> map = new HashMap<Integer, Product>();
		for (Product one : products) {
			int id = one.getId();
			map.put(id, one);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", map.keySet());
		params.put("locale", product.getLocale());
		List<AttributionGroup> attributionGroups = attributionGroupMapper.selectByProductIds(params);

		for (AttributionGroup one : attributionGroups) {
			int productId = one.getProductId();
			Product parent = map.get(productId);
			parent.addAttribution(one.getAttribution());
		}
		return products;
	}

	@Transactional(rollbackFor = Exception.class)
	public Turnover takeOrders(List<Order> orders) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			throw new IllegalArgumentException("Check Failed: orders is empty.");
		}

		Turnover turnover = orders.get(0).getTurnover();
		turnover = turnoverMapper.select(turnover);
		if (turnover == null) {
			throw new IllegalArgumentException("Check Failed: turnover is empty.");
		}

		Date now = new Date();
		for (Order o : orders) {
			o.setCreated(now);
			orderMapper.insert(o);

			List<OrderAttribution> orderAttributions = o.getOrderAttributions();
			for (OrderAttribution oa : orderAttributions) {
				oa.setOrderId(o.getId());
				oa.setCreated(now);
				orderAttributionMapper.insert(oa);
			}
		}

		List<byte[]> imgs = orderTempleteHtmlUtil.format_order_lines(orders, locale);
		List<Object> list = new ArrayList<Object>();
		for (byte[] img : imgs) {
			list.add(img);
			list.add(printer.getCutPaper());
		}
		print(list, false, times);

		return turnover;
	}

	public List<Order> getOrders(Order order) {
		List<Order> orders = orderMapper.selectOrdersByTurnover(order);
		fillOrderAttribution(order.getLocale(), orders);
		return orders;
	}

	public List<Order> getExtOrders(Order order) {
		List<Order> orders = orderMapper.selectExtOrdersByTurnover(order);
		fillOrderAttribution(order.getLocale(), orders);
		return orders;
	}

	private void fillOrderAttribution(String locale, List<Order> orders) {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		Map<Integer, Order> map = new HashMap<Integer, Order>();
		for (Order one : orders) {
			int id = one.getId();
			map.put(id, one);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", map.keySet());
		params.put("locale", locale);
		List<OrderAttribution> orderAttributions = orderAttributionMapper.selectByOrderIds(params);

		for (OrderAttribution one : orderAttributions) {
			int orderId = one.getOrderId();
			Order parent = map.get(orderId);
			parent.addOrderAttribution(one);
		}
	}

	public Map<String, Object> getOrdersByDate(Date from, Date to) throws Exception {
		Map<String, Date> param = new HashMap<String, Date>();
		param.put("from", from);
		param.put("to", to);
		List<Order> orders = orderMapper.selectOrdersByDate(param);
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}
		fillOrderAttribution(locale, orders);
		Map<String, Object> map = ledgerTempletePOSUtil.buildParam(orders);
		String html = ledgerTempletePOSUtil.format(map);
		List<Object> list = new ArrayList<Object>();
		list.add(html);
		list.add(printer.getCutPaper());
		print(list, false, 1);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(Turnover t) {
		turnoverMapper.update(t);
	}

	@Transactional(rollbackFor = Exception.class)
	public void remove(Takeaway t) {
		t = takeawayMapper.select(t);
		Turnover turnover = t.getTurnover();
		remove(turnover);
		takeawayMapper.delete(t);
	}

	@Transactional(rollbackFor = Exception.class)
	public void remove(Turnover t) {
		Order order = new Order();
		order.setTurnover(t);
		orderMapper.delete(order);
		orderAttributionMapper.delete(order);
		turnoverMapper.delete(t);
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(Takeaway t, boolean checkout) {
		if (checkout) {
			Turnover turnover = t.getTurnover();
			turnover.setCheckout(true);
			update(turnover);
		}

		takeawayMapper.update(t);
	}

	@Transactional(rollbackFor = Exception.class)
	public void add(Takeaway takeaway) {
		takeawayMapper.insert(takeaway);

		Turnover turnover = new Turnover();
		turnover.setTakeawayId(takeaway.getId());
		turnover.setTableId(0);
		turnover.setFirstTableId(0);
		turnoverMapper.insert(turnover);

		takeaway.setTurnover(turnover);
	}

	public List<TakeawayExt> getTakeaways() {
		return takeawayMapper.selectTodayTakeaways();
	}

	public void printOrders(Order model, boolean kitchen) throws Exception {
		List<Order> orders = getOrders(model);
		if (CollectionUtils.isEmpty(orders)) {
			logger.info("there is no order to print." + model);
		}
		fillOrderAttribution(model.getLocale(), orders);

		List<Object> list = new ArrayList<Object>();
		if (kitchen) {
			List<byte[]> imgs = orderTempleteHtmlUtil.format_order_lines(orders, locale);
			for (byte[] img : imgs) {
				list.add(img);
				list.add(printer.getCutPaper());
			}
			print(list, false, times);
		} else {
			// productId
			Map<Integer, Order> map = new HashMap<Integer, Order>();
			// productId-attId
			Map<String, OrderAttribution> attMap = new HashMap<String, OrderAttribution>();
			for (Order order : orders) {
				int id = order.getProduct().getId();
				if (map.containsKey(id)) {
					Order one = map.get(id);
					one.setCount(one.getCount() + order.getCount());
					List<OrderAttribution> orderAttributions = order.getOrderAttributions();
					for (OrderAttribution orderAttribution : orderAttributions) {
						String key = id + "-" + orderAttribution.getId();
						if (attMap.containsKey(key)) {
							OrderAttribution value = attMap.get(key);
							value.setCount(value.getCount() + orderAttribution.getCount());
						} else {
							one.addOrderAttribution(orderAttribution);
							attMap.put(key, orderAttribution);
						}
					}
				} else {
					Order one = order.copy();
					map.put(id, one);
					List<OrderAttribution> orderAttributions = one.getOrderAttributions();
					for (OrderAttribution orderAttribution : orderAttributions) {
						attMap.put(id + "-" + orderAttribution.getId(), orderAttribution);
					}
				}
			}
			Collection<Order> values = map.values();

			Turnover turnover = turnoverMapper.select(model.getTurnover());
			String content = receiptTempletePOSUtil.format_receipt_lines(new ArrayList<Order>(values),
					model.getLocale(), turnover);
			list.add(content);
			list.add(printer.getCutPaper());
			print(list, true, times);
		}
	}

	synchronized private void print(List<?> list, boolean logo, int times) throws Exception {
		printer.print(list, logo, times);
	}

	public List<Order> selectOrders(List<Order> orders) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Order order : orders) {
			int id = order.getId();
			ids.add(id);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("locale", locale);
		List<Order> ordersWithInfo = orderMapper.selectOrders(params);
		fillOrderAttribution(locale, ordersWithInfo);
		return ordersWithInfo;
	}

	// synchronized private void print(byte[] img, int times) throws Exception {
	// printer.print(img, times);
	// }
	@Transactional(rollbackFor = Exception.class)
	public void clear() {
		orderAttributionMapper.deleteAll();
		orderMapper.deleteAll();
		turnoverMapper.deleteAll();
		takeawayMapper.deleteAll();
	}

}
