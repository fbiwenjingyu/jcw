package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface AnalysisJudgmentMapper {
	public abstract List<Map<String, Object>> queryTablesByType(Integer paramInteger);

	public abstract List<String> selectAllTables();
}