package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface BIRHZXMapper {
	public abstract List<Map<String, Object>> selectForRHZXDZJZ();

	public abstract Map<String, Object> selectForRHZXSJSL();

	public abstract List<Map<String, Object>> selectForRHZXWBJ();
}