package com.ovit.jcw.mysqlmapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface MysqlQueryMapper {
	public abstract List<Map<String, Object>> selectTableColumnMapping(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> selectTableWithUserInfo();
}