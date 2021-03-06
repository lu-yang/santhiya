package com.betalife.sushibuffet.dao;

import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderAttribution;

public interface OrderAttributionMapper {
	List<OrderAttribution> selectByOrderId(Order order);

	List<OrderAttribution> selectByOrderIds(Map<String, Object> params);

	void insert(OrderAttribution o);

	void update(OrderAttribution o);

	void delete(Map<String, Integer> params);

	void deleteAll();

	OrderAttribution select(OrderAttribution o);
}
