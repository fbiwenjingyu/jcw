package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface BIJCXTMapper {
	public abstract List<Map<String, Object>> selectByJCXTFL(String paramString);

	public abstract List<Map<String, Object>> selectByJCXTQYAJFX();

	public abstract List<Map<String, Object>> selectByJCXTJCRYTJ();
}