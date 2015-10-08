package com.betalife.sushibuffet.dao;

import com.betalife.sushibuffet.model.Turnover;

public interface TurnoverMapper {
	void insert(Turnover t);

	void update(Turnover t);

	Turnover select(Turnover t);

	void delete(Turnover t);

	void deleteAll();
}
