package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public abstract interface FZBAZFALMapper {
	public abstract List<Map<String, Object>> query(@Param("lb") String paramString1,
			@Param("info") String paramString2);

	public abstract Map<String, Object> select(Integer paramInteger);

	public abstract String selectZW(Integer paramInteger);

	public abstract Integer selectCount();

	public abstract Integer selectTodayCount();

	public abstract void InsertObject(Map<String, String> paramMap);
}