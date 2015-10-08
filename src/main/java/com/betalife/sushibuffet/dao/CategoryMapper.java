package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Category;

public interface CategoryMapper {
	List<Category> selectByParentId(Category category);

	List<Category> selectAll(String locale);
}
