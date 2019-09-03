package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface BJGMapper {
	public abstract void createTable(@Param("bq") String paramString1, @Param("table") String paramString2,
			@Param("map") List<Map<String, Object>> paramList);

	public abstract void deleteByTable(@Param("bq") String paramString1, @Param("table") String paramString2);

	public abstract void createOracleTable(@Param("bq") String paramString1, @Param("table") String paramString2,
			@Param("map1") Map<String, String> paramMap1, @Param("map2") Map<String, String> paramMap2);

	public abstract List<Map<String, Object>> queryByTableInfo(@Param("bq") String paramString1,
			@Param("table") String paramString2, @Param("info") String paramString3,
			@Param("fieldInfo") List<String> paramList);

	public abstract List<Map<String, Object>> queryBaisicByInfo(@Param("table") String paramString1,
			@Param("key") String paramString2, @Param("value") String paramString3,
			@Param("map") Map<String, String> paramMap);
}