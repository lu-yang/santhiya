package com.betalife.sushibuffet.dao;

import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.AttributionGroup;
import com.betalife.sushibuffet.model.Product;

public interface AttributionGroupMapper {
	List<AttributionGroup> selectByProductId(Product product);

	List<AttributionGroup> selectByProductIds(Map<String, Object> params);

}
