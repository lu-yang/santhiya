package com.betalife.sushibuffet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Order;

public interface OrderMapper {

	List<Order> selectOrdersByTurnover(Order o);

	List<Order> selectExtOrdersByTurnover(Order o);	

	List<Order> selectOrders(Map<String, Object> params);

	void insert(Order o);

	List<Order> selectOrdersByDate(Map<String, Date> map);

	void delete(Order o);

	void deleteAll();

	void update(Order o);
}
