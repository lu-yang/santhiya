package com.betalife.sushibuffet.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betalife.sushibuffet.exchange.BaseExchange;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.exchange.CategoryListExchange;
import com.betalife.sushibuffet.exchange.ConstantExchange;
import com.betalife.sushibuffet.exchange.DiningtableListExchange;
import com.betalife.sushibuffet.exchange.DodoroException;
import com.betalife.sushibuffet.exchange.MapExchange;
import com.betalife.sushibuffet.exchange.OrderListExchange;
import com.betalife.sushibuffet.exchange.ProductListExchange;
import com.betalife.sushibuffet.exchange.TakeawayExchange;
import com.betalife.sushibuffet.exchange.TakeawayListExchange;
import com.betalife.sushibuffet.exchange.TurnoverExchange;
import com.betalife.sushibuffet.manager.CustomerManager;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.TakeawayExt;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.Constant;

@Controller
@RequestMapping("/*")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private CustomerManager customerManager;

	// 取所有桌子状态
	@RequestMapping(value = "availableTables", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody DiningtableListExchange fetchAllTables() {
		List<Diningtable> allTables = customerManager.getTables();
		DiningtableListExchange exchange = new DiningtableListExchange();
		exchange.setList(allTables.toArray(new Diningtable[0]));
		return exchange;
	}

	// 取指定分类的子类（取所有parentId等于{parentId}的分类）。例如：categoryName：热菜，categoryId：1；categoryName：炖菜，categoryId：2，
	// parentId：1；categoryName：炒菜，categoryId：3， parentId：1；
	// 参数parentId=1，结果是炖菜和炒菜
	@RequestMapping(value = "categories/{locale}/{parentId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody CategoryListExchange fetchRootCategories(@PathVariable String locale,
			@PathVariable int parentId) {
		Category model = new Category();
		model.setParentId(parentId);
		model.setLocale(locale);
		List<Category> all = customerManager.getCategoriesByParentId(model);

		CategoryListExchange exchange = new CategoryListExchange();
		exchange.setList(all.toArray(new Category[0]));
		return exchange;
	}

	// 开桌
	@RequestMapping(value = "openTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody TurnoverExchange openTable(@RequestBody Turnover turnover) {
		turnover = customerManager.openTable(turnover);

		TurnoverExchange exchange = new TurnoverExchange();
		exchange.setModel(turnover);
		return exchange;
	}

	// 取constants.properties文件中的内容
	@RequestMapping(value = "constant", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ConstantExchange fetchConstants() {
		Constant constant = customerManager.getConstant();

		ConstantExchange exchange = new ConstantExchange();
		exchange.setModel(constant);
		return exchange;
	}

	// 取指定分类的菜品（取所有categoryId等于{categoryId}的菜品）。
	@RequestMapping(value = "products/{locale}/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ProductListExchange fetchProductsByCategoryId(@PathVariable String locale,
			@PathVariable int categoryId) {
		Product model = new Product();
		model.setCategoryId(categoryId);
		model.setLocale(locale);
		List<Product> all = customerManager.getProductsByCategoryId(model);

		ProductListExchange exchange = new ProductListExchange();
		exchange.setList(all.toArray(new Product[0]));
		return exchange;
	}

	// 点菜。order的count表示增加量，例如：增加2个时，count是2，减少2个时，count是-2
	@RequestMapping(value = "orders/{turnoverId}/{isPrint}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody TurnoverExchange takeOrders(@RequestBody List<Order> orders,
			@PathVariable int turnoverId, @PathVariable boolean isPrint) throws Exception {
		Turnover turnover = customerManager.takeOrders(turnoverId, orders, isPrint);
		TurnoverExchange exchange = new TurnoverExchange();
		exchange.setModel(turnover);
		return exchange;
	}

	// 取所有turnoverId等于{turnoverId}的点单记录。
	@RequestMapping(value = "orders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody OrderListExchange orders(@PathVariable String locale, @PathVariable int turnoverId) {
		Order model = buildOrder(locale, turnoverId);
		List<Order> all = customerManager.getOrders(model);

		OrderListExchange exchange = new OrderListExchange();
		exchange.setList(all.toArray(new Order[0]));
		return exchange;
	}

	// 取所有turnoverId等于{turnoverId}的点单记录。
	@RequestMapping(value = "extOrders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody OrderListExchange extOrders(@PathVariable String locale,
			@PathVariable int turnoverId) {
		Order model = buildOrder(locale, turnoverId);
		List<Order> all = customerManager.getExtOrders(model);

		OrderListExchange exchange = new OrderListExchange();
		exchange.setList(all.toArray(new Order[0]));
		return exchange;
	}

	// 这个暂时不用
	@RequestMapping(value = "nouse", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public @ResponseBody OrderListExchange updateOrder(@RequestBody List<Order> orders) {
		List<Order> all = customerManager.selectOrders(orders);
		OrderListExchange exchange = new OrderListExchange();
		exchange.setList(all.toArray(new Order[0]));
		return exchange;
	}

	private Order buildOrder(String locale, int turnoverId) {
		Order model = new Order();
		model.setLocale(locale);
		Turnover turnover = new Turnover();
		turnover.setId(turnoverId);
		model.setTurnover(turnover);
		return model;
	}

	private BooleanExchange printOrders(String locale, int turnoverId, boolean kitchen) throws Exception {
		Order model = buildOrder(locale, turnoverId);
		customerManager.printOrders(model, kitchen);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	// 打印所有turnoverId等于{turnoverId}的点单记录（厨房）
	@RequestMapping(value = "printKitchenOrders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BooleanExchange printKitchenOrders(@PathVariable String locale,
			@PathVariable int turnoverId) throws Exception {
		return printOrders(locale, turnoverId, true);
	}

	// 打印所有turnoverId等于{turnoverId}的点单记录（结帐单）
	@RequestMapping(value = "printOrders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BooleanExchange printOrders(@PathVariable String locale,
			@PathVariable int turnoverId) throws Exception {
		return printOrders(locale, turnoverId, false);
	}

	// 打印所有turnoverId等于{turnoverId}的点单记录（结帐单）
	// @RequestMapping(value =
	// "orders/print/{locale}/{turnoverId}/{checkout}/{kitchen}", method =
	// RequestMethod.GET, produces = "application/json")
	// public @ResponseBody BooleanExchange printOrders(@PathVariable String
	// locale,
	// @PathVariable int turnoverId, @PathVariable boolean checkout,
	// @PathVariable boolean kitchen)
	// throws Exception {
	// return printOrders(locale, turnoverId, false);
	// }

	// 更新turnover
	@RequestMapping(value = "turnover", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody BooleanExchange updateTurnover(@RequestBody Turnover turnover) {
		customerManager.update(turnover);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	// 删除一个turnover
	@RequestMapping(value = "turnover/{turnoverId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody BooleanExchange removeTurnover(@PathVariable int turnoverId) {
		Turnover t = new Turnover();
		t.setId(turnoverId);
		customerManager.remove(t);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	// GET turnover by turnoverId with total price.
	@RequestMapping(value = "turnover/totalPrice/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody MapExchange<String, Object> getTurnoverWithTotalPrice(@PathVariable int turnoverId) {
		Map<String, Object> map = customerManager.getTurnoverWithTotalPrice(turnoverId);
		MapExchange<String, Object> exchange = new MapExchange<String, Object>();
		exchange.setModel(map);
		return exchange;
	}

	// GET turnovers by type with total price. type: 1. 所有 2. 堂吃. 3. 外卖
	@RequestMapping(value = "turnover/all/totalPrice/{type}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Map<String, Object>> getTurnoversWithTotalPrice(@PathVariable int type) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		List<Map<String, Object>> list = customerManager.getTurnoverListWithTotalPrice(params);
		return list;
	}

	// 打印指定时间内的总单
	@RequestMapping(value = "ledger/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> ledger(@PathVariable String from, @PathVariable String to)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = sdf.parse(from);
		Date toDate = sdf.parse(to);

		Map<String, Object> map = customerManager.getOrdersByDate(fromDate, toDate);
		return map;
	}

	// 取所有外卖记录（当天的）
	@RequestMapping(value = "takeaways", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody TakeawayListExchange takeaways() {
		List<TakeawayExt> all = customerManager.getTakeaways();
		TakeawayListExchange exchange = new TakeawayListExchange();
		exchange.setList(all.toArray(new TakeawayExt[0]));
		return exchange;
	}

	// 创建一个外卖
	@RequestMapping(value = "takeaway", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody TakeawayExchange addTakeaway(@RequestBody Takeaway takeaway) {
		customerManager.add(takeaway);
		TakeawayExchange exchange = new TakeawayExchange();
		exchange.setModel(takeaway);
		return exchange;
	}

	// 删除一个外卖
	@RequestMapping(value = "takeaway/{takeawayId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody BooleanExchange removeTakeaway(@PathVariable int takeawayId) {
		Takeaway takeaway = new Takeaway();
		takeaway.setId(takeawayId);
		customerManager.remove(takeaway);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	// 更新一个外卖，如果{checkout}是true，会更新{takeaway}所关联的{turnover}。通俗讲就是，{checkout}是true的时候，表示外卖结账，{takeaway}关联的{turnover}的{checkout}需要被更新成true
	@RequestMapping(value = "takeaway/{checkout}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public @ResponseBody BooleanExchange updateTakeaway(@PathVariable boolean checkout,
			@RequestBody Takeaway takeaway) {
		customerManager.update(takeaway, checkout);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	// 清表takeaway，turnover，order
	@RequestMapping(value = "clear", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody BooleanExchange clear() {
		customerManager.clear();
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody BaseExchange exception(Exception e) {
		logger.error(e.getMessage(), e);
		DodoroException ex = new DodoroException(e);
		BaseExchange exchange = new BaseExchange();
		exchange.setException(ex);
		return exchange;
	}
}
