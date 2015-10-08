package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.TakeawayExt;

public interface TakeawayMapper {
	void insert(Takeaway t);

	void update(Takeaway t);

	void delete(Takeaway t);

	void deleteAll();

	List<TakeawayExt> selectTodayTakeaways();

	Takeaway select(Takeaway t);
}
