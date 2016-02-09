package com.betalife.sushibuffet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.betalife.sushibuffet.model.Diningtable;

public interface DiningtableMapper {
	List<Diningtable> selectTables(@Param(value = "id") Integer tableId);
}
