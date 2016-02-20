package com.betalife.sushibuffet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.betalife.sushibuffet.model.TurnoverAttribute;

public interface TurnoverAttributeMapper {
	void insert(TurnoverAttribute t);

	void update(TurnoverAttribute t);

	TurnoverAttribute select(TurnoverAttribute t);

	List<TurnoverAttribute> selectList(@Param("attributes") List<TurnoverAttribute> attributes);

	void delete(@Param("attributes") List<TurnoverAttribute> attributes);

	void deleteAll();
}
