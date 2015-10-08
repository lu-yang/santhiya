package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Product;

public interface ProductMapper {
	List<Product> selectByCategoryId(Product product);

	List<Product> selectById(Product product);

	List<Product> selectAll(String locale);
}
