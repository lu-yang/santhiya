package com.betalife.sushibuffet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Takeaway;

public interface TakeawayMapper {
	void insert(Takeaway t);

	void update(Takeaway t);

	void delete(Takeaway t);

	void deleteAll();

	List<Takeaway> selectAll(Map<String, Date> param);

	Takeaway select(Takeaway t);
}
