package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.OrderProductGroup;

public interface OrderProductGroupMapper {

	void insert(OrderProductGroup o);

	void deleteByOrderId(Order o);

	void deleteAll();

	void update(OrderProductGroup o);

	List<OrderProductGroup> selectByOrderId(Order o);

	void reminder(Order o);

}
