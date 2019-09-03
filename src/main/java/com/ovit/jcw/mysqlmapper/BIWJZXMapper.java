package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface BIWJZXMapper {
	public abstract List<Map<String, Object>> selectByLSQSQK();

	public abstract Map<String, Object> selectBySJZLQK();

	public abstract List<Map<String, Object>> selectByFSLQSQK();

	public abstract List<Map<String, Object>> selectByWBJSJZB();

	public abstract List<Map<String, Object>> selectByWTSJFLZB();

	public abstract List<Map<String, Object>> selectBySJZLQKFX();
}