package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface OracleQueryMapper {
	public abstract List<Map<String, Object>> selectDetails(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> queryContent(Map<String, Object> paramMap);

	public abstract Integer queryCounts(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> fuzzyQuery(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> associatedQuery(Map<String, Object> paramMap);

	public abstract List<String> queryForBFHM(String paramString);

	public abstract List<Map<String, Object>> queryWithYYHD(String paramString);

	public abstract List<Map<String, Object>> queryWithDXHD(String paramString);
}