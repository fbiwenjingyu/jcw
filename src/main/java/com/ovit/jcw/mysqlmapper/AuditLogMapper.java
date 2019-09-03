package com.ovit.jcw.mysqlmapper;

import com.ovit.jcw.model.AuditLog;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public abstract interface AuditLogMapper {
	public abstract List<AuditLog> query(Map<String, Object> paramMap);

	public abstract void insertLogRecords(AuditLog paramAuditLog);

	public abstract Integer selectFrequency(Map<String, Object> paramMap);

	public abstract BigInteger selectUsageAmount(Map<String, Object> paramMap);

	public abstract List<Map<String, Object>> queryForDZJZ();

	public abstract List<Map<String, Object>> queryForSJSL();

	public abstract Integer selectCounts(String paramString);
}