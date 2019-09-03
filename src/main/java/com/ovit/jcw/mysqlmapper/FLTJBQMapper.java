package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface FLTJBQMapper {
	public abstract List<Map<String, Object>> findAll(Integer paramInteger);

	public abstract List<Map<String, Object>> findAllFirstTags(@Param("tag") Integer paramInteger);

	public abstract List<Map<String, Object>> findTagsByBQID(@Param("tag") Integer paramInteger1,
			@Param("BQJB") Integer paramInteger2);

	public abstract String findTagByTable(@Param("type") Integer paramInteger1, @Param("level") Integer paramInteger2,
			@Param("table") String paramString);
}