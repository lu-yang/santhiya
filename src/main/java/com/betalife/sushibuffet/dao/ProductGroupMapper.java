package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.KeyValue;

public interface ProductGroupMapper {

	List<KeyValue<Integer, Integer>> selectAll();
}
