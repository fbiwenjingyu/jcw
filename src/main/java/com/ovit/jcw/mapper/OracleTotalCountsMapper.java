package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface OracleTotalCountsMapper {
	public abstract List<Map<String, Object>> selectAllUserAndTables(Map<String, Object> paramMap);

	public abstract long selectCounts(Map<String, Object> paramMap);
}