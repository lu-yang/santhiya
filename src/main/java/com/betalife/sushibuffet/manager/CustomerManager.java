package com.betalife.sushibuffet.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.betalife.sushibuffet.print.PrintManager;
import com.betalife.sushibuffet.util.Constant;
import com.betalife.sushibuffet.util.LedgerTempletePOSUtil;

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

	@Autowired
	private LedgerTempletePOSUtil ledgerTempletePOSUtil;

	@Value("${order.locale}")
	private String locale;

	private Constant constant;

	@Autowired
	private PrintManager printManager;

	@Transactional(rollbackFor = Exception.class)
	public Turnover openTable(Turnover turnover) {
		turnover.setFirstTableId(turnover.getTableId());
		turnoverMapper.insert(turnover);

		return turnoverMapper.select(turnover);
	}

	public Turnover get(Turnover turnover) {
		return turnoverMapper.select(turnover);
	}

	public List<Turnover> getTurnoverList(Map<String, Object> params) {
		return turnoverMapper.selectList(params);
	}

	public Map<String, Object> getTurnoverWithTotalPrice(int turnoverId) {
		Turnover turnover = new Turnover();
		turnover.setId(turnoverId);
		turnover = get(turnover);

		Order order = new Order();
		order.setTurnover(turnover);
		List<Order> orders = getOrders(order);
		Map<String, Object> map = ledgerTempletePOSUtil.buildParam(null, orders, null);
		map.put("turnover", turnover);
		return map;
	}

	public List<Map<String, Object>> getTurnoverListWithTotalPrice(Map<String, Object> params) {
		List<Turnover> list = getTurnoverList(params);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Turnover turnover : list) {
			Order order = new Order();
			order.setTurnover(turnover);
			List<Order> orders = getOrders(order);
			Map<String, Object> map = ledgerTempletePOSUtil.buildParam(null, orders, null);
			map.put("turnover", turnover);
			result.add(map);
		}
		return result;
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
		if (CollectionUtils.isNotEmpty(attributionGroups)) {
			for (AttributionGroup one : attributionGroups) {
				int productId = one.getProductId();
				Product parent = map.get(productId);
				parent.addAttributionGroup(one);
			}
		}

		return products;
	}

	@Transactional(rollbackFor = Exception.class)
	public Turnover takeOrders(int turnoverId, List<Order> orders, boolean isPrint) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			throw new IllegalArgumentException("Check Failed: orders is empty.");
		}

		Turnover turnover = new Turnover();
		turnover.setId(turnoverId);
		turnover = turnoverMapper.select(turnover);
		if (turnover == null) {
			throw new IllegalArgumentException("Check Failed: turnover is empty.");
		}

		Date now = new Date();
		for (Order o : orders) {
			o.setCreated(now);
			o.setTurnover(turnover);
			orderMapper.insert(o);

			List<OrderAttribution> orderAttributions = o.getOrderAttributions();
			if (CollectionUtils.isNotEmpty(orderAttributions)) {
				for (OrderAttribution oa : orderAttributions) {
					oa.setOrderId(o.getId());
					oa.setCreated(now);
					orderAttributionMapper.insert(oa);
				}
			}
		}

		if (isPrint) {
			printManager.printOrders(turnover, orders, locale, false);
		}
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
		if (CollectionUtils.isNotEmpty(orderAttributions)) {
			for (OrderAttribution one : orderAttributions) {
				int orderId = one.getOrderId();
				Order parent = map.get(orderId);
				parent.addOrderAttribution(one);
			}
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

		Map<String, Object> map = ledgerTempletePOSUtil.buildParam(null, orders, null);
		printManager.printLedger(map);
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

		Turnover turnover = turnoverMapper.select(model.getTurnover());

		if (kitchen) {
			printManager.printOrders(turnover, orders, locale, false);
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
					if (CollectionUtils.isNotEmpty(orderAttributions)) {
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
					}
				} else {
					Order one = order.copy();
					map.put(id, one);
					List<OrderAttribution> orderAttributions = one.getOrderAttributions();
					if (CollectionUtils.isNotEmpty(orderAttributions)) {
						for (OrderAttribution orderAttribution : orderAttributions) {
							attMap.put(id + "-" + orderAttribution.getId(), orderAttribution);
						}
					}

				}
			}

			printManager.printReceipt(turnover, new ArrayList<Order>(map.values()), model.getLocale(), true);
		}
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
