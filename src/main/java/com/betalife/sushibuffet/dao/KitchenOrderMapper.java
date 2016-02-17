package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Order;

public interface KitchenOrderMapper {

	List<Order> selectColdDishes();

	List<Order> selectHotDishes();

	List<Order> selectServedDishes();

	List<Order> selectCombos();

}
