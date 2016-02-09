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
import com.betalife.sushibuffet.dao.KitchenOrderMapper;
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
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.print.PrintManager;
import com.betalife.sushibuffet.templete.LedgerTemplete;
import com.betalife.sushibuffet.util.Constant;
import com.betalife.sushibuffet.util.DodoroUtil;

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
	private KitchenOrderMapper kitchenOrderMapper;

	@Autowired
	private AttributionGroupMapper attributionGroupMapper;

	@Autowired
	private OrderAttributionMapper orderAttributionMapper;

	@Autowired
	private LedgerTemplete ledgerTemplete;

	@Value("${order.locale}")
	private String locale;

	@Value("${kitchen.locale}")
	private String kitchenLocale;

	private Constant constant;

	@Autowired
	private PrintManager printManager;

	@Transactional(rollbackFor = Exception.class)
	public Turnover openTable(Turnover turnover) {
		Integer tableId = turnover.getTableId();
		List<Diningtable> tables = tableMapper.selectTables(tableId);
		if (CollectionUtils.isEmpty(tables) || tables.get(0).getTurnover() == null
				|| tables.get(0).getTurnover().isCheckout()) {
			turnover.setFirstTableId(tableId);
			turnoverMapper.insert(turnover);
			return turnoverMapper.select(turnover);
		} else {
			return tables.get(0).getTurnover();
		}

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
		Map<String, Object> map = ledgerTemplete.buildParam(null, orders, null, null);
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
			Map<String, Object> map = ledgerTemplete.buildParam(null, orders, null, null);
			map.put("turnover", turnover);
			result.add(map);
		}
		return result;
	}

	public List<Category> getCategoriesByParentId(Category category) {
		return categoryMapper.selectByParentId(category);
	}

	public List<Diningtable> getTables(Integer tableId) {
		return tableMapper.selectTables(tableId);
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
			if (o.getId() == 0) {
				o.setPrinted(isPrint);
				o.setCreated(now);
				o.setTurnover(turnover);
				o.setModified(0);
				orderMapper.insert(o);

				List<OrderAttribution> orderAttributions = o.getOrderAttributions();
				if (CollectionUtils.isNotEmpty(orderAttributions)) {
					for (OrderAttribution oa : orderAttributions) {
						oa.setOrderId(o.getId());
						oa.setCreated(now);
						orderAttributionMapper.insert(oa);
					}
				}
			} else {
				// Map<String, Integer> params = new HashMap<String, Integer>();
				// params.put("orderId", o.getId());
				// orderAttributionMapper.delete(params);

				Order orderCopy = orderMapper.select(o);
				int count = orderCopy.getCount() + o.getCount();
				// Modified: 0:未修改数量；1：加菜；2：减菜；3：消菜
				if (count == 0) {
					orderMapper.delete(orderCopy);
					o.setOrderAttributions(null);
					o.setModified(3);
				} else {
					Order model = new Order();
					model.setId(o.getId());
					model.setCount(count);
					// model.setPrinted(isPrint);
					orderMapper.update(model);
					o.setModified(o.getCount() > 0 ? 1 : 2);
				}

				List<OrderAttribution> orderAttributions = o.getOrderAttributions();
				if (CollectionUtils.isNotEmpty(orderAttributions)) {
					Map<String, Integer> params = new HashMap<String, Integer>();
					for (OrderAttribution oa : orderAttributions) {
						OrderAttribution orderAttributionCopy = orderAttributionMapper.select(oa);
						int orderAttributionCount = orderAttributionCopy.getCount() + oa.getCount();
						if (orderAttributionCount == 0) {
							params.put("id", oa.getId());
							orderAttributionMapper.delete(params);
							oa.setModified(3);
						} else {
							OrderAttribution model = new OrderAttribution();
							model.setId(oa.getId());
							model.setCount(orderAttributionCount);
							orderAttributionMapper.update(model);
							oa.setModified(oa.getCount() > 0 ? 1 : 2);
						}
					}
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

	public Map<String, Object> ledger(Date from, Date to, boolean isPrint) throws Exception {
		Map<String, Date> param = new HashMap<String, Date>();
		param.put("from", from);
		param.put("to", to);
		List<Order> orders = orderMapper.selectOrdersByDate(param);

		fillOrderAttribution(locale, orders);

		Map<String, Object> map = ledgerTemplete.buildParam(null, orders, null, null);
		if (isPrint) {
			Map<String, Map<String, Object>> barnameParam = ledgerTemplete.buildBarnameParam(map);
			printManager.printLedger(barnameParam);
		}
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
		orderMapper.deleteByTurnoverId(order);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("turnoverId", t.getId());
		orderAttributionMapper.delete(params);
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

	@Transactional(rollbackFor = Exception.class)
	public void convert(Takeaway takeaway) {
		takeawayMapper.insert(takeaway);

		Turnover turnover = takeaway.getTurnover();
		turnover.setTakeawayId(takeaway.getId());
		turnover.setTableId(0);
		turnover.setFirstTableId(0);
		turnoverMapper.update(turnover);

	}

	public List<Map<String, Object>> getTakeaways(Date from, Date to) {
		Map<String, Date> param = new HashMap<String, Date>();
		param.put("from", from);
		param.put("to", to);
		List<Takeaway> list = takeawayMapper.selectAll(param);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Takeaway takeaway : list) {
			Order order = new Order();
			Turnover turnover = takeaway.getTurnover();
			order.setTurnover(turnover);
			List<Order> orders = getOrders(order);
			Map<String, Object> map = ledgerTemplete.buildParam(null, orders, null, null);
			map.put("takeaway", takeaway);
			result.add(map);
		}
		return result;
	}

	public Takeaway get(Takeaway t) {
		return takeawayMapper.select(t);
	}

	public void printKitchenOrders(List<Order> orders, Turnover turnover) throws Exception {
		List<Order> list = new ArrayList<Order>();
		for (Order order : orders) {
			if (!order.getPrinted()) {
				list.add(order);
			}
		}
		if (CollectionUtils.isNotEmpty(list)) {
			orderMapper.updatePrint(list);
		}
		printManager.printOrders(turnover, orders, locale, false);
	}

	public void printOrders(List<Order> orders, Turnover turnover, String locale) throws Exception {
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
		Takeaway takeaway = null;
		if (DodoroUtil.isTakeaway(turnover)) {
			takeaway = new Takeaway();
			takeaway.setId(turnover.getTakeawayId());
			takeaway = takeawayMapper.select(takeaway);
		}
		printManager.printReceipt(turnover, new ArrayList<Order>(map.values()), locale, true, takeaway);
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

	public List<Order> selectColdDishes() {
		List<Order> list = kitchenOrderMapper.selectColdDishes();
		fillOrderAttribution(kitchenLocale, list);
		return list;
	}

	public List<Order> selectHotDishes() {
		List<Order> list = kitchenOrderMapper.selectHotDishes();
		fillOrderAttribution(kitchenLocale, list);
		return list;
	}

	public List<Order> selectServedDishes() {
		List<Order> list = kitchenOrderMapper.selectServedDishes();
		fillOrderAttribution(kitchenLocale, list);
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void reminderOrder(int id) {
		Order o = new Order();
		o.setId(id);
		o = orderMapper.select(o);
		o.setStatus(o.getStatus() + 1);
		orderMapper.update(o);
	}

	@Transactional(rollbackFor = Exception.class)
	public void serveOrder(int id, int status) {
		Order o = new Order();
		o.setId(id);
		o.setStatus(status);
		orderMapper.update(o);
	}

}
