package com.betalife.sushibuffet.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalife.santhiya.util.UpdateClassify;
import com.betalife.sushibuffet.dao.AttributionGroupMapper;
import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.dao.DiningtableMapper;
import com.betalife.sushibuffet.dao.KitchenOrderMapper;
import com.betalife.sushibuffet.dao.OrderAttributionMapper;
import com.betalife.sushibuffet.dao.OrderMapper;
import com.betalife.sushibuffet.dao.OrderProductGroupMapper;
import com.betalife.sushibuffet.dao.ProductGroupMapper;
import com.betalife.sushibuffet.dao.ProductMapper;
import com.betalife.sushibuffet.dao.SettingsMapper;
import com.betalife.sushibuffet.dao.TakeawayMapper;
import com.betalife.sushibuffet.dao.TurnoverAttributeMapper;
import com.betalife.sushibuffet.dao.TurnoverMapper;
import com.betalife.sushibuffet.model.AttributionGroup;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.KeyValue;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;
import com.betalife.sushibuffet.model.OrderProductGroup;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.model.TurnoverAttribute;
import com.betalife.sushibuffet.model.TurnoverExt;
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
	private TurnoverAttributeMapper turnoverAttributeMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductGroupMapper productGroupMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderProductGroupMapper orderProductGroupMapper;

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

		List<TurnoverAttribute> turnoverAttributes = turnoverAttributeMapper.selectListByTurnovers(list);
		Map<Integer, List<TurnoverAttribute>> attributeMap = new HashMap<Integer, List<TurnoverAttribute>>();
		for (TurnoverAttribute turnoverAttribute : turnoverAttributes) {
			Integer turnoverId = turnoverAttribute.getTurnoverId();
			List<TurnoverAttribute> values = attributeMap.get(turnoverId);
			if (values == null) {
				values = new ArrayList<TurnoverAttribute>();
				attributeMap.put(turnoverId, values);
			}
			values.add(turnoverAttribute);
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Turnover turnover : list) {
			Order order = new Order();
			List<TurnoverAttribute> attributes = attributeMap.get(turnover.getId());
			if (attributes != null) {
				TurnoverExt turnoverExt = turnover.toTurnoverExt();
				turnoverExt.setAttributes(attributes);
				turnover = turnoverExt;
			}
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

	public Map<Integer, List<Integer>> getProductGroup() {
		List<KeyValue<Integer, Integer>> all = productGroupMapper.selectAll();
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (KeyValue<Integer, Integer> keyValue : all) {
			Integer key = keyValue.getKey();
			if (map.containsKey(key)) {
				List<Integer> list = map.get(key);
				list.add(keyValue.getValue());
			} else {
				List<Integer> list = new ArrayList<Integer>();
				list.add(keyValue.getValue());
				map.put(key, list);
			}
		}
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	public Turnover takeOrders(int turnoverId, List<Order> orders, boolean isPrint) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			throw new IllegalArgumentException("Check Failed: orders is empty.");
		}

		Map<Integer, List<Integer>> productGroup = getProductGroup();

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

				int productId = o.getProduct().getId();
				if (productGroup.containsKey(productId)) {
					List<Integer> list = productGroup.get(productId);
					OrderProductGroup opg = new OrderProductGroup();
					for (Integer id : list) {
						opg.setOrder(o);
						Product p = new Product();
						p.setId(id);
						opg.setProduct(p);
						orderProductGroupMapper.insert(opg);
					}
				}

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
					orderProductGroupMapper.deleteByOrderId(orderCopy);
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
		fillOrderAttribution(order.getLocale(), orders.toArray(new Order[0]));
		return orders;
	}

	public List<Order> getExtOrders(Order order) {
		List<Order> orders = orderMapper.selectExtOrdersByTurnover(order);
		fillOrderAttribution(order.getLocale(), orders.toArray(new Order[0]));
		return orders;
	}

	private void fillOrderAttribution(String locale, OrderProductGroup[] groups) {
		if (ArrayUtils.isEmpty(groups)) {
			return;
		}
		List<Order> orders = new ArrayList<Order>();
		for (OrderProductGroup one : groups) {
			Order order = one.getOrder();
			orders.add(order);
		}
		fillOrderAttribution(locale, orders.toArray(new Order[0]));
	}

	private void fillOrderAttribution(String locale, Order[] orders) {
		if (ArrayUtils.isEmpty(orders)) {
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

	public Map<String, Object> ledger(Date from, Date to, boolean isPrint, List<TurnoverAttribute> attributes)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("from", from);
		param.put("to", to);
		if (CollectionUtils.isNotEmpty(attributes)) {
			param.put("attributes", attributes);
		}
		List<Order> orders = orderMapper.selectOrdersByDate(param);

		fillOrderAttribution(locale, orders.toArray(new Order[0]));
		fillTurnoverAttribution(orders);

		Map<String, Object> map = ledgerTemplete.buildParam(null, orders, null, null);
		if (isPrint) {
			Map<String, Map<String, Object>> barnameParam = ledgerTemplete.buildBarnameParam(map);
			printManager.printLedger(barnameParam);
		}
		return map;
	}

	private void fillTurnoverAttribution(List<Order> orders) {
		Map<Integer, Turnover> turnoverMap = new HashMap<Integer, Turnover>();
		Map<Integer, List<Order>> turnoverOrdersMap = new HashMap<Integer, List<Order>>();
		for (Order order : orders) {
			Turnover turnover = order.getTurnover();
			int turnoverId = turnover.getId();
			List<Order> list = turnoverOrdersMap.get(turnoverId);
			if (!turnoverMap.containsKey(turnoverId)) {
				turnoverMap.put(turnoverId, turnover);
				list = new ArrayList<Order>();
				turnoverOrdersMap.put(turnoverId, list);
			}
			list.add(order);
		}
		List<Turnover> turnovers = new ArrayList<Turnover>(turnoverMap.values());
		List<TurnoverAttribute> turnoverAttributes = turnoverAttributeMapper.selectListByTurnovers(turnovers);
		for (TurnoverAttribute turnoverAttribute : turnoverAttributes) {
			Integer turnoverId = turnoverAttribute.getTurnoverId();
			Turnover turnover = turnoverMap.get(turnoverId);
			TurnoverExt turnoverExt = null;
			if (turnover instanceof TurnoverExt) {
				turnoverExt = (TurnoverExt) turnover;
			} else {
				turnoverExt = turnover.toTurnoverExt();
				turnoverMap.put(turnoverId, turnoverExt);
			}
			turnoverExt.addAttribute(turnoverAttribute);
		}

		for (Integer turnoverId : turnoverOrdersMap.keySet()) {
			List<Order> list = turnoverOrdersMap.get(turnoverId);
			Turnover turnover = turnoverMap.get(turnoverId);
			for (Order order : list) {
				order.setTurnover(turnover);
			}
		}
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
		turnoverAttributeMapper.deleteByTurnover(t);
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
		fillOrderAttribution(locale, ordersWithInfo.toArray(new Order[0]));
		return ordersWithInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void clear() {
		orderAttributionMapper.deleteAll();
		orderProductGroupMapper.deleteAll();
		orderMapper.deleteAll();
		turnoverMapper.deleteAll();
		turnoverAttributeMapper.deleteAll();
		takeawayMapper.deleteAll();
	}

	public List<OrderProductGroup> selectColdDishes() {
		List<OrderProductGroup> list = kitchenOrderMapper.selectColdDishes();
		fillOrderAttribution(kitchenLocale, list.toArray(new OrderProductGroup[0]));
		return list;
	}

	public List<OrderProductGroup> selectHotDishes() {
		List<OrderProductGroup> list = kitchenOrderMapper.selectHotDishes();
		fillOrderAttribution(kitchenLocale, list.toArray(new OrderProductGroup[0]));
		return list;
	}

	// public List<Order> selectCombos() {
	// List<Order> list = kitchenOrderMapper.selectCombos();
	// fillOrderAttribution(kitchenLocale, list);
	// return list;
	// }

	public List<OrderProductGroup> selectServedDishes() {
		List<OrderProductGroup> list = kitchenOrderMapper.selectServedDishes();
		fillOrderAttribution(kitchenLocale, list.toArray(new OrderProductGroup[0]));
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void reminderOrder(int id) {
		Order o = new Order();
		o.setId(id);
		o = orderMapper.select(o);
		o.setStatus(o.getStatus() + 1);
		orderMapper.update(o);

		orderProductGroupMapper.reminder(o);
	}

	@Transactional(rollbackFor = Exception.class)
	public void serveOrder(int id, int productId, int status) {
		OrderProductGroup opg = new OrderProductGroup();
		Order o = new Order();
		o.setId(id);
		opg.setOrder(o);
		Product p = new Product();
		p.setId(productId);
		opg.setProduct(p);
		opg.setStatus(status);
		orderProductGroupMapper.update(opg);

		List<OrderProductGroup> groups = orderProductGroupMapper.selectByOrderId(o);
		int orderStatus = 0;
		for (OrderProductGroup orderProductGroup : groups) {
			orderStatus += orderProductGroup.getStatus();
		}

		o = orderMapper.select(o);
		if (orderStatus == 0) {
			if (o.getStatus() != 0) {
				o.setStatus(0);
				orderMapper.update(o);
			}
		} else {
			if (o.getStatus() == 0) {
				o.setStatus(1);
				orderMapper.update(o);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(List<TurnoverAttribute> list) {
		List<TurnoverAttribute> attributes = turnoverAttributeMapper.selectList(list);

		String[] idStrList = { "turnoverId", "attributeName" };
		UpdateClassify updateClassify = new UpdateClassify(list, attributes, idStrList);
		List<TurnoverAttribute> insertList = updateClassify.getInsertList();
		List<TurnoverAttribute> updateList = updateClassify.getUpdateList();
		for (TurnoverAttribute t : updateList) {
			turnoverAttributeMapper.update(t);
		}
		for (TurnoverAttribute t : insertList) {
			turnoverAttributeMapper.insert(t);
		}

	}

}
