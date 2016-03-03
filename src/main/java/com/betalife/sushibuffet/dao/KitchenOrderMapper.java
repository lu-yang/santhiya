package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.OrderProductGroup;

public interface KitchenOrderMapper {

	List<OrderProductGroup> selectColdDishes();

	List<OrderProductGroup> selectHotDishes();

	List<OrderProductGroup> selectServedDishes();

	// List<Order> selectCombos();

}
