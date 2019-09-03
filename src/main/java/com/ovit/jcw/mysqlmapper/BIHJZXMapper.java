package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public abstract interface BIHJZXMapper {
	public abstract List<Map<String, Object>> selectByHJZXType(String paramString);

	public abstract List<Map<String, Object>> selectByHJZXSJFL(String paramString);

	public abstract List<Map<String, Object>> selectByHJZXSJFLINFO(@Param("type") Integer paramInteger,
			@Param("structure") String paramString);

	public abstract List<Map<String, Object>> selectByHJZXSJZL(Integer paramInteger);

	public abstract List<Map<String, Object>> selectByHJZXSJAL();
}