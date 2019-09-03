package com.ovit.jcw.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface GABYMapper {
	public abstract List<Map<String, Object>> queryDataByTable(@Param("table") String paramString1,
			@Param("sfzh") String paramString2);

	public abstract void deleteDataByTable(@Param("table") String paramString1, @Param("sfzh") String paramString2);

	public abstract void insertData(@Param("table") String paramString, @Param("map") Map<String, String> paramMap);

	public abstract void deleteList(@Param("table") String paramString, @Param("list") List<String> paramList);
}