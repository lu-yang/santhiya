package com.betalife.sushibuffet.dao;

import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Turnover;

public interface TurnoverMapper {
	void insert(Turnover t);

	void update(Turnover t);

	Turnover select(Turnover t);

	List<Turnover> selectList(Map<String, Object> params);

	void delete(Turnover t);

	void deleteAll();
}
