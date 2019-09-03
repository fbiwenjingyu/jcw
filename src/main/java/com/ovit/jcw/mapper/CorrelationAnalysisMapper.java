package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface CorrelationAnalysisMapper {
	public abstract List<Map<String, Object>> selectProjectNameByTerm(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> selectProjectAddByTerm(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> selectCompanyByTerm(@Param("queryCondition") String paramString);

	public abstract List<Map<String, Object>> selectChildByTermNotClob(Map<String, Object> paramMap);
}