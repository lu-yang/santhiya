package com.betalife.sushibuffet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.TakeawayExt;

public interface TakeawayMapper {
	void insert(Takeaway t);

	void update(Takeaway t);

	void delete(Takeaway t);

	void deleteAll();

	List<TakeawayExt> selectAll(Map<String, Date> param);

	Takeaway select(Takeaway t);
}
